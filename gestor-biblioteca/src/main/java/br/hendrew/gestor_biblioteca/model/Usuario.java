package br.hendrew.gestor_biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "usuario", schema = "public")
@SequenceGenerator(schema = "public", name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {

    @Id
    @Column(name = "usuarioId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    private Integer id;

    @NotNull
    @Column(name = "descricao")
    @Size(max = 100)
    private String descricao;

    @NotNull
    @Column(name = "email")
    @Size(max = 100)
    private String email;

    @NotNull
    @Column(name = "dataCadastro")
    private LocalDate dataCadastro;

    @NotNull
    @Column(name = "telefone")
    @Size(max = 11)
    private String telefone;
}
