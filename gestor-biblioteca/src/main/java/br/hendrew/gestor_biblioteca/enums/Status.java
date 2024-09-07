package br.hendrew.gestor_biblioteca.enums;

import br.hendrew.gestor_biblioteca.interfaces.Description;
import lombok.Getter;

@Getter
public enum Status implements Description {

    ATIVO("Ativo"),
    BAIXADO("Baixado");


    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }
}
