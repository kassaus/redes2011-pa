package gestor;

import enumerados.Accao;
import enumerados.Alvo;
import enumerados.Erro;
import enumerados.Sucesso;
import enumerados.TipoMensagem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import main.Main;
import mensagens.Mensagem;

public class Gestor {

    private static final String RESOURCES_PATH = "resources/";
    private static final String ADMIN_FILE = RESOURCES_PATH + "admin.dat";
    private static final String LISTA_BRANCA_FILE = RESOURCES_PATH + "listaBranca.dat";
    private static final String LISTA_NEGRA_FILE = RESOURCES_PATH + "listaNegra.dat";
    private static final String LISTA_ITEMS_VOTACAO = RESOURCES_PATH + "listaItemsVotacao.dat";
    private static final String LISTA_VOTANTES = RESOURCES_PATH + "listaVotantes.dat";
    private static final String LISTA_VOTOS = RESOURCES_PATH + "votos.dat";
    private static final String VOTACAO = RESOURCES_PATH + "votacao.dat";;

    private List<String> listaAdmin = null;
    private List<String> listaBranca = null;
    private List<String> listaNegra = null;
    private List<String> listaVotantes = null;
    private List<String> listaVotos = null;
    private HashMap<String, String> itensVotacao = null;
    private List<String> siglas = null;

    private Calendar dataInicio = null;
    private Calendar dataFim = null;
    private Integer duracaoVotacao = null;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static Gestor instance = null;

    private Gestor() {
        listaAdmin = new ArrayList<String>();
        listaAdmin.addAll((readFileToList(Gestor.class.getClassLoader().getResource(ADMIN_FILE).getPath())));
        listaBranca = new ArrayList<String>();
        listaBranca.addAll(readFileToList(Gestor.class.getClassLoader().getResource(LISTA_BRANCA_FILE).getPath()));
        listaNegra = new ArrayList<String>();
        listaNegra.addAll(readFileToList(Gestor.class.getClassLoader().getResource(LISTA_NEGRA_FILE).getPath()));

        listaVotantes = new ArrayList<String>();
        listaVotantes.addAll(readFileToList(Gestor.class.getClassLoader().getResource(LISTA_VOTANTES).getPath()));
        listaVotos = new ArrayList<String>();
        listaVotos.addAll(readFileToList(Gestor.class.getClassLoader().getResource(LISTA_VOTOS).getPath()));
        itensVotacao = new HashMap<String, String>();
        itensVotacao.putAll(readFileToMap(Gestor.class.getClassLoader().getResource(LISTA_ITEMS_VOTACAO).getPath()));
        actualizaListaSiglas();
        readDateFile(Gestor.class.getClassLoader().getResource(VOTACAO).getPath());

        // final String sigla = siglas.get(2);
        // final String descricao = itensVotacao.get(sigla);
    }

