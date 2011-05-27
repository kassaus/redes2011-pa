package main;

import gestor.Gestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mensagens.Mensagem;
import mensagens.util.Codificador;
import mensagens.util.Descodificador;

public class Main {

    private static HashMap<String, Socket> threads;
    
    public int TCP_SERVER_PORT = 6500;
    public int UDP_UNICAST_PORT = 6501;
    public int UDP_MULTICAST_PORT = 6502;
 //   public int UDP_MULTICAST_____

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        threads = new HashMap<String, Socket>();

        // criar socket na porta 6500
        ServerSocket server = new ServerSocket(TCP_SERVER_PORT);
        System.out.println("servidor iniciado na porta " + TCP_SERVER_PORT);
        while (true) {// aguarda clientes
            Socket socket = null;
            socket = server.accept();
            Thread t = new Thread(new EchoClientThread(socket));
            t.start();
        }// fim aguarda clientes
    }// fim main

    public static List<String> listaVotantesOnline() {
        return Arrays.asList(threads.keySet().toArray(new String[0]));
    }

    public static class EchoClientThread implements Runnable {

        private Socket s;

        public EchoClientThread(Socket socket) {
            this.s = socket;
        }

        public void run() {
            // thread
            String stringClient = s.getInetAddress().getHostAddress();// IP do

            threads.put(stringClient, s);

            // cliente
            System.out.println("conectado com " + stringClient);

            BufferedReader input = null;
            PrintStream output = null;

            try {// inicio try
                input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                output = new PrintStream(s.getOutputStream(), true);

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
                                        resposta = Gestor.adicionarVotanteBranca(msg);
                                        strResposta = Codificador.codificar(resposta);

                                        output.println(strResposta);

                                        break;
                                    case LISTA_NEGRA:
                                        resposta = Gestor.adicionarVotanteNegra(msg);
                                        strResposta = Codificador.codificar(resposta);

                                        output.println(strResposta);

                                        break;

                                    case LISTA_VOTACAO:
                                        resposta = Gestor.adicionarItemListaVotacao(msg);
                                        strResposta = Codificador.codificar(resposta);

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

                                        resposta = Gestor.listarListaBranca(msg);
                                        strResposta = Codificador.codificar(resposta);

                                        output.println(strResposta);

                                        break;
                                    case LISTA_NEGRA:
                                        resposta = Gestor.listarListaNegra(msg);
                                        strResposta = Codificador.codificar(resposta);
                                        output.println(strResposta);

                                        break;

                                    case LISTA_VOTACAO:
                                        resposta = Gestor.listarItensVotacao(msg);
                                        strResposta = Codificador.codificar(resposta);
                                        output.println(strResposta);

                                        break;

                                    case LISTA_ONLINE:
                                        resposta = Gestor.listarListaVotantesOnline(msg);
                                        strResposta = Codificador.codificar(resposta);
                                        output.println(strResposta);

                                        break;

                                    case LISTA_RESULTADOS:
                                        resposta = Gestor.listarListaVotos(msg);
                                        strResposta = Codificador.codificar(resposta);
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
                                        resposta = Gestor.removerItemListaVotacao(msg);
                                        strResposta = Codificador.codificar(resposta);
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
                            case DEFINIR:
                                resposta = Gestor.duracaoVotacao(msg);
                                strResposta = Codificador.codificar(resposta);
                                output.println(strResposta);
                                break;

                            default:
                                break;
                            }
                        }

                        break;

                    case ITEM:
                        if (msg.getAccao() != null) {
                            switch (msg.getAccao()) {
                            case VENCEDOR:
                                resposta = Gestor.itemVencedor(msg);
                                strResposta = Codificador.codificar(resposta);
                                output.println(strResposta);

                                break;

                            default:
                                break;
                            }
                        }

                    case VOTO:
                        resposta = Gestor.votar(msg);
                        strResposta = Codificador.codificar(resposta);
                        output.println(strResposta);

                        break;

                    case SISTEMA:

                        if (msg.getAccao() != null) {
                            switch (msg.getAccao()) {
                            case DESCONECTAR:
                                resposta = Gestor.desconectar(msg);
                                strResposta = Codificador.codificar(resposta);
                                output.println(strResposta);

                                final int indice = Integer.parseInt(msg.getTexto());
                                final String ip = listaVotantesOnline().get(indice);
                                final Socket s = threads.get(ip);
                                threads.remove(ip);

                                System.out.println("O cliente " + ip + " foi desligado.");

                                s.close();

                                break;

                            case SAIR:
                                resposta = Gestor.sair(msg);
                                strResposta = Codificador.codificar(resposta);
                                output.println(strResposta);
                                System.out.println("O cliente " + stringClient + " terminou a ligação.");
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
            }// fim try
            catch (Exception e) {
                // e.printStackTrace();
            } finally {
                try {
                    input.close();// termina input
                    output.close();// termina output
                    s.close();// termina socket
                } catch (Exception e) {
                }
            }
        }// fim metodo run
    }// fim classe EchoClientThread
    /*
     * Gestor gestor = new Gestor(); gestor.imprimeListaTodas(); gestor.votar("192.000.000.001", 0); gestor.imprimeListaTodas();
     */

    // criar socket na porta 6500
    /*
     * ServerSocket server = new ServerSocket(6500); System.out.println("servidor iniciado na porta 6500"); while (true) {// aguarda clientes Socket
     * socket = null; socket = server.accept(); System.out.println("nova conexao.."); Thread t = new Thread(new Cliente(socket)); t.start(); }// fim
     * aguarda clientes }
     * 
     * public static class Cliente implements Runnable { private Socket s;
     * 
     * public Cliente(Socket socket) { this.s = socket; }
     * 
     * public void run() { String threadName = Thread.currentThread().getName();// nome da // thread String stringClient =
     * s.getInetAddress().toString();// IP do cliente System.out.println("conectado com " + stringClient); try {// inicio try BufferedReader input =
     * new BufferedReader( new InputStreamReader(s.getInputStream())); PrintStream output = new PrintStream(s.getOutputStream(), true);
     * 
     * String line; MetodosServidor serverJobs = new MetodosServidor();
     * 
     * while ((line = input.readLine()) != null) {// ciclo de input System.out.println(stringClient + ": " + threadName + ": " + line);// imprime
     * mensagem if (line.equalsIgnoreCase("quit")) { break; } else if (line.equalsIgnoreCase("horas")) {
     * 
     * output.println(serverJobs.hournow()); // echo do input // para output } else if (line.equalsIgnoreCase("data")) {
     * output.println(serverJobs.datenow()); } else if (line.equalsIgnoreCase("frase")) { output.println(serverJobs.getFrase()); } else if
     * (line.equalsIgnoreCase("lista")) { for (int i = 0; i < serverJobs.getFraseTotal(); i++) { output.println(serverJobs.getFrasePosition(i)); } }
     * 
     * else { output.println("Alvo desconhecido!"); } }// fim do ciclo de input output.println("ate logo!"); input.close();// termina input
     * output.close();// termina output s.close();// termina socket }// fim try catch (Exception e) { System.err.println("Erro: " + e); }
     * System.out.println("cliente " + stringClient + " desconectado!"); }// fim metodo run }// fim classe EchoClientThread
     */
}
