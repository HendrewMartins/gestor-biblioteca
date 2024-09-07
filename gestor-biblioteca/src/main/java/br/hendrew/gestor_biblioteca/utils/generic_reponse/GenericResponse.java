package br.hendrew.gestor_biblioteca.utils.generic_reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenericResponse {

    private GenericResponseTypeEnum type;
    private String errorId;
    private String message;
    private String error;
    private Integer status;

    public static GenericResponse getGenericResponse(String errorId, String message, String error, Integer status) {
        GenericResponse response = new GenericResponse();
        response.setType(GenericResponseTypeEnum.ERROR);
        response.setErrorId(errorId);
        response.setMessage(message);
        response.setError(error);
        response.setStatus(status);
        return response;
    }

    public static GenericResponse getGenericResponse(String message, String error, Integer status) {
        GenericResponse response = new GenericResponse();
        response.setMessage(message);
        response.setError(error);
        response.setStatus(status);
        return response;
    }

    public static GenericResponse getGenericResponse(String message, Integer status) {
        GenericResponse response = new GenericResponse();
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }

    public static GenericResponse getGenericResponse(GenericResponseTypeEnum type, String message, Integer status) {
        GenericResponse response = new GenericResponse();
        response.setType(type);
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }


}
