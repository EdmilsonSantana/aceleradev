package br.com.codenation.desafio.exceptions;

public class TimeNaoEncontradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3544451725858932368L;
	
	private static final String MSG_TIME_NAO_ENCONTRADO = "O Time não foi encontrado.";

	public TimeNaoEncontradoException() {
		super(MSG_TIME_NAO_ENCONTRADO);
	}
}
