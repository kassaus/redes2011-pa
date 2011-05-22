import gestor.Gestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import mensagens.Mensagem;
import mensagens.util.Codificador;
import mensagens.util.Descodificador;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// criar socket na porta 6500
		ServerSocket server = new ServerSocket(6500);
		System.out.println("servidor iniciado na porta 6500");
		while (true) {// aguarda clientes
			Socket socket = null;
			socket = server.accept();
			Thread t = new Thread(new EchoClientThread(socket));
			t.start();
		}// fim aguarda clientes
	}// fim main

	public static class EchoClientThread implements Runnable {
		private Socket s;

		public EchoClientThread(Socket socket) {
			this.s = socket;
		}

		public void run() {
			// thread
			String stringClient = s.getInetAddress().toString();// IP do cliente
			System.out.println("conectado com " + stringClient);
			try {// inicio try
				final BufferedReader input = new BufferedReader(
						new InputStreamReader(s.getInputStream()));
				final PrintStream output = new PrintStream(s.getOutputStream(),
						true);

				String line;

				while ((line = input.readLine()) != null) {
					boolean sair = false;

					final Mensagem msg = Descodificador.descodificar(line);
					Mensagem resposta = null;
					String strResposta = null;

					switch (msg.getTipoMensagem()) {
					case LISTA:
						if (msg.getAccao() != null) {
							switch (msg.getAccao()) {

							case ADICIONAR:

								if (msg.getAlvo() != null) {
									switch (msg.getAlvo()) {
									case LISTA_BRANCA:
										resposta = Gestor
												.adicionarVotanteBranca(msg);
										strResposta = Codificador
												.codificar(resposta);

										output.println(strResposta);

										break;
									case LISTA_NEGRA:
										resposta = Gestor
												.adicionarVotanteNegra(msg);
										strResposta = Codificador
												.codificar(resposta);

										output.println(strResposta);

										break;

									case LISTA_VOTACAO:
										resposta = Gestor
												.adicionarItemListaVotacao(msg);
										strResposta = Codificador
												.codificar(resposta);

										output.println(strResposta);

										break;

									default:
										break;
									}
								}

								break;
							case LISTAR:

								if (msg.getAlvo() != null) {
									switch (msg.getAlvo()) {
									case LISTA_BRANCA:

										resposta = Gestor
												.listarListaBranca(msg);
										strResposta = Codificador
												.codificar(resposta);

										output.println(strResposta);

										break;
									case LISTA_NEGRA:
										resposta = Gestor.listarListaNegra(msg);
										strResposta = Codificador
												.codificar(resposta);
										output.println(strResposta);

										break;

									case LISTA_VOTACAO:
										resposta = Gestor
												.listarItensVotacao(msg);
										strResposta = Codificador
												.codificar(resposta);
										output.println(strResposta);

										break;

									case LISTA_ONLINE:
										resposta = Gestor
												.listarListaVotantes(msg);
										strResposta = Codificador
												.codificar(resposta);
										output.println(strResposta);

										break;

									case LISTA_RESULTADOS:
										resposta = Gestor.listarListaVotos(msg);
										strResposta = Codificador
												.codificar(resposta);
										output.println(strResposta);

										break;

									default:
										break;
									}
								}

								break;
							case REMOVER:

								if (msg.getAlvo() != null) {
									switch (msg.getAlvo()) {
									case LISTA_VOTACAO:
										resposta = Gestor
												.removerItemListaVotacao(msg);
										strResposta = Codificador
												.codificar(resposta);
										output.println(strResposta);

										break;

									default:
										break;
									}
								}

								break;

							default:
								break;
							}
						}

						break;

					case NUMERO:

						if (msg.getAccao() != null) {
							switch (msg.getAccao()) {
							case TMP_RESTANTE:
								resposta = Gestor.tempoRestante(msg);
								strResposta = Codificador.codificar(resposta);
								output.println(strResposta);

								break;
							case TOTAL:
								resposta = Gestor.totalVotos(msg);
								strResposta = Codificador.codificar(resposta);
								output.println(strResposta);

								break;
							case VENCEDOR:
								resposta = Gestor.itemVencedor(msg);
								strResposta = Codificador.codificar(resposta);
								output.println(strResposta);

								break;

							default:
								break;
							}
						}

						break;

					case VOTO:

						break;

					case SISTEMA:

						if (msg.getAccao() != null) {
							switch (msg.getAccao()) {
							case DESCONECTAR:

								break;

							case SAIR:
								System.out.println("Cliente " + stringClient
										+ " desconectado.");
								output.println("Ligação terminada.");
								sair = true;

								break;

							default:
								break;
							}
						}
						break;

					default:
						break;
					}

					if (sair) {
						break;
					}
				}

				input.close();// termina input
				output.close();// termina output
				s.close();// termina socket
			}// fim try
			catch (Exception e) {
				e.printStackTrace();
			}
		}// fim metodo run
	}// fim classe EchoClientThread
	/*
	 * Gestor gestor = new Gestor(); gestor.imprimeListaTodas();
	 * gestor.votar("192.000.000.001", 0); gestor.imprimeListaTodas();
	 */

	// criar socket na porta 6500
	/*
	 * ServerSocket server = new ServerSocket(6500);
	 * System.out.println("servidor iniciado na porta 6500"); while (true) {//
	 * aguarda clientes Socket socket = null; socket = server.accept();
	 * System.out.println("nova conexao.."); Thread t = new Thread(new
	 * Cliente(socket)); t.start(); }// fim aguarda clientes }
	 * 
	 * public static class Cliente implements Runnable { private Socket s;
	 * 
	 * public Cliente(Socket socket) { this.s = socket; }
	 * 
	 * public void run() { String threadName =
	 * Thread.currentThread().getName();// nome da // thread String stringClient
	 * = s.getInetAddress().toString();// IP do cliente
	 * System.out.println("conectado com " + stringClient); try {// inicio try
	 * BufferedReader input = new BufferedReader( new
	 * InputStreamReader(s.getInputStream())); PrintStream output = new
	 * PrintStream(s.getOutputStream(), true);
	 * 
	 * String line; MetodosServidor serverJobs = new MetodosServidor();
	 * 
	 * while ((line = input.readLine()) != null) {// ciclo de input
	 * System.out.println(stringClient + ": " + threadName + ": " + line);//
	 * imprime mensagem if (line.equalsIgnoreCase("quit")) { break; } else if
	 * (line.equalsIgnoreCase("horas")) {
	 * 
	 * output.println(serverJobs.hournow()); // echo do input // para output }
	 * else if (line.equalsIgnoreCase("data")) {
	 * output.println(serverJobs.datenow()); } else if
	 * (line.equalsIgnoreCase("frase")) { output.println(serverJobs.getFrase());
	 * } else if (line.equalsIgnoreCase("lista")) { for (int i = 0; i <
	 * serverJobs.getFraseTotal(); i++) {
	 * output.println(serverJobs.getFrasePosition(i)); } }
	 * 
	 * else { output.println("Alvo desconhecido!"); } }// fim do ciclo de input
	 * output.println("ate logo!"); input.close();// termina input
	 * output.close();// termina output s.close();// termina socket }// fim try
	 * catch (Exception e) { System.err.println("Erro: " + e); }
	 * System.out.println("cliente " + stringClient + " desconectado!"); }// fim
	 * metodo run }// fim classe EchoClientThread
	 */
}
