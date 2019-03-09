package br.com.codenation.desafio.exceptions;

public class IdentificadorUtilizadoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String MSG_ID_UTILIZADO = "O identificador está sendo utilizado.";

	public IdentificadorUtilizadoException() {
		super(MSG_ID_UTILIZADO);
	}

}
