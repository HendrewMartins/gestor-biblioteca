package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.crud.GenericCrudService;
import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.enums.Status;
import br.hendrew.gestor_biblioteca.model.Emprestimo;
import br.hendrew.gestor_biblioteca.repository.EmprestimoRepository;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoService extends GenericCrudService<Emprestimo, Integer, EmprestimoRepository, EmprestimoDto> {



    public EmprestimoService() {
        super(EmprestimoDto.class,Emprestimo.class);
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

}
