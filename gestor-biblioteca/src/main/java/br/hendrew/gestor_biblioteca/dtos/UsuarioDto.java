package br.hendrew.gestor_biblioteca.dtos;

import br.hendrew.gestor_biblioteca.annotation.ManyDto;
import br.hendrew.gestor_biblioteca.exception.ValidationException;
import br.hendrew.gestor_biblioteca.interfaces.EntityDto;
import br.hendrew.gestor_biblioteca.utils.ValidatorUtil;
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
@ManyDto
public class UsuarioDto implements EntityDto {

    private Integer id;
    @NotNull
    private String descricao;
    @NotNull
    private String email;
    @NotNull
    private LocalDate dataCadastro;
    @NotNull
    private String telefone;

    public UsuarioDto(Integer id){
        this.id = id;
    }

    @Override
    public void validate() {
        if (ValidatorUtil.isDataValida(dataCadastro))
            throw new ValidationException("Data de cadastro não pode ser maior que a data atual!");
        if (!ValidatorUtil.isValidEmail(email))
            throw new ValidationException("E-mail inválido: " + email + "!");

    }

}
