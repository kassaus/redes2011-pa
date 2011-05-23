package entidades;

import gestor.Gestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

import mensagens.Mensagem;
import mensagens.util.Descodificador;
import util.ConstrutorMensagens;

public class Cliente {

    final private String backEndIp;

    private InetAddress ip = null;
    private String mensagem = null;
    private Mensagem resposta = null;
    private Socket socket = null;
    private BufferedReader input = null;
    private PrintStream output = null;

    final private int port = 6500;

    public Cliente(final String backEndIp) {
        this.backEndIp = backEndIp;

        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String resposta, line;

        try {
            connectToServer();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t = new Thread() {

            @Override
            public void run() {

                /*
                 * 
                 * DatagramSocket datagramSocket = null; DatagramPacket inPacket; byte[] buffer; int PORT = 9013;
                 * 
                 * try { while (true) { datagramSocket = new DatagramSocket(PORT); datagramSocket.setBroadcast(true); buffer = new byte[256]; inPacket
                 * = new DatagramPacket(buffer, buffer.length); datagramSocket.receive(inPacket); String mensagem = new String(inPacket.getData(), 0,
                 * inPacket.getLength()); System.out.println("Admin disse: " + mensagem); datagramSocket.close(); } } catch (Exception e) {
                 * e.printStackTrace(); }
                 */
            }
        };

        t.start();

    }

    private void connectToServer() throws UnknownHostException, IOException {
        socket = new Socket(this.backEndIp, port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintStream(socket.getOutputStream(), true);
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
        System.out.println("7  - Lista branca de votantes");
        System.out.println("8  - Adicionar votante a lista branca");
        System.out.println("9  - Lista negra de votantes");
        System.out.println("10 - Adicionar votante a lista negra");
        System.out.println("11 - Desconectar votante");
        System.out.println("12 - Tempo restante de votação");
        System.out.println("13 - Numero total de votos");
        System.out.println("14 - Listar resultados de votação (%)");
        System.out.println("15 - Item ganhador");
        System.out.println("16 - Definir duração");
        System.out.println("17 - Iniciar votação");
        System.out.println("99 - Sair");
        menuAdmin();

    }

    public void menuAdmin() {
        Scanner teclado = new Scanner(System.in);
        int opcao = 0;
        try {
            while (true) {
                boolean sair = false;

                opcao = getOpcao(teclado);

                switch (opcao) {
                case 0:
                    desenhaMenuClienteAdmin();
                    break;
                case 1:
                    mostraListaVotacao();
                    break;
                case 2:
                    // Adicionar item de votação
                    mostraListaVotacao();

                    System.out.println("Insira a nova sigla: ");
                    teclado.nextLine();
                    final String sigla = teclado.nextLine();

                    System.out.println("Insira a nova descricao: ");
                    final String descricao = teclado.nextLine();

                    mensagem = ConstrutorMensagens.adicionarItem(sigla, descricao, ip.getHostAddress());
                    resposta = enviarMensagemTcp(mensagem);

                    System.out.println(resposta.getTexto());

                    break;
                case 3:
                    // Remover item de votação
                    mostraListaVotacao();
                    Integer index = null;
                    System.out.println("Insira o numero do item a remover: ");

                    while (index == null) {
                        try {
                            teclado.nextLine();
                            index = teclado.nextInt();
                        } catch (InputMismatchException e) {
                            index = null;
                            System.out.println("O número que introduziu é inválido.\n");
                            System.out.println("Insira de novo o numero do item a remover: ");
                        }
                    }

                    System.out.println("Confirma a remoção do item  " + index + "? s/n");
                    String escolha = teclado.next();
                    while (!escolha.equalsIgnoreCase("S") && !escolha.equalsIgnoreCase("N")) {
                        System.out.println("Confirme a opção, escrevendo s/n.");
                        System.out.println("Confirma a remoção do item  " + index + "? s/n");
                        escolha = teclado.next();
                    }

                    if (escolha.equalsIgnoreCase("N")) {
                        break;
                    }

                    mensagem = ConstrutorMensagens.removerItemLista(index.toString(), ip.getHostAddress());
                    resposta = enviarMensagemTcp(mensagem);

                    System.out.println(resposta.getTexto());

                    break;
                case 4:
                    mensagem = ConstrutorMensagens.obterListaVotantes(ip.getHostAddress());
                    resposta = enviarMensagemTcp(mensagem);
                    System.out.println("Lista Votantes on-line:");
                    listar(resposta.getTexto());

                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    // Lista branca de votantes
                    mensagem = ConstrutorMensagens.obterListaBranca(ip.getHostAddress());
                    resposta = enviarMensagemTcp(mensagem);
                    System.out.println("Lista Branca:");
                    listar(resposta.getTexto());
                    /*
                     * System.out.println(resposta.getTipoMensagem().ordinal() + "|" + resposta.getIp() + "|" + resposta.getAccao().ordinal() + "|" +
                     * resposta.getAlvo().ordinal() + "|" + resposta.getTexto());
                     */

                    break;
                case 8:
                    // Adicionar votante a lista branca
                    System.out.println("Insira 0 IP do votante:");
                    teclado.nextLine();
                    final String ipVotante = teclado.nextLine();
                    mensagem = ConstrutorMensagens.adicionarVotanteBranca(ip.getHostAddress(), ipVotante);
                    resposta = enviarMensagemTcp(mensagem);
                    System.out.println(resposta.getTexto());

                    break;
                case 9:
                    // Lista negra de votantes
                    mensagem = ConstrutorMensagens.obterListaNegra(ip.getHostAddress());
                    resposta = enviarMensagemTcp(mensagem);

                    System.out.println("Lista Negra:");
                    listar(resposta.getTexto());

                    break;
                case 10:
                    // Adicionar votante a lista negra
                    System.out.println("Insira o IP do votante:");
                    teclado.nextLine();
                    final String ipVotanteNegra = teclado.nextLine();
                    mensagem = ConstrutorMensagens.adicionarVotanteNegra(ip.getHostAddress(), ipVotanteNegra);
                    resposta = enviarMensagemTcp(mensagem);
                    System.out.println(resposta.getTexto());
                    break;
                case 11:
                    // Desconectar votante
                    index = null;
                    escolha = null;
                    System.out.println("Insira o numero do votante a desconectar: ");

                    while (index == null) {
                        try {
                            teclado.nextLine();
                            index = teclado.nextInt();
                        } catch (InputMismatchException e) {
                            index = null;
                            System.out.println("O número que introduziu é inválido.\n");
                            System.out.println("Insira de novo do votante a desconectar: ");
                        }
                    }

                    System.out.println("Confirma a remoção do item  " + index + "? s/n");
                    escolha = teclado.next();
                    while (!escolha.equalsIgnoreCase("S") && !escolha.equalsIgnoreCase("N")) {
                        System.out.println("Confirme a opção, escrevendo s/n.");
                        System.out.println("Confirma a remoção do item  " + index + "? s/n");
                        escolha = teclado.next();
                    }

                    if (escolha.equalsIgnoreCase("N")) {
                        break;
                    }

                    mensagem = ConstrutorMensagens.removerVotante(index.toString(), ip.getHostAddress());
                    resposta = enviarMensagemTcp(mensagem);

                    System.out.println(resposta.getTexto());
                    sair = true;

                    break;
                case 12:
                    // Tempo restante de votação
                    tempoRestante();
                    break;
                case 13:
                    // Numero total de votos
                    totalVotos();
                    break;
                case 14:
                    // Listar resultados de votação (%)
                    resultadosVotacao();
                    break;
                case 15:
                    // Item ganhador
                    itemVencedor();
                    break;
                case 16:
                    // Defenir duração

                    Integer duracao = null;
                    escolha = null;
                    Integer tempoVotacao = Gestor.getDuracaoVotacao();
                    if (tempoVotacao == null) {
                        System.out.println("Não existe nenhuma duração definida");
                    } else {
                        System.out.println("Tempo da duração actual - " + tempoVotacao);
                    }

                    System.out.println("Insira o tempo de votação em minutos: ");

                    while (duracao == null) {
                        try {
                            teclado.nextLine();
                            duracao = teclado.nextInt();
                        } catch (InputMismatchException e) {
                            index = null;
                            System.out.println("O número que introduziu é inválido.\n");
                            System.out.println("Insira de novo o tempo de votação em minutos: ");
                        }
                    }

                    System.out.println("Confirma o tempo de votação - " + duracao + "? s/n");
                    escolha = teclado.next();
                    while (!escolha.equalsIgnoreCase("S") && !escolha.equalsIgnoreCase("N")) {
                        System.out.println("Confirme a opção, escrevendo s/n.");
                        System.out.println("Confirma o tempo de votação - " + duracao + "? s/n");
                        escolha = teclado.next();
                    }

                    if (escolha.equalsIgnoreCase("N")) {
                        break;
                    }
                    mensagem = ConstrutorMensagens.duracao(ip.getHostAddress(), duracao.toString());
                    resposta = enviarMensagemTcp(mensagem);
                    System.out.println(resposta.getTexto());
                    break;
                case 17:
                    // Iniciar votação

                    break;
                case 99:
                    sair = deveSair();
                    break;
                default:
                    opcaoInvalida(teclado);
                    desenhaMenuClienteAdmin();
                    break;
                }

                if (sair) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
                socket.close();
            } catch (Exception ex) {

            }
        }
    }

    private void itemVencedor() {
        mensagem = ConstrutorMensagens.obterItemVencedor(ip.getHostAddress());
        resposta = enviarMensagemTcp(mensagem);
        System.out.println("Vencedor da votação");
        System.out.println(resposta.getTexto());
    }

    private void resultadosVotacao() {
        mensagem = ConstrutorMensagens.obterListaVotos(ip.getHostAddress());
        resposta = enviarMensagemTcp(mensagem);
        System.out.println("Lista Resultados:");
        final StringTokenizer tok = new StringTokenizer(resposta.getTexto(), ";");
        while (tok.hasMoreTokens()) {
            System.out.println(tok.nextToken());
        }
    }

    private void totalVotos() {
        mensagem = ConstrutorMensagens.numeroTotalVotos(ip.getHostAddress());
        resposta = enviarMensagemTcp(mensagem);
        System.out.println("Total de votos");
        System.out.println(resposta.getTexto());
    }

    private void tempoRestante() {
        mensagem = ConstrutorMensagens.tempoRestanteVotacao(ip.getHostAddress());
        resposta = enviarMensagemTcp(mensagem);
        System.out.println(resposta.getTexto());
    }

    private void mostraListaVotacao() throws IOException {
        mensagem = ConstrutorMensagens.obterItensVotacao(ip.getHostAddress());
        resposta = enviarMensagemTcp(mensagem);
        System.out.println("Lista de Votação:");
        listar(resposta.getTexto());
    }

    private void listar(final String texto) {
        final StringTokenizer tok = new StringTokenizer(texto, ";");

        if (tok.countTokens() == 1) {
            System.out.println(texto);
        } else {
            for (int i = 0; tok.hasMoreTokens(); i++) {
                System.out.println(i + " - " + tok.nextToken());
            }
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
            while (true) {
                boolean sair = false;

                opcao = getOpcao(teclado);

                switch (opcao) {
                case 0:
                    desenhaMenuClienteVotante();
                    break;
                case 1:
                    mostraListaVotacao();
                    break;
                case 2:
                    tempoRestante();
                    break;
                case 3:
                    // Votar
                    mostraListaVotacao();
                    Integer indexVoto = null;
                    System.out.println("Insira o numero do item a votar: ");

                    while (indexVoto == null) {
                        try {
                            teclado.nextLine();
                            indexVoto = teclado.nextInt();
                        } catch (InputMismatchException e) {
                            indexVoto = null;
                            System.out.println("O número que introduziu é inválido.\n");
                            System.out.println("Insira de novo o numero do item a votar: ");
                        }
                    }

                    System.out.println("Confirma o seu voto no item  " + indexVoto + "? s/n");
                    String escolha = teclado.next();
                    while (!escolha.equalsIgnoreCase("S") && !escolha.equalsIgnoreCase("N")) {
                        System.out.println("Confirme a opção, escrevendo s/n.");
                        System.out.println("Confirma o seu voto no item   " + indexVoto + "? s/n");
                        escolha = teclado.next();
                    }

                    if (escolha.equalsIgnoreCase("N")) {
                        break;
                    }

                    mensagem = ConstrutorMensagens.votar(ip.getHostAddress(), indexVoto.toString());
                    resposta = enviarMensagemTcp(mensagem);

                    System.out.println(resposta.getTexto());

                    break;
                case 4:
                    totalVotos();
                    break;
                case 5:
                    resultadosVotacao();
                    break;
                case 6:
                    itemVencedor();
                    break;
                case 99:
                    sair = deveSair();
                    break;
                default:
                    opcaoInvalida(teclado);
                    desenhaMenuClienteVotante();
                    break;
                }

                if (sair) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
                socket.close();
            } catch (Exception ex) {

            }
        }
    }

    private void opcaoInvalida(Scanner teclado) {
        System.out.println("Opção incorrecta!");
        System.out.println("Prima qualquer tecla para continuar");
        teclado.nextLine();
        teclado.nextLine();
    }

    private boolean deveSair() {
        boolean sair;
        mensagem = ConstrutorMensagens.sair(ip.getHostAddress());
        resposta = enviarMensagemTcp(mensagem);
        System.out.println(resposta.getTexto());
        sair = true;
        return sair;
    }

    private int getOpcao(Scanner teclado) {
        int opcao;
        System.out.println("Opção? ");

        try {
            opcao = teclado.nextInt();
        } catch (InputMismatchException e) {
            opcao = -1;
        }
        return opcao;
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
                    opcaoInvalida(teclado);
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

    public Mensagem enviarMensagemTcp(final String msg) {
        boolean closed = false;
        boolean first = true;

        long start = System.currentTimeMillis();

        while (!closed && (System.currentTimeMillis() - start) < (10 * 1000)) {
            try {
                output.println(msg);
                final String line = input.readLine();

                if (line == null) {
                    throw new IOException();
                }

                closed = true;

                return Descodificador.descodificar(line);
            } catch (IOException e) {
                try {
                    connectToServer();
                } catch (UnknownHostException e1) {
                } catch (IOException e1) {
                }

                if (first) {
                    System.out.println("À espera de uma ligação com o BackEnd...");
                    first = false;
                }
            }
        }

        System.out.println("Não foi possível estabelecer ligação com o BackEnd.");

        System.exit(1);

        return null;
        /*
         * String host = backEndIp; int port = 6500; String line; Socket socket = new Socket(host, port); BufferedReader input = new
         * BufferedReader(new InputStreamReader(socket .getInputStream())); PrintStream output = new PrintStream(socket.getOutputStream(), true);
         * output.println(cmd); // envia alvo ao servidor line = input.readLine();// resposta do servidor input.close();// termina input
         * output.close();// termina output socket.close();// termina socket
         * 
         * return line;
         */

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
                outPacket = new DatagramPacket(msg.getBytes(), msg.length(), host, PORT);
                datagramSocket.send(outPacket);

                buffer = new byte[256];
                inPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(inPacket);
                String response = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("Servidor " + host + " disse: " + response);
                datagramSocket.close();

            }
        } catch (Exception e) {

        }
    }
}
