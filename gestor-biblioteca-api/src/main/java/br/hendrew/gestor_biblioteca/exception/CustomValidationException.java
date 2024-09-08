package br.hendrew.gestor_biblioteca.exception;

public class CustomValidationException extends RuntimeException {
    private final String fieldName;
    private final String errorMessage;

    public CustomValidationException(String fieldName, String errorMessage) {
        super(String.format("Erro no campo '%s': %s", fieldName, errorMessage));
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
