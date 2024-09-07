package br.hendrew.gestor_biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "livro", schema = "public")
@SequenceGenerator(schema = "public", name = "seq_livro", sequenceName = "seq_livro", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class Livro implements Serializable {

    @Id
    @Column(name = "livroId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_livro")
    private Integer id;

    @NotNull
    @Column(name = "titulo")
    @Size(max = 100)
    private String titulo;

    @NotNull
    @Column(name = "autor")
    @Size(max = 100)
    private String autor;

    @NotNull
    @Column(name = "isbn")
    @Size(max = 20)
    private String isbn;

    @NotNull
    @Column(name = "datapublicacao")
    private LocalDate dataPublicacao;

    @NotNull
    @Column(name = "categoria")
    @Size(max = 30)
    private String categoria;
}
