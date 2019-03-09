package br.com.codenation.desafio.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

/**
 * Classe responsável pela representação de um Jogador de Futebol.
 * 
 * @author edmilson.santana
 *
 */
public class Jogador {

	private Long id;

	private String nome;

	private LocalDate dataNascimento;

	private Integer nivelHabilidade;

	private BigDecimal salario;

	private Time time;
	
	private Integer idade;

	private Jogador(Long id, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		this.setId(id);
		this.setDataNascimento(dataNascimento);
		this.setNivelHabilidade(nivelHabilidade);
		this.setSalario(salario);
		this.setNome(nome);
		Period period = Period.between(dataNascimento,LocalDate.now());
		this.setIdade(period.getYears());
	}

	public void definirCapitao() {
		if (this.time != null) {
			time.setCapitao(this);
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getNivelHabilidade() {
		return nivelHabilidade;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public void setNivelHabilidade(Integer nivelHabilidade) {
		this.nivelHabilidade = nivelHabilidade;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {
		return "Jogador [id=" + id + ", nome=" + nome + "]";
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
		Jogador other = (Jogador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public static Jogador novoJogador(Long id, String nome, LocalDate dataNascimento, Integer nivelHabilidade,
			BigDecimal salario) {
		return new Jogador(id, nome, dataNascimento, nivelHabilidade, salario);
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

}
