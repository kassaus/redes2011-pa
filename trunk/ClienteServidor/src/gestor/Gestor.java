package gestor;

import enumerados.Accao;
import enumerados.Erro;
import enumerados.Ok;
import enumerados.TipoMensagem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import mensagens.Mensagem;

public abstract class Gestor {
	private static final String RESOURCES_PATH = "resources/";

	private static final String ADMIN_FILE = RESOURCES_PATH + "admin.dat";
	private static final String LISTA_BRANCA_FILE = RESOURCES_PATH
			+ "listaBranca.dat";
	private static final String LISTA_NEGRA_FILE = RESOURCES_PATH
			+ "listaNegra.dat";
	private static final String LISTA_ITEMS_VOTACAO = RESOURCES_PATH
			+ "listaItemsVotacao.dat";
	private static final String LISTA_VOTANTES = RESOURCES_PATH
			+ "listaVotantes.dat";
	private static final String LISTA_VOTOS = RESOURCES_PATH + "votos.dat";

	static List<String> listaAdmin = null;
	static List<String> listaBranca = null;
	static List<String> listaNegra = null;
	static List<String> listaVotantes = null;
	static List<String> listaVotos = null;
	static HashMap<String, String> itensVotacao = null;
	static List<String> siglas = null;

	static Calendar dataInicio = null;
	static Calendar dataFim = null;
	static Integer duracaoVotacao;

	static {
		listaAdmin = new ArrayList<String>();
		listaAdmin.addAll((readFileToList(Gestor.class.getClassLoader()
				.getResource(ADMIN_FILE).getPath())));
		listaBranca = new ArrayList<String>();
		listaBranca.addAll(readFileToList(Gestor.class.getClassLoader()
				.getResource(LISTA_BRANCA_FILE).getPath()));
		listaNegra = new ArrayList<String>();
		listaNegra.addAll(readFileToList(Gestor.class.getClassLoader()
				.getResource(LISTA_NEGRA_FILE).getPath()));

		listaVotantes = new ArrayList<String>();
		listaVotantes.addAll(readFileToList(Gestor.class.getClassLoader()
				.getResource(LISTA_VOTANTES).getPath()));
		listaVotos = new ArrayList<String>();
		listaVotos.addAll(readFileToList(Gestor.class.getClassLoader()
				.getResource(LISTA_VOTOS).getPath()));
		itensVotacao = new HashMap<String, String>();
		itensVotacao.putAll(readFileToMap(Gestor.class.getClassLoader()
				.getResource(LISTA_ITEMS_VOTACAO).getPath()));

		actualizaListaSiglas();

		// final String sigla = siglas.get(2);
		// final String descricao = itensVotacao.get(sigla);
	}

