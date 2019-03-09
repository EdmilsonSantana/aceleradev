package desafio;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.desafio.model.Jogador;
import br.com.codenation.desafio.model.Time;
import br.com.codenation.desafio.service.JogadorService;
import br.com.codenation.desafio.service.TimeService;

public class TesteDesafioMeuTime {

	private TimeService timeService;
	private JogadorService jogadorService;

	@Before
	public void before() {
		timeService = new TimeService();
		jogadorService = new JogadorService();
		criarTimes();
		criarJogadores();
	}

	private void criarTimes() {
		timeService.incluir(1L, "Time A", LocalDate.of(1995, 1, 31), "Vermelho", "Verde");
		timeService.incluir(2L, "Time B", LocalDate.of(1884, 2, 01), "Rosa", "Azul");
		timeService.incluir(3L, "Time C", LocalDate.of(2000, 3, 12), "Laranja", "Amarelo");
		timeService.incluir(4L, "Time D", LocalDate.of(1900, 4, 05), "Vermelho", "Preto");
		timeService.incluir(5L, "Time E", LocalDate.of(2002, 5, 23), "Preto", "Branco");
	}

	private void criarJogadores() {

		timeService.adicionarJogador(1L,
				jogadorService.incluir(1L, "Jogador A", LocalDate.of(1995, 05, 20), 25, BigDecimal.valueOf(10000)));
		timeService.adicionarJogador(2L,
				jogadorService.incluir(2L, "Jogador B", LocalDate.of(2000, 1, 01), 0, BigDecimal.valueOf(5000.50)));
		timeService.adicionarJogador(3L,
				jogadorService.incluir(3L, "Jogador C", LocalDate.of(1969, 12, 31), 50, BigDecimal.valueOf(1000000)));
		timeService.adicionarJogador(4L,
				jogadorService.incluir(4L, "Jogador D", LocalDate.of(1969, 8, 24), 10, BigDecimal.valueOf(2000000)));
		timeService.adicionarJogador(5L,
				jogadorService.incluir(5L, "Jogador E", LocalDate.of(1998, 2, 28), 100, BigDecimal.valueOf(5000.80)));
	}

