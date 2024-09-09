package br.hendrew.gestor_biblioteca.enums;

import br.hendrew.gestor_biblioteca.interfaces.Description;
import lombok.Getter;

@Getter
public enum CategoriaLivro implements Description {
    ROMANCE("Romance", "Romance"),
    FICCAO("Ficção", "Fiction"),
    NAO_FICCAO("Não-ficção", "Non-fiction"),
    FANTASIA("Fantasia", "Fantasy"),
    CIENTIFICO("Científico", "Science"),
    BIOGRAFIA("Biografia", "Biography"),
    HISTORIA("História", "History"),
    AUTOAJUDA("Autoajuda", "Self-help"),
    MISTERIO("Mistério", "Mystery"),
    TERROR("Terror", "Horror"),
    COMPUTADOR("Computador", "Computers"),
    EDUCACAO("Educação","Education"),
    OUTRAS("Outras", "Outher");

    private final String description;
    private final String descriptionEn;

    CategoriaLivro(String description, String descriptionEn) {
        this.description = description;
        this.descriptionEn = descriptionEn;
    }

    public static CategoriaLivro fromCategoria(String descricaoEn) {
        for (CategoriaLivro categoria : CategoriaLivro.values()) {
            if (categoria.getDescriptionEn().equalsIgnoreCase(descricaoEn)) {
                return categoria;
            }
        }
        return OUTRAS;
    }
}
