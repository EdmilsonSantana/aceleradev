package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.model.Jogador;
import br.com.codenation.desafio.service.JogadorService;
import br.com.codenation.desafio.service.TimeService;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private TimeService timeService;
	private JogadorService jogadorService;
	
	public DesafioMeuTimeApplication() {
		this.timeService = new TimeService();
		this.jogadorService = new JogadorService();
	}
	
	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal,
			String corUniformeSecundario) {

		timeService.incluir(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade,
			BigDecimal salario) {

		Jogador jogador = jogadorService.incluir(id, nome, dataNascimento, nivelHabilidade, salario);

		timeService.adicionarJogador(idTime, jogador);
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		jogadorService.definirCapitao(idJogador);
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		return timeService.buscarCapitao(idTime).getId();
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		return jogadorService.buscarNomeJogador(idJogador);
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		return timeService.buscarNomeTime(idTime);
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		return timeService.buscarJogadoresDoTime(idTime);
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		return timeService.buscarMelhorJogadorDoTime(idTime).getId();
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		return timeService.buscarJogadorMaisVelho(idTime).getId();
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		return timeService.buscarIdsTimes();
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		return timeService.buscarJogadorMaiorSalario(idTime).getId();
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return jogadorService.buscarSalarioDoJogador(idJogador);
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		return jogadorService.getTopJogadores(top);
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		return timeService.buscarCorCamisaTimeDeFora(timeDaCasa, timeDeFora);
	}

}
