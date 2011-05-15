import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import serverUtils.ServerMetodos;

public class Server {

	public static void main(String args[]) throws Exception {
		// criar socket na porta 6500
		ServerSocket server = new ServerSocket(6500);
		System.out.println("servidor iniciado na porta 6500");
		while (true) {// aguarda clientes
			Socket socket = null;
			socket = server.accept();
			System.out.println("nova conexao..");
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
			String threadName = Thread.currentThread().getName();// nome da
			// thread
			String stringClient = s.getInetAddress().toString();// IP do cliente
			System.out.println("conectado com " + stringClient);
			try {// inicio try
				BufferedReader input = new BufferedReader(
						new InputStreamReader(s.getInputStream()));
				PrintStream output = new PrintStream(s.getOutputStream(), true);

				String line;
				ServerMetodos serverJobs = new ServerMetodos();

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
}// fim classe EchoServerThread
