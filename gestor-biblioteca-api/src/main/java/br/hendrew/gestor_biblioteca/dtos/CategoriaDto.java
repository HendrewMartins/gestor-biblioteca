package br.hendrew.gestor_biblioteca.dtos;

import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDto {
    @Enumerated(EnumType.ORDINAL)
    private CategoriaLivro categoria;
}
