package br.hendrew.gestor_biblioteca.utils.generic_reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenericResponse {

    private ResponseTypeEnum type;
    private String errorId;
    private String message;
    private String error;
    private Integer status;

    public GenericResponse(String message, Integer status) {
        this.setMessage(message);
        this.setStatus(status);
    }

    public static GenericResponse getGenericResponse(String message, Integer status) {
        GenericResponse response = new GenericResponse();
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }

}
