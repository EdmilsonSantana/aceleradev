package br.com.codenation.desafio.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe responsável pela representação do Time de Futebol.
 * 
 * @author edmilson.santana
 *
 */
public class Time {

	private Long id;

	private String nome;

	private LocalDate dataCriacao;

	private String corUniformePrincipal;

	private String corUniformeSecundario;

	private Jogador capitao;

	private Collection<Jogador> jogadores = new HashSet<Jogador>();

	private Time(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal,
			String corUniformeSecundario) {
		this.setId(id);
		this.setNome(nome);
		this.setDataCriacao(dataCriacao);
		this.setCorUniformePrincipal(corUniformePrincipal);
		this.setCorUniformeSecundario(corUniformeSecundario);
	}

	public Jogador getCapitao() {
		return capitao;
	}

	public void setCapitao(Jogador jogador) {
		if (this.jogadores.contains(jogador)) {
			this.capitao = jogador;
		}
	}

	public List<Long> getIdsJogadores() {
		return this.jogadores.stream().map(Jogador::getId).sorted(Comparator.naturalOrder())
				.collect(Collectors.toList());
	}

	public Optional<Jogador> getJogadorMaisVelho() {

		return this.jogadores.stream()
				.sorted(Comparator
						.comparing(Jogador::getIdade, Comparator.reverseOrder())
						.thenComparing(Jogador::getId, Comparator.naturalOrder()))
				.findFirst();
	}

	public Optional<Jogador> getMelhorJogador() {
		return this.jogadores.stream().sorted(
				Comparator.comparing(Jogador::getNivelHabilidade, Comparator.reverseOrder()))
				.findFirst();
	}

	public Optional<Jogador> getJogadorComMaiorSalario() {
		return this.jogadores.stream()
				.sorted(Comparator
						.comparing(Jogador::getSalario, Comparator.reverseOrder())
						.thenComparing(Jogador::getId, Comparator.naturalOrder()))
				.findFirst();
	}

	public void adicionarJogador(Jogador jogador) {
		if (jogador != null) {
			jogador.setTime(this);
			this.jogadores.add(jogador);
		}
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getCorUniformePrincipal() {
		return corUniformePrincipal;
	}

	public void setCorUniformePrincipal(String corUniformePrincipal) {
		this.corUniformePrincipal = corUniformePrincipal;
	}

	public String getCorUniformeSecundario() {
		return corUniformeSecundario;
	}

	public void setCorUniformeSecundario(String corUniformeSecundario) {
		this.corUniformeSecundario = corUniformeSecundario;
	}

	@Override
	public String toString() {
		return "Time [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public static Time novoTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal,
			String corUniformeSecundario) {
		return new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
	}

}
