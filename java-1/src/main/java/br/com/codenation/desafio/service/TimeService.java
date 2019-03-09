package br.com.codenation.desafio.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.desafio.model.Jogador;
import br.com.codenation.desafio.model.Time;

/**
 * Classe responsável pelas operações relacionadas ao {@link Time} e seus
 * Jogadores.
 * 
 * @author edmilson.santana
 *
 */
public class TimeService {

	private final Map<Long, Time> times;

	public TimeService() {
		times = new HashMap<>();
	}

	public void incluir(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal,
			String corUniformeSecundario) {

		if (times.containsKey(id)) {
			throw new IdentificadorUtilizadoException();
		}

		times.put(id, Time.novoTime(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
	}

	public Time buscarPorId(Long idTime) {
		return Optional.ofNullable(times.get(idTime)).orElseThrow(() -> new TimeNaoEncontradoException());
	}

	public void adicionarJogador(Long idTime, Jogador jogador) {
		Time time = this.buscarPorId(idTime);
		time.adicionarJogador(jogador);
	}

	public Jogador buscarCapitao(Long idTime) {

		Time time = this.buscarPorId(idTime);

		return Optional.ofNullable(time.getCapitao()).orElseThrow(() -> new CapitaoNaoInformadoException());
	}

	public String buscarNomeTime(Long idTime) {
		return this.buscarPorId(idTime).getNome();
	}

	public List<Long> buscarJogadoresDoTime(Long idTime) {
		return this.buscarPorId(idTime).getIdsJogadores();
	}

	public Jogador buscarJogadorMaisVelho(Long idTime) {
		return this.buscarPorId(idTime).getJogadorMaisVelho().orElseThrow(() -> new JogadorNaoEncontradoException());
	}

	public Jogador buscarMelhorJogadorDoTime(Long idTime) {
		return this.buscarPorId(idTime).getMelhorJogador().orElseThrow(() -> new JogadorNaoEncontradoException());
	}

	public List<Long> buscarIdsTimes() {
		return this.times.values().stream().map(Time::getId).sorted(Comparator.naturalOrder())
				.collect(Collectors.toList());
	}

	public Jogador buscarJogadorMaiorSalario(Long idTime) {
		return this.buscarPorId(idTime).getJogadorComMaiorSalario()
				.orElseThrow(() -> new JogadorNaoEncontradoException());
	}

	public String buscarCorCamisaTimeDeFora(Long idTimeDaCasa, Long idTimeDeFora) {

		String corCamisaTimeDefora = null;

		Time timeDaCasa = this.buscarPorId(idTimeDaCasa);
		Time timeDeFora = this.buscarPorId(idTimeDeFora);

		if (timeDaCasa.getCorUniformePrincipal().equals(timeDeFora.getCorUniformePrincipal())) {
			corCamisaTimeDefora = timeDeFora.getCorUniformeSecundario();
		} else {
			corCamisaTimeDefora = timeDeFora.getCorUniformePrincipal();
		}

		return corCamisaTimeDefora;
	}
	
	public void clear() {
		this.times.clear();
	}

}
