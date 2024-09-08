package br.hendrew.gestor_biblioteca.dtos;

import br.hendrew.gestor_biblioteca.annotation.ManyDto;
import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
import br.hendrew.gestor_biblioteca.interfaces.EntityDto;
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
public class LivroDto implements EntityDto {

    private Integer id;
    @NotNull
    private String titulo;
    @NotNull
    private String autor;
    @NotNull
    private String isbn;
    @NotNull
    private LocalDate dataPublicacao;
    @NotNull
    private CategoriaLivro categoria;

    public LivroDto(Integer id) {
        this.id = id;
    }

    public LivroDto(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    @Override
    public void validate() {
    }
}
