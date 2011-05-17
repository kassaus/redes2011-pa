import gestor.Gestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import serverUtils.MetodosServidor;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Gestor gestor = new Gestor();
		gestor.imprimeListaTodas();
		gestor.votar("192.000.000.001", 0);
		gestor.imprimeListaTodas();

		// criar socket na porta 6500
		ServerSocket server = new ServerSocket(6500);
		System.out.println("servidor iniciado na porta 6500");
		while (true) {// aguarda clientes
			Socket socket = null;
			socket = server.accept();
			System.out.println("nova conexao..");
			Thread t = new Thread(new Cliente(socket));
			t.start();
		}// fim aguarda clientes
	}

	public static class Cliente implements Runnable {
		private Socket s;

		public Cliente(Socket socket) {
			this.s = socket;
		}

		public void run() {
			String threadName = Thread.currentThread().getName();// nome da
			// thread
			String stringClient = s.getInetAddress().toString();// IP do cliente
			System.out.println("conectado com " + stringClient);
			try {// inicio try
				BufferedReader input = new BufferedReader(
						new InputStreamReader(s.getInputStream()));
				PrintStream output = new PrintStream(s.getOutputStream(), true);

				String line;
				MetodosServidor serverJobs = new MetodosServidor();

				while ((line = input.readLine()) != null) {// ciclo de input
					System.out.println(stringClient + ": " + threadName + ": "
							+ line);// imprime mensagem
					if (line.equalsIgnoreCase("quit")) {
						break;
					} else if (line.equalsIgnoreCase("horas")) {

						output.println(serverJobs.hournow()); // echo do input
						// para output
					} else if (line.equalsIgnoreCase("data")) {
						output.println(serverJobs.datenow());
					} else if (line.equalsIgnoreCase("frase")) {
						output.println(serverJobs.getFrase());
					} else if (line.equalsIgnoreCase("lista")) {
						for (int i = 0; i < serverJobs.getFraseTotal(); i++) {
							output.println(serverJobs.getFrasePosition(i));
						}
					}

					else {
						output.println("Comando desconhecido!");
					}
				}// fim do ciclo de input
				output.println("ate logo!");
				input.close();// termina input
				output.close();// termina output
				s.close();// termina socket
			}// fim try
			catch (Exception e) {
				System.err.println("Erro: " + e);
			}
			System.out.println("cliente " + stringClient + " desconectado!");
		}// fim metodo run
	}// fim classe EchoClientThread

}
