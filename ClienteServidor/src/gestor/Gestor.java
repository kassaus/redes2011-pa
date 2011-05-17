package gestor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Gestor {
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

	List<String> listaAdmin = null;
	List<String> listaBranca = null;
	List<String> listaNegra = null;
	List<String> listaVotacao = null;
	List<String> listaVotantes = null;
	List<String> listaVotos = null;

	Calendar dataInicio = null;
	Calendar dataFim = null;
	Integer duracaoVotacao;

	public Gestor() {
		listaAdmin = new ArrayList<String>();
		listaAdmin.addAll((readFile(getClass().getClassLoader().getResource(
				ADMIN_FILE).getPath())));
		listaBranca = new ArrayList<String>();
		listaBranca.addAll(readFile(getClass().getClassLoader().getResource(
				LISTA_BRANCA_FILE).getPath()));
		listaNegra = new ArrayList<String>();
		listaNegra.addAll(readFile(getClass().getClassLoader().getResource(
				LISTA_NEGRA_FILE).getPath()));
		listaVotacao = new ArrayList<String>();
		listaVotacao.addAll(readFile(getClass().getClassLoader().getResource(
				LISTA_ITEMS_VOTACAO).getPath()));
		listaVotantes = new ArrayList<String>();
		listaVotantes.addAll(readFile(getClass().getClassLoader().getResource(
				LISTA_VOTANTES).getPath()));
		listaVotos = new ArrayList<String>();
		listaVotos.addAll(readFile(getClass().getClassLoader().getResource(
				LISTA_VOTOS).getPath()));

	}

	private ArrayList<String> readFile(String ficheiro) {
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

	public void imprimeLista(List<Object> lista) {
		for (Object item : lista) {
			System.out.println((String) item);
		}
	}

	public void imprimeListaTodas() {
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

	public List<String> listarItensVotacao() {
		return listaVotacao;

	}

	public void adicionarItens(String item) {
		listaVotacao.add(item);

	}

	public void removerItem(int item) {
		listaVotacao.remove(item);

	}

	public List<String> listarVotantes() {
		return listaVotantes;

	}

	public List<String> listaBranca() {
		return listaBranca;

	}

	public void adicionarVotanteListaBranca(String ip) {
		listaBranca.add(ip);

	}

	public List<String> listaNegra() {
		return listaNegra;

	}

	public void adicionarVotanteListaNegra(String ip) {

		listaNegra.add(ip);
	}

	public void desconectarVotante(String ip) {
		// desenvolver....

	}

	public void iniciarVotacao() {
		if (duracaoVotacao != null) {
			dataInicio = Calendar.getInstance();
			dataFim = (Calendar) dataInicio.clone();
			dataFim.add(Calendar.MINUTE, duracaoVotacao);
		} else {
			// enviar mensagem de erro......
		}

	}

	public void definirDuracao(int duracao) {
		duracaoVotacao = duracao;

	}

	public void tempoRestante() {
		long tempRestante = dataFim.getTimeInMillis()
				- Calendar.getInstance().getTimeInMillis();
		int segundos = (int) (tempRestante / 1000) % 60;
		int minutos = (int) ((tempRestante / 1000) / 60);
		int horas = (int) ((tempRestante / 1000) / 3600);

		// enviar o tempo para o Cliente
	}

	public int numeroTotalVotos() {
		return listaVotantes.size();

	}

	public List<String> resultadosVotacao() {
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

	public String itemVencedor() {
		return Collections.max(listaVotos);
	}

	public void votar(String ip, int index) {
		listaVotantes.add(ip);
		listaVotos.set(index, Integer.toString(Integer.parseInt(listaVotos
				.get(index)) + 1));
	}
}
