package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.crud.CrudService;
import br.hendrew.gestor_biblioteca.dtos.CategoriaDto;
import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.dtos.LivroDto;
import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
import br.hendrew.gestor_biblioteca.enums.Status;
import br.hendrew.gestor_biblioteca.exception.ValidationException;
import br.hendrew.gestor_biblioteca.model.Emprestimo;
import br.hendrew.gestor_biblioteca.model.Livro;
import br.hendrew.gestor_biblioteca.repository.EmprestimoRepository;
import br.hendrew.gestor_biblioteca.utils.QueryDto;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService extends CrudService<Emprestimo, Integer, EmprestimoRepository, EmprestimoDto> {


    public EmprestimoService() {
        super(EmprestimoDto.class, Emprestimo.class);
    }

    @Override
    public GenericResponse save(EmprestimoDto genericClass) {
        if (this.livroEmprestado(genericClass.getLivro().getId()))
            throw new ValidationException("Livro " + genericClass.getLivro().getTitulo() + " já se encontra emprestado!");
        return super.save(genericClass);
    }

    public Boolean livroEmprestado(Integer livroId) {
        String sql = "select exists(select 1 from public.emprestimo where livro_id = :livroId and status = :status) as emprestado";
        return (Boolean) getEntityManager()
                .unwrap(Session.class).createNativeQuery(sql)
                .addScalar("emprestado", StandardBasicTypes.BOOLEAN)
                .setParameter("livroId", livroId)
                .setParameter("status", Status.ATIVO.ordinal())
                .getSingleResult();
    }

    public List<LivroDto> recomendacao(Integer usuarioId) throws Exception {
        List<CategoriaLivro> categoriaLivros = this.buscarCategoriaEmprestimoUsuario(usuarioId);
        if (categoriaLivros == null || categoriaLivros.isEmpty()) return new ArrayList<>();
        return this.buscarRecomendacao(categoriaLivros.stream().map(item -> item.ordinal()).collect(Collectors.toUnmodifiableList()), usuarioId);
    }

    private List<LivroDto> buscarRecomendacao(List<Integer> categoria, Integer usuarioId) {
        String sql = "select livro.* from public.livro " +
                "where livro.categoria in :categoria " +
                "and not exists(select 1 from public.emprestimo " +
                "where emprestimo.livro_id = livro.livro_id and emprestimo.usuario_id = :usuarioId)";

        List<Livro> livros = this.getEntityManager().createNativeQuery(sql, Livro.class)
                .setParameter("categoria", categoria)
                .setParameter("usuarioId", usuarioId)
                .getResultList();

        if (livros == null || livros.isEmpty()) new ArrayList<>();
        List<LivroDto> livroDtos = new ArrayList<>();
        for (Livro livro : livros) {
            livroDtos.add(getEntityToDtoMapper().mapEntityToDto(livro, LivroDto.class));
        }
        return livroDtos;
    }

    public List<CategoriaLivro> buscarCategoriaEmprestimoUsuario(Integer usuarioId) throws Exception {
        String sql = "select distinct livro.categoria from public.emprestimo " +
                "inner join public.livro on livro.livro_id = emprestimo.livro_id " +
                "where emprestimo.usuario_id = :usuarioId";

        QueryDto<CategoriaDto> queryDto = new QueryDto<>(sql, getEntityManager(), CategoriaDto.class);
        queryDto.setParameter("usuarioId", usuarioId);
        List<CategoriaDto> categoriaDtos = queryDto.getResultList();
        if (categoriaDtos == null || categoriaDtos.isEmpty())
            return new ArrayList<>();
        return categoriaDtos.stream().map(item -> item.getCategoria()).collect(Collectors.toUnmodifiableList());
    }
}
