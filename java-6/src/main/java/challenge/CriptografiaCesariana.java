package challenge;

import java.util.function.Function;
import java.util.function.Predicate;

public class CriptografiaCesariana implements Criptografia {

	private final static Integer DESLOCAMENTO_CIFRA = 3;

	@Override
	public String criptografar(String texto) {
		return this.deslocarCifra(texto, (caractere) -> caractere + DESLOCAMENTO_CIFRA);
	}

	@Override
	public String descriptografar(String texto) {
		return this.deslocarCifra(texto, (caractere) -> caractere - DESLOCAMENTO_CIFRA);
	}

	private String deslocarCifra(String texto, Function<Integer, Integer> funcaoDeslocamento) {

		this.validarTexto(texto);

		Predicate<Integer> condicaoDeslocamento = (
				caractere) -> !(Character.isDigit(caractere) || Character.isWhitespace(caractere));

		return texto.toLowerCase().chars().map(
				(caractere) -> condicaoDeslocamento.test(caractere) ? funcaoDeslocamento.apply(caractere) : caractere)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	private void validarTexto(String texto) {
		if (texto == null) {
			throw new NullPointerException();
		}
		if ("".equals(texto)) {
			throw new IllegalArgumentException();
		}
	}
}
