package br.hendrew.gestor_biblioteca.enums;

import br.hendrew.gestor_biblioteca.interfaces.Description;
import lombok.Getter;

@Getter
public enum CategoriaLivro implements Description {
    ROMANCE("Romance"),
    FICCAO("Ficção"),
    NAO_FICCAO("Não-ficção"),
    FANTASIA("Fantasia"),
    CIENTIFICO("Científico"),
    BIOGRAFIA("Biografia"),
    HISTORIA("História"),
    AUTOAJUDA("Autoajuda"),
    MISTÉRIO("Mistério"),
    TERROR("Terror");

    private final String descricao;
    CategoriaLivro(String descricao) {
        this.descricao = descricao;
    }
}
