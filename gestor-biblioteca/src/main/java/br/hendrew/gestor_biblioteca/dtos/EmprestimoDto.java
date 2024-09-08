package br.hendrew.gestor_biblioteca.dtos;

import br.hendrew.gestor_biblioteca.annotation.ManyDto;
import br.hendrew.gestor_biblioteca.enums.Status;
import br.hendrew.gestor_biblioteca.interfaces.EntityDto;
import br.hendrew.gestor_biblioteca.utils.ValidatorUtil;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoDto implements EntityDto {

    private Integer id;
    @NotNull
    private LivroDto livro;
    @NotNull
    private UsuarioDto usuario;
    @NotNull
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    @NotNull
    private Status status;

    @Override
    public void validate() {
        if (ValidatorUtil.isDataValida(dataEmprestimo))
            throw new ValidationException("Data de emprestimo não pode ser maior que a data atual!");
        if (ValidatorUtil.isDataValida(dataDevolucao))
            throw new ValidationException("Data de devolução não pode ser maior que a data atual!");
        if (Status.BAIXADO.equals(status) && dataDevolucao == null)
            throw new ValidationException("Data de devolução não pode ser nula quando situação igual baixado!");
    }
}
