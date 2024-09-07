package br.hendrew.gestor_biblioteca.exception;

public class TratamentoNotFoundException extends Exception {

	public TratamentoNotFoundException() {
		    super();
		  }

	public TratamentoNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
		    super(message, cause, enableSuppression, writableStackTrace);
		  }

	public TratamentoNotFoundException(String message, Throwable cause) {
		    super(message, cause);
		  }

	public TratamentoNotFoundException(String message) {
		    super(message);
		  }

	public TratamentoNotFoundException(Throwable cause) {
		    super(cause);
		  }

}
