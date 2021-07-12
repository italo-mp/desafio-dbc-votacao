package com.desafio.dbc.votacao.exception;

public class DesafioDbcException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DesafioDbcException() {
		super();
	}

	public DesafioDbcException(String message, Throwable cause) {
		super(message, cause);
	}

	public DesafioDbcException(String message) {
		super(message);
	}

	public DesafioDbcException(Throwable cause) {
		super(cause);
	}
}
