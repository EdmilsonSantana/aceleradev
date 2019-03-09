package br.com.codenation.desafio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.model.Jogador;
import br.com.codenation.desafio.util.Util;

public class JogadorService {

	private final Map<Long, Jogador> jogadores;

	public JogadorService() {
		jogadores = new HashMap<>();
	}

	public Jogador buscarPorId(Long idJogador) {
		return Optional.ofNullable(jogadores.get(idJogador)).orElseThrow(() -> new JogadorNaoEncontradoException());
	}

	public Jogador incluir(Long id, String nome, LocalDate dataNascimento, Integer nivelHabilidade,
			BigDecimal salario) {

		if (this.jogadores.containsKey(id)) {
			throw new IdentificadorUtilizadoException();
		}

		Jogador jogador = Jogador.novoJogador(id, nome, dataNascimento, nivelHabilidade, salario);
		this.jogadores.put(id, jogador);

		return jogador;
	}

	public void definirCapitao(Long idJogador) {
		Jogador jogador = this.buscarPorId(idJogador);
		jogador.definirCapitao();
	}

	public String buscarNomeJogador(Long idJogador) {
		return this.buscarPorId(idJogador).getNome();
	}

	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return this.buscarPorId(idJogador).getSalario();
	}

	public List<Long> getTopJogadores(Integer top) {

		return Util.head(this.jogadores.values().stream()
				.sorted(Comparator
						.comparing(Jogador::getNivelHabilidade, Comparator.reverseOrder())
						.thenComparing(Jogador::getId, Comparator.naturalOrder()))
				.map(Jogador::getId).collect(Collectors.toList()), top);
	}
	
	public void clear() {
		this.jogadores.clear();
	}
}