    private ArrayList<String> readFileToList(String ficheiro) {
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

    private void readDateFile(String ficheiro) {
        String strDadosFile;

        try {
            FileReader leitor = new FileReader(ficheiro);
            BufferedReader in = new BufferedReader(leitor);

            try {
                strDadosFile = in.readLine();
                if (strDadosFile != null && !strDadosFile.isEmpty()) {
                    dataInicio = Calendar.getInstance();
                    dataInicio.setTime(dateFormat.parse(strDadosFile));
                }
            } catch (ParseException e) {
                dataInicio = null;
            }

            try {
                strDadosFile = in.readLine();
                if (strDadosFile != null && !strDadosFile.isEmpty()) {
                    duracaoVotacao = Integer.parseInt(strDadosFile);
                }
            } catch (NumberFormatException e) {
            }

            try {
                strDadosFile = in.readLine();
                if (strDadosFile != null && !strDadosFile.isEmpty()) {
                    dataFim = Calendar.getInstance();
                    dataFim.setTime(dateFormat.parse(strDadosFile));
                }
            } catch (ParseException e) {
                dataFim = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> readFileToMap(String ficheiro) {
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

    public Mensagem listarListaBranca(final Mensagem msg) {
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

    public Mensagem listarListaNegra(final Mensagem msg) {
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

    public Mensagem listarListaVotantesOnline(final Mensagem msg) {
        final Mensagem mensagem = new Mensagem(msg);
        StringBuilder texto = new StringBuilder();
        for (String ip : Main.listaVotantesOnline()) {
            texto.append(";");
            texto.append(ip);
        }
        texto.deleteCharAt(0);
        mensagem.setTexto(texto.toString());
        return mensagem;

    }

    public Mensagem listarListaVotos(final Mensagem msg) {
        final Mensagem mensagem = new Mensagem(msg);
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < resultadosVotacao().size(); i++) {
            texto.append(";");
            texto.append(siglas.get(i) + " - ");
            texto.append(resultadosVotacao().get(i));
        }
        texto.deleteCharAt(0);
        mensagem.setTexto(texto.toString());
        return mensagem;

    }

    public Mensagem listarItensVotacao(final Mensagem msg) {
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

    public Mensagem adicionarItemListaVotacao(final Mensagem msg) {
        final Mensagem mensagem = new Mensagem(msg);
        mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
        final StringTokenizer tok = new StringTokenizer(msg.getTexto(), "§");
        try {
            if (dataFim == null) {
                final String sigla = tok.nextToken();
                final String descricao = tok.nextToken();
                if (!sigla.contains(sigla)) {
                    itensVotacao.put(sigla, descricao);
                    actualizaListaSiglas();
                    listaVotos.add("0");
                    Collections.fill(listaVotos, "0");

                    mensagem.setTexto(Sucesso.OK.getMensagem() + ": " + Sucesso.ADICIONAR.getMensagem());
                } else {
                    mensagem.setTexto("Erro: " + Erro.ITEM_EXISTENTE.getCodigo() + ": "
                            + Erro.ITEM_EXISTENTE.getMensagem());
                }
            } else {
                mensagem
                        .setTexto("Erro: " + Erro.VOTACAO_ACTIVA.getCodigo() + ": " + Erro.VOTACAO_ACTIVA.getMensagem());
            }
        } catch (IndexOutOfBoundsException ex) {
            mensagem.setTexto(Erro.ADICIONAR.getCodigo() + ": " + Erro.ADICIONAR.getMensagem());
        }

        return mensagem;
    }

    public Mensagem removerItemListaVotacao(final Mensagem msg) {
        final Mensagem mensagem = new Mensagem();
        mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
        mensagem.setIp(msg.getIp());

        String sigla = null;
        int indice = Integer.parseInt(msg.getTexto());

        sigla = siglas.get(indice);

        try {
            if (dataFim == null) {
                itensVotacao.remove(sigla);
                actualizaListaSiglas();
                listaVotos.remove(indice);
                Collections.fill(listaVotos, "0");

                mensagem.setTexto(Sucesso.OK.getMensagem() + ": " + Sucesso.REMOVER.getMensagem());
            } else {
                mensagem
                        .setTexto("Erro: " + Erro.VOTACAO_ACTIVA.getCodigo() + ": " + Erro.VOTACAO_ACTIVA.getMensagem());
            }
        } catch (IndexOutOfBoundsException ex) {
            mensagem.setTexto(Erro.REMOVER.getCodigo() + ": " + Erro.REMOVER.getMensagem());
        }

        return mensagem;
    }

    private void actualizaListaSiglas() {
        // Actualiza a lista de siglas com as Keys da HasMap.
        siglas = Arrays.asList(itensVotacao.keySet().toArray(new String[0]));
    }

    public Mensagem itemVencedor(final Mensagem msg) {
        final Mensagem mensagem = new Mensagem(msg);

        try {
            String sigla = siglas.get(listaVotos.indexOf(itemVencedor()));

            mensagem.setTexto(sigla + " - " + itensVotacao.get(sigla));
        } catch (Exception ex) {

            mensagem.setTexto(Erro.VENCEDOR.getCodigo() + ": " + Erro.VENCEDOR.getMensagem());

        }
        return mensagem;

    }

    public Mensagem adicionarVotanteBranca(Mensagem msg) {
        final Mensagem mensagem = new Mensagem();
        mensagem.setIp(msg.getIp());
        try {
            if (validaIp(msg.getTexto()) && listaNegra.contains(msg.getTexto())) {
                listaNegra.remove(msg.getIp());
            }

            if (validaIp(msg.getTexto()) && !listaBranca.contains(msg.getTexto())) {
                listaBranca.add(msg.getTexto());
                mensagem.setTexto(Sucesso.OK.getMensagem() + ":" + msg.getTexto() + " "
                        + Sucesso.ADICIONAR.getMensagem());
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            mensagem.setTexto("Erro: " + Erro.IP_INVALIDO.getCodigo() + " '" + msg.getTexto() + "' - "
                    + Erro.IP_INVALIDO.getMensagem());
        }

        return mensagem;
    }

    public Mensagem adicionarVotanteNegra(Mensagem msg) {
        final Mensagem mensagem = new Mensagem();
        mensagem.setIp(msg.getIp());
        try {
            if (validaIp(msg.getTexto())) {
                if (!listaNegra.contains(msg.getTexto())) {
                    listaNegra.add(msg.getTexto());
                }

                mensagem.setTexto(Sucesso.OK.getMensagem() + " '" + msg.getTexto() + "' - "
                        + Sucesso.ADICIONAR.getMensagem());
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            mensagem.setTexto("Erro: " + Erro.IP_INVALIDO.getCodigo() + " '" + msg.getTexto() + "' - "
                    + Erro.IP_INVALIDO.getMensagem());
        }
        return mensagem;
    }

    public Mensagem tempoRestante(Mensagem msg) {
        final Mensagem mensagem = new Mensagem(msg);

        if (dataFim != null) {
            long tempRestante = dataFim.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
            int segundos = (int) (tempRestante / 1000) % 60;
            int horas = (int) ((tempRestante / 1000) / 3600);
            int minutos = (int) ((tempRestante / 1000) / 60) - horas * 60;

            mensagem.setTexto(horas + " h " + minutos + " m " + segundos + " s");

        } else {
            mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
            mensagem.setAccao(Accao.TMP_RESTANTE);
            mensagem.setTexto("Erro: " + Erro.TEMPO_VOTACAO.getCodigo() + " " + Erro.TEMPO_VOTACAO.getMensagem());
        }
        return mensagem;
    }

    public Mensagem totalVotos(Mensagem msg) {
        final Mensagem mensagem = new Mensagem(msg);
        mensagem.setTexto(String.valueOf(listaVotantes.size()));

        return mensagem;
    }

    public Mensagem sair(Mensagem msg) {
        final Mensagem mensagem = new Mensagem(msg);
        mensagem.setTexto(Sucesso.OK.getMensagem() + " - A sair. ");
        return mensagem;
    }

    public Mensagem desconectar(Mensagem msg) {
        final Mensagem mensagem = new Mensagem(msg);
        mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
        mensagem.setAlvo(Alvo.OK);
        mensagem.setTexto(Sucesso.OK.getMensagem() + " - " + Sucesso.DESCONECTAR.getMensagem());
        return mensagem;
    }

    public Mensagem votar(Mensagem msg) {
        final Mensagem mensagem = new Mensagem(msg);
        mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
        if (dataFim == null) {
            mensagem.setTexto("Erro " + Erro.VOTACAO_INDISPONIVEL.getCodigo() + ": "
                    + Erro.VOTACAO_INDISPONIVEL.getMensagem());
            return mensagem;
        }

        if ((listaBranca.isEmpty() || listaBranca.contains(msg.getIp())) && !listaNegra.contains(msg.getIp())) {
            if (!listaVotantes.contains(msg.getIp())) {
                final int index = Integer.parseInt(msg.getTexto());
                mensagem.setTexto(Sucesso.OK.getMensagem() + ": Voto efectuado no " + siglas.get(index));
                listaVotantes.add(msg.getIp());
                listaVotos.set(index, Integer.toString(Integer.parseInt(listaVotos.get(index)) + 1));
            } else {
                mensagem.setTexto("Erro " + Erro.VOTAR.getCodigo() + ": " + Erro.VOTAR.getMensagem());
            }
        } else {
            mensagem.setTexto("Erro " + Erro.VOTANTE_LISTA_NEGRA.getCodigo() + ": "
                    + Erro.VOTANTE_LISTA_NEGRA.getMensagem());
        }

        return mensagem;
    }

    public Mensagem duracaoVotacao(Mensagem msg) {
        final Mensagem mensagem = new Mensagem();

        if (dataFim == null || dataFim.getTimeInMillis() < System.currentTimeMillis()) {
            duracaoVotacao = Integer.parseInt(msg.getTexto());
            mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
            mensagem.setIp(msg.getIp());
            mensagem.setTexto(Sucesso.OK.getMensagem() + ": " + Sucesso.DEFINIR.getMensagem());

        } else {
            mensagem.setTexto("Erro " + Erro.VOTACAO_ACTIVA.getCodigo() + ":" + Erro.VOTACAO_ACTIVA.getMensagem());
        }

        return mensagem;
    }

    public Mensagem iniciarVotacao(Mensagem msg) {
        final Mensagem mensagem = new Mensagem();
        mensagem.setIp(msg.getIp());
        mensagem.setTipoMensagem(TipoMensagem.INFORMACAO);
        if (duracaoVotacao != null) {
            dataInicio = Calendar.getInstance();
            dataFim = (Calendar) dataInicio.clone();
            dataFim.add(Calendar.MINUTE, duracaoVotacao);
            mensagem.setTexto(Sucesso.OK.getMensagem() + ": " + Sucesso.INICIAR_VOTACAO.getMensagem());
        } else {

            mensagem.setTexto("Erro " + Erro.TEMPO_DURACAO.getCodigo() + ":" + Erro.TEMPO_DURACAO.getMensagem());
        }

        return mensagem;
    }

    public List<String> resultadosVotacao() {
        List<String> percentagens = new ArrayList<String>();
        double valorPercentagem = 0;
        DecimalFormat decimalFormato = new DecimalFormat("##.#");

        for (String item : listaVotos) {
            valorPercentagem = (Double.parseDouble(item) / listaVotantes.size()) * 100.0;
            percentagens.add(decimalFormato.format(valorPercentagem) + "%");
        }

        return percentagens;
    }

    public String itemVencedor() {
        return Collections.max(listaVotos);
    }

    public final List<String> getListaAdmin() {
        return listaAdmin;
    }

    public void imprimeLista(List<Object> lista) {
        for (Object item : lista) {
            System.out.println((String) item);
        }
    }

    public boolean validaIp(String ip) {
        String[] partes = ip.split("\\.");

        if (partes.length != 4) {
            return false;
        }

        for (String str : partes) {
            int i = Integer.parseInt(str);
            if (i < 0 || i > 255) {
                return false;
            }
        }
        return true;
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

    public List<String> listarListaVotantes() {
        return listaVotantes;

    }

    public Integer getDuracaoVotacao() {
        return duracaoVotacao;
    }

    public final static Gestor getInstance() {
        if (instance == null) {
            instance = new Gestor();
        }

        return instance;
    }

}
