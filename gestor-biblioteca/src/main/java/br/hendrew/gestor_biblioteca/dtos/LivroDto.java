package br.hendrew.gestor_biblioteca.dtos;

import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
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
public class LivroDto implements Serializable {

    private Integer id;
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate dataPublicacao;
    private CategoriaLivro categoria;

}
