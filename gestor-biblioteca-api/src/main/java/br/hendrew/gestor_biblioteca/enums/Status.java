package br.hendrew.gestor_biblioteca.enums;

import br.hendrew.gestor_biblioteca.interfaces.Description;
import lombok.Getter;

@Getter
public enum Status implements Description {

    ATIVO("Ativo"),
    BAIXADO("Baixado");


    private final String description;

    Status(String description) {
        this.description = description;
    }
}
