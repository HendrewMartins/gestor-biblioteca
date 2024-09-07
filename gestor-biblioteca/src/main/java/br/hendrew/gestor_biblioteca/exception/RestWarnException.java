package br.hendrew.gestor_biblioteca.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

public class RestWarnException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private int status = HttpStatus.BAD_REQUEST.value();
	
	public static RestWarnException byListMensagens(List<String> mensagens) {
		
		String erros = null;
		
		for (String msg : mensagens) {
			if (erros==null) {
				erros = "- " + msg;
			} else {
				erros += "\n- " + msg;
			}
		}
		
		return new RestWarnException(erros);
		
	}
	
	public RestWarnException() {
		super();
	}

	public RestWarnException(String msg) {
		super(msg);
	}
	
	public RestWarnException(int status, String msg) {
		super(msg);
		this.status = status;
	}

	public RestWarnException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
