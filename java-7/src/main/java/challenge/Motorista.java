package challenge;



import java.util.Objects;

public class Motorista {

	private static final int PONTUACAO_MAXIMA_CARTEIRA = 20;

	private static final int IDADE_MINIMA_MOTORISTA = 18;

	private final String nome;

	private final Integer idade;

	private final Integer pontos;

	private final String habilitacao;

	private Motorista(String nome, Integer idade, Integer pontos, String habilitacao) {
		this.nome = nome;
		this.idade = idade;
		this.pontos = pontos;
		this.habilitacao = habilitacao;
	}

	public String getNome() {
		return nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public Integer getPontos() {
		return pontos;
	}

	public String getHabilitacao() {
		return habilitacao;
	}

	public Boolean ehDeMenor() {
		return !this.possuiIdadeMaiorQue(IDADE_MINIMA_MOTORISTA);
	}

	public Boolean possuiHabilitacaoSuspensa() {
		return this.pontos > PONTUACAO_MAXIMA_CARTEIRA;
	}

	public Boolean possuiIdadeMaiorQue(Integer idade) {
		return this.idade > idade;
	}

	public static MotoristaBuilder builder() {
		return new MotoristaBuilder();
	}

	public static class MotoristaBuilder {
		private String nome;

		private Integer idade;

		private Integer pontos;

		private String habilitacao;

		public MotoristaBuilder withNome(String nome) {
			this.nome = nome;
			return this;
		}

		public MotoristaBuilder withIdade(Integer idade) {
			if (idade < 0) {
				throw new IllegalArgumentException("Idade não pode ser um número negativo.");
			}
			this.idade = idade;
			return this;
		}

		public MotoristaBuilder withPontos(Integer pontos) {
			if (pontos < 0) {
				throw new IllegalArgumentException("Pontos não pode ser um número negativo.");
			}
			this.pontos = pontos;
			return this;
		}

		public MotoristaBuilder withHabilitacao(String habilitacao) {
			this.habilitacao = habilitacao;
			return this;
		}

		public Motorista build() {

			Objects.requireNonNull(this.habilitacao);
			Objects.requireNonNull(this.nome);

			return new Motorista(nome, idade, pontos, habilitacao);
		}

	}
}
