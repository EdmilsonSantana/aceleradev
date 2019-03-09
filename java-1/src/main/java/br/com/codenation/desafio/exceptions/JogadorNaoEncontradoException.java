package br.com.codenation.desafio.exceptions;

public class JogadorNaoEncontradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9142736485985757832L;
	private static final String MSG_JOGADOR_NAO_ENCONTRADO = "O Jogador não foi encontrado.";

	public JogadorNaoEncontradoException() {
		super(MSG_JOGADOR_NAO_ENCONTRADO);
	}
}
