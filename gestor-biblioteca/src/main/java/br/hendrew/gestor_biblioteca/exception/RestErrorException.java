package br.hendrew.gestor_biblioteca.exception;

public class RestErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
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
