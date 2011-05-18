package entidades;

import gestor.GestorMensagem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import mensagens.Mensagem;
import mensagens.util.Descodificador;

public class Cliente {
	final private String backEndIp;

	private InetAddress ip = null;
	private String mensagem = null;
	private String resposta = null;

	public Cliente(String backEndIp) {
		this.backEndIp = backEndIp;

		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		Thread t = new Thread() {
			@Override
			public void run() {
				DatagramSocket datagramSocket = null;
				DatagramPacket inPacket;
				byte[] buffer;
				int PORT = 9013;

				try {
					while (true) {
						datagramSocket = new DatagramSocket(PORT);
						datagramSocket.setBroadcast(true);
						buffer = new byte[256];
						inPacket = new DatagramPacket(buffer, buffer.length);
						datagramSocket.receive(inPacket);
						String mensagem = new String(inPacket.getData(), 0,
								inPacket.getLength());
						System.out.println("Admin disse: " + mensagem);
						datagramSocket.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		t.start();

	}

	public void desenhaMenuClienteAdmin() {

		System.out.println("************** Net Client ************\n");
		System.out.println("MENU ADMINISTRADOR\n");
		System.out.println("0  - Menu Inicial");
		System.out.println("1  - Listar itens em votação");
		System.out.println("2  - Adicionar item de votação");
		System.out.println("3  - Remover item de votação");
		System.out.println("4  - Listar votantes on-line");
		System.out.println("5  - Enviar mensagem a um votante");
		System.out.println("6  - Enviar mensagem a todos os votantes");
		System.out.println("7  - lista branca de votantes");
		System.out.println("8  - Adicionar votante a lista branca");
		System.out.println("9  - lista negra de votantes");
		System.out.println("10 - Adicionar votante a lista negra");
		System.out.println("11 - Desconectar votante");
		System.out.println("12 - Tempo restante de votação");
		System.out.println("13 - Numero total de votos");
		System.out.println("14 - Listar resultados de votação (%)");
		System.out.println("15 - Item ganhador");
		System.out.println("99 - Sair");
		menuAdmin();

	}

	public void menuAdmin() {
		Scanner teclado = new Scanner(System.in);
		int opcao = 0;
		try {

			System.out.println("Opção? ");
			opcao = teclado.nextInt();
			while (true) {
				switch (opcao) {
				case 0:
					desenhaMenuClienteAdmin();
					break;
				case 1:
					mensagem = GestorMensagem.getListaItens(ip);
					resposta = enviarMensagemTcp(mensagem);

					final Mensagem msg = Descodificador.descodificar(resposta);

					// fazer qq de util

					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					break;
				case 10:
					break;
				case 11:
					break;
				case 12:
					break;
				case 13:
					break;
				case 14:
					break;
				case 15:
					break;
				case 99:
					break;
				default:
					System.out.println("Opção incorrecta!");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			System.out
					.println("Opção inválida tem de introduzir apenas numeros!");
			System.out.println("Prima qualquer tecla para continuar");
			teclado.nextLine();
			teclado.nextLine();
			desenhaMenuClienteAdmin();
		}
	}

	public void desenhaMenuClienteVotante() {

		System.out.println("************** Net Client ************\n");
		System.out.println("MENU VOTANTE\n");
		System.out.println("0  - Menu Inicial");
		System.out.println("1  - Listar itens em votação");
		System.out.println("2  - Tempo restante de votação");
		System.out.println("3  - Votar");
		System.out.println("4  - Numero total de votos");
		System.out.println("5  - Listar resultados de votação (%)");
		System.out.println("6  - Item ganhador");
		System.out.println("99 - Sair");
		menuVotante();

	}

	public void menuVotante() {
		Scanner teclado = new Scanner(System.in);
		int opcao = 0;
		try {

			System.out.println("Opção? ");
			opcao = teclado.nextInt();
			while (true) {
				switch (opcao) {
				case 0:
					desenhaMenuClienteVotante();
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 99:
					break;
				default:
					System.out.println("Opção incorrecta!");
					System.out.println("Prima qualquer tecla para continuar");
					teclado.nextLine();
					teclado.nextLine();
					desenhaMenuClienteVotante();
					break;
				}
			}
		} catch (Exception e) {
			System.out
					.println("Opção inválida tem de introduzir apenas numeros!");
			System.out.println("Prima qualquer tecla para continuar");
			teclado.nextLine();
			teclado.nextLine();
			desenhaMenuClienteVotante();
		}
	}

	public void desenhaMenuWebCliente() {

		System.out.println("************** Web Client ************\n");
		System.out.println("MENU CONSULTA\n");
		System.out.println("0  - Menu Inicial");
		System.out.println("1  - Listar itens em votação");
		System.out.println("2  - Número total de votantes");
		System.out.println("3  - Listar resultados de votação (%)");
		System.out.println("4  - Item ganhador");
		System.out.println("99 - Sair");
		menuConsulta();

	}

	public void menuConsulta() {
		Scanner teclado = new Scanner(System.in);
		int opcao = 0;
		try {

			System.out.println("Opção? ");
			opcao = teclado.nextInt();
			while (true) {
				switch (opcao) {
				case 0:
					desenhaMenuWebCliente();
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 99:
					break;
				default:
					System.out.println("Opção incorrecta!");
					System.out.println("Prima qualquer tecla para continuar");
					teclado.nextLine();
					teclado.nextLine();
					desenhaMenuWebCliente();
					break;
				}
			}
		} catch (Exception e) {
			System.out
					.println("Opção inválida tem de introduzir apenas numeros!");
			System.out.println("Prima qualquer tecla para continuar");
			teclado.nextLine();
			teclado.nextLine();
			desenhaMenuWebCliente();
		}
	}

	public String enviarMensagemTcp(String cmd) throws IOException {

		String host = backEndIp;
		int port = 6500;
		String line;
		Socket socket = new Socket(host, port);
		BufferedReader input = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
		PrintStream output = new PrintStream(socket.getOutputStream(), true);
		output.println(cmd); // envia comando ao servidor
		line = input.readLine();// resposta do servidor
		input.close();// termina input
		output.close();// termina output
		socket.close();// termina socket

		return line;
	}

	public void enviarMensagemUdp(String ip, String msg) {
		DatagramSocket datagramSocket;
		InetAddress host;
		DatagramPacket outPacket, inPacket;
		byte[] buffer;
		int PORT = 9013;

		try {
			host = InetAddress.getByName(ip);
			while (true) {
				buffer = new byte[256];
				datagramSocket = new DatagramSocket();
				outPacket = new DatagramPacket(msg.getBytes(), msg.length(),
						host, PORT);
				datagramSocket.send(outPacket);

				buffer = new byte[256];
				inPacket = new DatagramPacket(buffer, buffer.length);
				datagramSocket.receive(inPacket);
				String response = new String(inPacket.getData(), 0, inPacket
						.getLength());
				System.out.println("Servidor " + host + " disse: " + response);
				datagramSocket.close();

			}
		} catch (Exception e) {

		}
	}
}
