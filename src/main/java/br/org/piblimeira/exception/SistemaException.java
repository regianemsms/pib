package br.org.piblimeira.exception;

public class SistemaException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SistemaException(){
	}
	
	public SistemaException(String message){
		super(message); 
	}
	
	public SistemaException(String message, Throwable cause){
		super(message, cause); 
	}
	
	public SistemaException(Throwable cause) { 
		super(cause); 
	}

}
