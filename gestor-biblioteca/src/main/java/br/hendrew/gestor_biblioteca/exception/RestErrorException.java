package br.hendrew.gestor_biblioteca.exception;

public class RestErrorException extends RuntimeException {
	
	public RestErrorException(Exception e) {
		super(e);
	}
	
	public RestErrorException(String msg) {
		super(msg);
	}

	public RestErrorException(String message, Throwable cause) {
		super(message, cause);
	}

}
