package util;

import java.io.IOException;
import java.net.InetAddress;

import mensagens.Mensagem;
import mensagens.util.Codificador;
import enumerados.Accao;
import enumerados.Alvo;
import enumerados.TipoMensagem;

public class ConstrutorMensagens {

	private static final String STRING_VAZIA = " ";
	private static final int VAZIO = -1;

	public ConstrutorMensagens() throws IOException {

	}

	public static String getListaItens(InetAddress ip) {
		return null;
	}

	public static String obterListaBranca(final String ip) {
		return Codificador.codificar(new Mensagem(TipoMensagem.LISTA.ordinal(),
				ip, Accao.LISTAR.ordinal(), Alvo.LISTA_BRANCA.ordinal(),
				STRING_VAZIA));
	}
}