	private static ArrayList<String> readFileToList(String ficheiro) {
		ArrayList<String> dados = new ArrayList<String>();
		String linha;
		try {
			FileReader leitor = new FileReader(ficheiro);
			BufferedReader in = new BufferedReader(leitor);
			while ((linha = in.readLine()) != null) {
				dados.add(linha);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dados;
	}

	private static HashMap<String, String> readFileToMap(String ficheiro) {
		HashMap<String, String> dados = new HashMap<String, String>();
		String linha;
		try {
			FileReader leitor = new FileReader(ficheiro);
			BufferedReader in = new BufferedReader(leitor);
			while ((linha = in.readLine()) != null) {
				final StringTokenizer tok = new StringTokenizer(linha, "§");
				dados.put(tok.nextToken(), tok.nextToken());
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dados;
	}

	public static Mensagem listarListaBranca(final Mensagem msg) {
		final Mensagem mensagem = new Mensagem(msg);

		StringBuilder texto = new StringBuilder();

		for (String ip : listaBranca) {
			texto.append(";");
			texto.append(ip);
		}

		texto.deleteCharAt(0);

		mensagem.setTexto(texto.toString());

		return mensagem;

	}

	public static Mensagem listarListaNegra(final Mensagem msg) {
		final Mensagem mensagem = new Mensagem(msg);
		StringBuilder texto = new StringBuilder();
		try {
			for (String ip : listaNegra) {
				texto.append(";");
				texto.append(ip);
			}
			texto.deleteCharAt(0);
			mensagem.setTexto(texto.toString());
		} catch (Exception ex) {
			mensagem.setTexto(Erro.LISTAR.getMensagem());
		}
		return mensagem;

	}

	public static Mensagem listarListaVotantes(final Mensagem msg) {
		final Mensagem mensagem = new Mensagem(msg);
		StringBuilder texto = new StringBuilder();
		for (String ip : listaVotantes) {
			texto.append(";");
			texto.append(ip);
		}
		texto.deleteCharAt(0);
		mensagem.setTexto(texto.toString());
		return mensagem;

	}

	public static Mensagem listarListaVotos(final Mensagem msg) {
		final Mensagem mensagem = new Mensagem(msg);
		StringBuilder texto = new StringBuilder();
		for (String resultado : resultadosVotacao()) {
			texto.append(";");
			texto.append(resultado);
		}
		texto.deleteCharAt(0);
		mensagem.setTexto(texto.toString());
		return mensagem;

	}

	public static Mensagem listarItensVotacao(final Mensagem msg) {
		final Mensagem mensagem = new Mensagem(msg);
		StringBuilder texto = new StringBuilder();

		for (String sigla : siglas) {
			texto.append(";");
			texto.append(sigla);
			texto.append(" - ");
			texto.append(itensVotacao.get(sigla));
		}
		texto.deleteCharAt(0);
		mensagem.setTexto(texto.toString());
		return mensagem;

	}

	public static Mensagem adicionarItemListaVotacao(final Mensagem msg) {
		final Mensagem mensagem = new Mensagem();
		mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
		mensagem.setIp(msg.getIp());
		final StringTokenizer tok = new StringTokenizer(msg.getTexto(), "§");
		try {
			final String sigla = tok.nextToken();
			final String descricao = tok.nextToken();
			itensVotacao.put(sigla, descricao);
			actualizaListaSiglas();

			mensagem.setTexto(Ok.CONFIRMACAO.getMensagem() + ": "
					+ Ok.ADICIONAR.getMensagem());
		} catch (IndexOutOfBoundsException ex) {
			mensagem.setTexto(Erro.ADICIONAR.getCodigo() + ": "
					+ Erro.ADICIONAR.getMensagem());
		}

		return mensagem;
	}

	public static Mensagem removerItemListaVotacao(final Mensagem msg) {
		final Mensagem mensagem = new Mensagem();
		mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
		mensagem.setIp(msg.getIp());

		String sigla = null;
		sigla = siglas.get(Integer.parseInt(msg.getTexto()));

		try {
			itensVotacao.remove(sigla);
			actualizaListaSiglas();

			mensagem.setTexto(Ok.CONFIRMACAO.getMensagem() + ": "
					+ Ok.REMOVER.getMensagem());
		} catch (IndexOutOfBoundsException ex) {
			mensagem.setTexto(Erro.REMOVER.getCodigo() + ": "
					+ Erro.REMOVER.getMensagem());
		}

		return mensagem;
	}

	private static void actualizaListaSiglas() {
		// Actualiza a lista de siglas com as Keys da HasMap.
		siglas = Arrays.asList(itensVotacao.keySet().toArray(new String[0]));
	}

	public static Mensagem itemVencedor(final Mensagem msg) {
		final Mensagem mensagem = new Mensagem();
		mensagem.setIp(msg.getIp());
		try {
			itemVencedor();
			mensagem.setTexto(Ok.CONFIRMACAO.getMensagem() + ":"
					+ Ok.VENCEDOR.getMensagem());
		} catch (Exception ex) {

			mensagem.setTexto(Erro.VENCEDOR.getCodigo() + ": "
					+ Erro.VENCEDOR.getMensagem());

		}
		return mensagem;

	}

	public static Mensagem adicionarVotanteBranca(Mensagem msg) {
		final Mensagem mensagem = new Mensagem();
		mensagem.setIp(msg.getIp());
		try {
			listaBranca.add(msg.getTexto());
			mensagem.setTexto(Ok.CONFIRMACAO.getMensagem() + ":"
					+ msg.getTexto() + " " + Ok.ADICIONAR.getMensagem());
		} catch (Exception ex) {

			mensagem.setTexto(Erro.ADICIONAR.getCodigo() + ": "
					+ Erro.ADICIONAR.getMensagem());

		}
		return mensagem;
	}

	public static Mensagem adicionarVotanteNegra(Mensagem msg) {
		final Mensagem mensagem = new Mensagem();
		mensagem.setIp(msg.getIp());
		try {
			listaNegra.add(msg.getTexto());
			mensagem.setTexto(Ok.CONFIRMACAO.getMensagem() + ":"
					+ msg.getTexto() + " " + Ok.ADICIONAR.getMensagem());
		} catch (Exception ex) {

			mensagem.setTexto(Erro.ADICIONAR.getCodigo() + ": "
					+ Erro.ADICIONAR.getMensagem());

		}
		return mensagem;
	}

	public static Mensagem tempoRestante(Mensagem msg) {
		final Mensagem mensagem = new Mensagem(msg);

		if (dataFim != null) {
			long tempRestante = dataFim.getTimeInMillis()
					- Calendar.getInstance().getTimeInMillis();
			int segundos = (int) (tempRestante / 1000) % 60;
			int minutos = (int) ((tempRestante / 1000) / 60);
			int horas = (int) ((tempRestante / 1000) / 3600);
			mensagem
					.setTexto(horas + " h " + minutos + " m " + segundos + " s");

		} else {
			mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
			mensagem.setAccao(Accao.TMP_RESTANTE);
			mensagem.setTexto(Erro.TEMPO_VOTACAO.getCodigo() + " "
					+ Erro.TEMPO_VOTACAO.getMensagem());
		}
		return mensagem;
	}

	public static Mensagem totalVotos(Mensagem msg) {
		final Mensagem mensagem = new Mensagem(msg);
		mensagem.setTexto(String.valueOf(listaVotantes.size()));

		return mensagem;
	}

	public static void desconectarVotante(String ip) {
		// desenvolver....

	}

	public static void iniciarVotacao() {
		if (duracaoVotacao != null) {
			dataInicio = Calendar.getInstance();
			dataFim = (Calendar) dataInicio.clone();
			dataFim.add(Calendar.MINUTE, duracaoVotacao);
		} else {
			// enviar mensagem de erro......
		}

	}

	public static void definirDuracao(int duracao) {
		duracaoVotacao = duracao;

	}

	public static List<String> resultadosVotacao() {
		List<String> percentagens = new ArrayList<String>();
		int totalVotos = 0;
		double valorPercentagem = 0;
		DecimalFormat decimalFormato = new DecimalFormat("##.#");
		for (String item : listaVotos) {
			totalVotos += Integer.parseInt(item);
		}
		for (String item : listaVotos) {
			valorPercentagem = (Integer.parseInt(item) / 240.0) * 100.0;
			percentagens.add(decimalFormato.format(valorPercentagem) + "%");
		}

		return percentagens;
	}

	public static String itemVencedor() {
		return Collections.max(listaVotos);
	}

	public static void votar(String ip, int index) {
		listaVotantes.add(ip);
		listaVotos.set(index, Integer.toString(Integer.parseInt(listaVotos
				.get(index)) + 1));
	}

	public static void imprimeLista(List<Object> lista) {
		for (Object item : lista) {
			System.out.println((String) item);
		}
	}

	public static void imprimeListaTodas() {
		System.out.println("*********Lista Admin*******");
		for (String item : listaAdmin) {
			System.out.println(item);
		}

		System.out.println("*********Lista Branca*******");
		for (String item : listaBranca) {
			System.out.println(item);
		}

		System.out.println("*********Lista Negra*******");
		for (String item : listaNegra) {
			System.out.println(item);
		}

		System.out.println("*********Lista Votos*******");
		for (String item : listaVotos) {
			System.out.println(item);
		}

		System.out.println("*********Lista Resultados Percentagens*******");
		for (String item : resultadosVotacao()) {
			System.out.println(item);
		}

		System.out.println("*********Lista Votantes*******");
		for (String item : listaVotantes) {
			System.out.println(item);
		}

		System.out.println("*********Lista Vencedor*******");
		System.out.println(itemVencedor());
	}

	public static List<String> listarListaVotantes() {
		return listaVotantes;

	}

}
