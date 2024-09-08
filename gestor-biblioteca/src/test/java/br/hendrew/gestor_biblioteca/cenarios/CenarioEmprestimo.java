package br.hendrew.gestor_biblioteca.cenarios;

import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.dtos.LivroDto;
import br.hendrew.gestor_biblioteca.dtos.UsuarioDto;
import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
import br.hendrew.gestor_biblioteca.enums.Status;
import br.hendrew.gestor_biblioteca.model.Emprestimo;
import br.hendrew.gestor_biblioteca.model.Livro;
import br.hendrew.gestor_biblioteca.model.Usuario;

import java.time.LocalDate;


public class CenarioEmprestimo {

    public static Usuario usuario() {
        return CenarioUsuario.usuario();
    }

    public static Livro livro() {
        return CenarioLivro.livro();
    }

    public static EmprestimoDto getEmprestimoDto(Integer livroId, Integer usuarioId) {
        EmprestimoDto dto = new EmprestimoDto();
        dto.setLivro(new LivroDto(livroId));
        dto.setUsuario(new UsuarioDto(usuarioId));
        dto.setDataEmprestimo(LocalDate.now());
        dto.setStatus(Status.ATIVO);
        return dto;
    }

    public static Emprestimo getEmprestimo(Integer livroId, Integer usuarioId) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(new Livro(livroId));
        emprestimo.setUsuario(new Usuario(usuarioId));
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setStatus(Status.ATIVO);
        return emprestimo;
    }
}
