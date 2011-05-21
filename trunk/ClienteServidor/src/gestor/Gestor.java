package gestor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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
	static List<String> listaVotacao = null;
	static List<String> listaVotantes = null;
	static List<String> listaVotos = null;

	static Calendar dataInicio = null;
	static Calendar dataFim = null;
	static Integer duracaoVotacao;

	static {
		listaAdmin = new ArrayList<String>();
		listaAdmin.addAll((readFile(Gestor.class.getClassLoader().getResource(
				ADMIN_FILE).getPath())));
		listaBranca = new ArrayList<String>();
		listaBranca.addAll(readFile(Gestor.class.getClassLoader().getResource(
				LISTA_BRANCA_FILE).getPath()));
		listaNegra = new ArrayList<String>();
		listaNegra.addAll(readFile(Gestor.class.getClassLoader().getResource(
				LISTA_NEGRA_FILE).getPath()));
		listaVotacao = new ArrayList<String>();
		listaVotacao.addAll(readFile(Gestor.class.getClassLoader().getResource(
				LISTA_ITEMS_VOTACAO).getPath()));
		listaVotantes = new ArrayList<String>();
		listaVotantes.addAll(readFile(Gestor.class.getClassLoader()
				.getResource(LISTA_VOTANTES).getPath()));
		listaVotos = new ArrayList<String>();
		listaVotos.addAll(readFile(Gestor.class.getClassLoader().getResource(
				LISTA_VOTOS).getPath()));
	}

	private static ArrayList<String> readFile(String ficheiro) {
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

	public static List<String> listarItensVotacao() {
		return listaVotacao;

	}

	public static void adicionarItens(String item) {
		listaVotacao.add(item);

	}

	public static void removerItem(int item) {
		listaVotacao.remove(item);

	}

	public static List<String> listarVotantes() {
		return listaVotantes;

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

	public static void adicionarVotanteListaBranca(String ip) {
		listaBranca.add(ip);

	}

	public static List<String> listaNegra() {
		return listaNegra;

	}

	public static void adicionarVotanteListaNegra(String ip) {

		listaNegra.add(ip);
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

	public static void tempoRestante() {
		long tempRestante = dataFim.getTimeInMillis()
				- Calendar.getInstance().getTimeInMillis();
		int segundos = (int) (tempRestante / 1000) % 60;
		int minutos = (int) ((tempRestante / 1000) / 60);
		int horas = (int) ((tempRestante / 1000) / 3600);

		// enviar o tempo para o Cliente
	}

	public static int numeroTotalVotos() {
		return listaVotantes.size();

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

}
