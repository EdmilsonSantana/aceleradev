package br.com.codenation.desafio.exceptions;

public class CapitaoNaoInformadoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1089706874932407082L;
	private static final String MSG_CAPITAO_NAO_INFORMADO = "O Capitão não foi informado.";
	
	public CapitaoNaoInformadoException() {
		super(MSG_CAPITAO_NAO_INFORMADO);
	}
}
