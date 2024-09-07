package br.hendrew.gestor_biblioteca.dtos;

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
public class UsuarioDto implements Serializable {

    private Integer id;
    private String descricao;
    private String email;
    private LocalDate dataCadastro;
    private String telefone;

}
