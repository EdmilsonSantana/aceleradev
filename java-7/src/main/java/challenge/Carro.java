package challenge;



import java.util.Objects;
import java.util.Optional;

public class Carro {

	private final Cor cor;

	private final String placa;

	private final Motorista motorista;

	private Carro(Cor cor, String placa, Motorista motorista) {
		this.cor = cor;
		this.placa = placa;
		this.motorista = motorista;
	}

	public Cor getCor() {
		return cor;
	}

	public String getPlaca() {
		return placa;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public Boolean ehAutonomo() {
		return !Optional.ofNullable(this.motorista).isPresent();
	}

	public Boolean possuiMotoristaComIdadeMaiorQue(Integer idade) {
		return !ehAutonomo() && this.motorista.possuiIdadeMaiorQue(idade);
	}

	public static CarroBuilder builder() {
		return new CarroBuilder();
	}

	public static class CarroBuilder {
		private Cor cor;
		private String placa;
		private Motorista motorista;

		public CarroBuilder withCor(Cor cor) {
			this.cor = cor;
			return this;
		}

		public CarroBuilder withPlaca(String placa) {
			this.placa = placa;
			return this;
		}

		public CarroBuilder withMotorista(Motorista motorista) {
			this.motorista = motorista;
			return this;
		}

		public Carro build() {
			Objects.requireNonNull(this.placa);
			Objects.requireNonNull(this.cor);
			return new Carro(cor, placa, motorista);
		}
	}

}
