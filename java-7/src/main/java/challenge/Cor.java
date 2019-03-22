package challenge;



import java.util.function.Supplier;

public enum Cor implements Supplier<String> {

	COLORIDO("Colorido"), PRETO("Preto"), BRANCO("Branco");

	private String descricao;

	private Cor(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String get() {
		return this.descricao;
	}
}
