package br.hendrew.gestor_biblioteca.dtos;

import br.hendrew.gestor_biblioteca.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoDto implements Serializable {

    private Integer id;
    private LivroDto livro;
    private UsuarioDto usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private Status status;
}
