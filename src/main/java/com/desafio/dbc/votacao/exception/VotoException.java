package com.desafio.dbc.votacao.exception;

public class VotoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public VotoException() {
	        super();
	    }
	    public VotoException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public VotoException(String message) {
	        super(message);
	    }
	    public VotoException(Throwable cause) {
	        super(cause);
	    }
}
