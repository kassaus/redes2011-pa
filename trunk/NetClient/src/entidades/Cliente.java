package entidades;

import gestor.GestorMensagem;

import java.util.Scanner;

public class Cliente {

	public Cliente() {

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
			while(true) {
				switch (opcao) {
				case 0:
					desenhaMenuClienteAdmin();
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
			System.out.println("Opção inválida tem de introduzir apenas numeros!");
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
			while(true) {
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
			System.out.println("Opção inválida tem de introduzir apenas numeros!");
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
			while(true) {
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
			System.out.println("Opção inválida tem de introduzir apenas numeros!");
			System.out.println("Prima qualquer tecla para continuar");
			teclado.nextLine();
			teclado.nextLine();
			desenhaMenuWebCliente();
		}
	}

}
