package br.hendrew.gestor_biblioteca.utils.generic_reponse;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum GenericResponseTypeEnum {

    ERROR("Erro"),
    VALIDATION("Validação");

    @Getter
    @JsonValue
    private String value;

}
