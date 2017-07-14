package br.org.piblimeira.exception;

/**
 * 
 * @author Andr�
 *
 */
public class BusinessException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BusinessException(){
		 super(); 
	}
	
	public BusinessException(String message){
		super(message); 
	}
	
	public BusinessException(String message, Throwable cause){
		super(message, cause); 
	}
	
	public BusinessException(Throwable cause) { 
		super(cause); 
	}
	
}
