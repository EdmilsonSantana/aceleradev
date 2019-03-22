package challenge;



import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Estacionamento {

	private static final int IDADE_MINIMA_PARA_OCUPAR_VAGA = 55;

	private static final Integer NUMERO_MAXIMO_VAGAS = 10;

	private Queue<Carro> carros = new LinkedList<>();

	public void estacionar(Carro carro) {
		if (carro.ehAutonomo()) {
			throw new EstacionamentoException();
		}

		Motorista motorista = carro.getMotorista();
		if (motorista.ehDeMenor() || motorista.possuiHabilitacaoSuspensa()) {
			throw new EstacionamentoException();
		}

		if (this.carrosEstacionados() == NUMERO_MAXIMO_VAGAS) {
			this.removerCarroEstacionado();
		}

		this.carros.add(carro);
	}

	private void removerCarroEstacionado() {

		Carro carro = Optional.ofNullable(this.carros.poll()).orElseThrow(() -> new EstacionamentoException());

		if (carro.possuiMotoristaComIdadeMaiorQue(IDADE_MINIMA_PARA_OCUPAR_VAGA)) {
			this.removerCarroEstacionado();
			this.carros.add(carro);
		}
	}

	public int carrosEstacionados() {
		return this.carros.size();
	}

	public Boolean carroEstacionado(Carro carro) {
		return this.carros.contains(carro);
	}
}