	@Test
	public void testeIncluirTime() {

		Long id = 6L;
		String nome = "Sport Clube do Recife";
		LocalDate dataCriacao = LocalDate.of(1994, 6, 2);
		String corUniformePrincipal = "Vermelho";
		String corUniformeSecundario = "Amarelo";

		timeService.incluir(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
		Time time = timeService.buscarPorId(id);

		assertEquals(id, time.getId());
		assertEquals(nome, time.getNome());
		assertEquals(dataCriacao, time.getDataCriacao());
		assertEquals(corUniformePrincipal, time.getCorUniformePrincipal());
		assertEquals(corUniformeSecundario, time.getCorUniformeSecundario());
	}

	@Test(expected = TimeNaoEncontradoException.class)
	public void testeBuscarTimeNaoEncontrado() {
		timeService.buscarPorId(0L);
	}

	@Test(expected = IdentificadorUtilizadoException.class)
	public void testeIncluirTimeComIdExistente() {
		timeService.incluir(1L, "Sport Clube do Recife", LocalDate.of(1994, 6, 2), "Vermelho", "Preto");
	}

	@Test
	public void testeIncluirJogador() {
		Long id = 6L;
		Long idTime = 1L;
		String nome = "Durval da Silva";
		LocalDate dataNascimento = LocalDate.of(1980, 7, 11);
		Integer nivelHabilidade = 100;
		BigDecimal salario = BigDecimal.valueOf(960000);

		Jogador jogador = jogadorService.incluir(id, nome, dataNascimento, nivelHabilidade, salario);
		timeService.adicionarJogador(idTime, jogador);

		assertEquals(id, jogador.getId());
		assertEquals(nome, jogador.getNome());
		assertEquals(dataNascimento, jogador.getDataNascimento());
		assertEquals(nivelHabilidade, jogador.getNivelHabilidade());
		assertEquals(salario, jogador.getSalario());
		assertEquals(idTime, jogador.getTime().getId());
	}

	@Test(expected = TimeNaoEncontradoException.class)
	public void testeIncluirJogadorComTimeNaoEncontrado() {

		Long idTime = 0L;

		Jogador jogador = jogadorService.incluir(7L, "Durval da Silva", LocalDate.of(1980, 7, 11), 100,
				BigDecimal.valueOf(960000));

		timeService.adicionarJogador(idTime, jogador);
	}

	@Test(expected = IdentificadorUtilizadoException.class)
	public void testeIncluirJogadorComIdExistente() {

		Long idTime = 1L;

		Jogador jogador = jogadorService.incluir(1L, "Durval da Silva", LocalDate.of(1980, 7, 11), 100,
				BigDecimal.valueOf(960000));

		timeService.adicionarJogador(idTime, jogador);
	}

	@Test
	public void testeDefinirCapitao() {
		Long idJogador = 1L;
		Long idTime = 1L;

		jogadorService.definirCapitao(idJogador);

		Time time = timeService.buscarPorId(idTime);
		Jogador jogador = jogadorService.buscarPorId(idJogador);

		assertEquals(time.getCapitao(), jogador);
	}

	@Test(expected = JogadorNaoEncontradoException.class)
	public void testeDefinirCapitaoJogadorNaoEncontrado() {
		Long idJogador = 0L;

		jogadorService.definirCapitao(idJogador);
	}

	@Test(expected = CapitaoNaoInformadoException.class)
	public void testeBuscarCapitaoTimeNaoInformado() {
		Long idTime = 1L;

		timeService.buscarCapitao(idTime);
	}

	@Test
	public void testeBuscarCapitaoTime() {
		Long idTime = 1L;
		Long idJogador = 1L;

		jogadorService.definirCapitao(idJogador);

		Jogador capitao = timeService.buscarCapitao(idTime);
		Jogador jogador = jogadorService.buscarPorId(idJogador);

		assertEquals(capitao.getId(), jogador.getId());
	}

	@Test(expected = TimeNaoEncontradoException.class)
	public void testeBuscarCapitaoTimeNaoEncontrado() {
		Long idTime = 0L;

		timeService.buscarCapitao(idTime);
	}

	@Test
	public void testeBuscarNomeJogador() {

		String nome = jogadorService.buscarNomeJogador(1L);

		assertEquals(nome, "Jogador A");
	}

	@Test(expected = JogadorNaoEncontradoException.class)
	public void testeBuscarNomeJogadorNaoEncontrado() {

		jogadorService.buscarNomeJogador(0L);
	}

	@Test
	public void testeBuscarNomeTime() {

		String nome = timeService.buscarNomeTime(1L);

		assertEquals(nome, "Time A");
	}

	@Test(expected = TimeNaoEncontradoException.class)
	public void testeBuscarNomeTimeNaoEncontrado() {

		timeService.buscarNomeTime(0L);
	}

	@Test
	public void testeBuscarJogadoresDoTime() {

		Long idTime = 1L;

		timeService.adicionarJogador(idTime, jogadorService.incluir(8L, "Durval da Silva", LocalDate.of(1980, 7, 11),
				100, BigDecimal.valueOf(960000)));

		timeService.adicionarJogador(idTime, jogadorService.incluir(7L, "Alessandro Beti Rosa",
				LocalDate.of(1977, 4, 9), 100, BigDecimal.valueOf(960000)));

		List<Long> idsJogadoresEsperado = Arrays.asList(1L, 7L, 8L);
		List<Long> idsJogadoresObtido = timeService.buscarJogadoresDoTime(idTime);

		assertEquals(idsJogadoresEsperado, idsJogadoresObtido);
	}

	@Test(expected = TimeNaoEncontradoException.class)
	public void testeBuscarJogadoresDoTimeNaoEncontrado() {
		timeService.buscarJogadoresDoTime(0L);
	}

	@Test
	public void testeBuscarMelhorJogadorDoTime() {

		Long idTime = 1L;

		timeService.adicionarJogador(idTime, jogadorService.incluir(8L, "Durval da Silva", LocalDate.of(1980, 7, 11),
				100, BigDecimal.valueOf(960000)));

		Jogador melhorJogador = timeService.buscarMelhorJogadorDoTime(idTime);

		assertEquals("Durval da Silva", melhorJogador.getNome());
		assertEquals(Integer.valueOf(100), melhorJogador.getNivelHabilidade());
	}

	@Test(expected = TimeNaoEncontradoException.class)
	public void testeBuscarMelhorJogadorTimeNaoEncontrado() {
		timeService.buscarMelhorJogadorDoTime(0L);
	}

	@Test
	public void testeBuscarJogadorMaisVelho() {

		Long idTime = 1L;

		timeService.adicionarJogador(idTime, jogadorService.incluir(8L, "Durval da Silva", LocalDate.of(1980, 7, 11),
				100, BigDecimal.valueOf(960000)));

		timeService.adicionarJogador(idTime, jogadorService.incluir(7L, "Alessandro Beti Rosa",
				LocalDate.of(1977, 4, 9), 100, BigDecimal.valueOf(960000)));

		Jogador melhorJogador = timeService.buscarJogadorMaisVelho(idTime);

		assertEquals("Alessandro Beti Rosa", melhorJogador.getNome());
	}

	@Test
	public void testeBuscarJogadorMaisVelhoEmpate() {

		Long idTime = 1L;

		timeService.adicionarJogador(idTime, jogadorService.incluir(7L, "Durval da Silva", LocalDate.of(1980, 7, 11),
				100, BigDecimal.valueOf(960000)));

		timeService.adicionarJogador(idTime, jogadorService.incluir(8L, "Alessandro Beti Rosa",
				LocalDate.of(1980, 7, 11), 100, BigDecimal.valueOf(960000)));

		Jogador melhorJogador = timeService.buscarJogadorMaisVelho(idTime);

		assertEquals("Durval da Silva", melhorJogador.getNome());
	}

	@Test
	public void testeBuscarTimes() {
		List<Long> idsTimesEsperado = Arrays.asList(1L, 2L, 3L, 4L, 5L);
		List<Long> idsTimesObtido = timeService.buscarIdsTimes();

		assertEquals(idsTimesEsperado, idsTimesObtido);
	}

	@Test
	public void testeBuscarTimesNaoCadastrados() {

		timeService.clear();

		assertEquals(new ArrayList<Long>(), timeService.buscarIdsTimes());
	}

	@Test
	public void testeBuscarJogadorMaiorSalario() {

		Long idTime = 1L;

		timeService.adicionarJogador(idTime, jogadorService.incluir(7L, "Durval da Silva", LocalDate.of(1980, 7, 11),
				100, BigDecimal.valueOf(1000000)));

		timeService.adicionarJogador(idTime, jogadorService.incluir(8L, "Alessandro Beti Rosa",
				LocalDate.of(1977, 4, 9), 100, BigDecimal.valueOf(1000000.5)));

		Jogador jogador = timeService.buscarJogadorMaiorSalario(idTime);

		assertEquals(jogador.getNome(), "Alessandro Beti Rosa");
		assertEquals(jogador.getSalario(), BigDecimal.valueOf(1000000.5));
	}

	@Test
	public void testeBuscarJogadorEmpateDoMaiorSalario() {

		Long idTime = 3L;

		timeService.adicionarJogador(idTime, jogadorService.incluir(7L, "Durval da Silva", LocalDate.of(1980, 7, 11),
				100, BigDecimal.valueOf(960000)));

		timeService.adicionarJogador(idTime, jogadorService.incluir(8L, "Alessandro Beti Rosa",
				LocalDate.of(1977, 4, 9), 100, BigDecimal.valueOf(1000000)));

		Jogador jogador = timeService.buscarJogadorMaiorSalario(idTime);

		assertEquals(jogador.getNome(), "Jogador C");
		assertEquals(jogador.getSalario(), BigDecimal.valueOf(1000000));
	}

	@Test
	public void testeBuscarSalarioJogador() {

		BigDecimal salario = jogadorService.buscarSalarioDoJogador(2L);

		assertEquals(BigDecimal.valueOf(5000.50), salario);
	}

	@Test
	public void testeBuscarTopJogadores() {

		Integer top = 5;
		List<Long> idsJogadoresEsperado = Arrays.asList(5L, 3L, 1L, 4L, 2L);
		List<Long> idsJogadoresObtido = jogadorService.getTopJogadores(top);

		assertEquals(top, Integer.valueOf(idsJogadoresObtido.size()));
		assertEquals(idsJogadoresEsperado, idsJogadoresObtido);
	}

	@Test
	public void testeBuscarTopJogadoresComEmpate() {

		Integer top = 5;

		jogadorService.incluir(7L, "Durval da Silva", LocalDate.of(1980, 7, 11), 100, BigDecimal.valueOf(960000));
		List<Long> idsJogadoresEsperado = Arrays.asList(5L, 7L, 3L, 1L, 4L);
		List<Long> idsJogadoresObtido = jogadorService.getTopJogadores(top);

		assertEquals(top, Integer.valueOf(idsJogadoresObtido.size()));
		assertEquals(idsJogadoresEsperado, idsJogadoresObtido);
	}

	@Test
	public void testeBuscarTopJogadoresNaoCadastrados() {

		jogadorService.clear();

		assertEquals(new ArrayList<>(), jogadorService.getTopJogadores(5));
	}

	@Test
	public void testeBuscarCorCamisaTimeDeForaCamisasDiferentes() {

		assertEquals("Rosa", timeService.buscarCorCamisaTimeDeFora(1L, 2L));
	}

	@Test
	public void testeBuscarCorCamisaTimeDeForaCamisasIguais() {

		assertEquals("Preto", timeService.buscarCorCamisaTimeDeFora(1L, 4L));
	}
}
