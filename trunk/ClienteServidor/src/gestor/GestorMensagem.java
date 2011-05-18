package gestor;

import java.io.IOException;
import java.net.InetAddress;

import mensagens.Mensagem;
import mensagens.util.Codificador;
import enumerados.TipoLista;
import enumerados.TipoMensagem;

public class GestorMensagem {

	private static final int VAZIO = -1;

	public GestorMensagem() throws IOException {

	}

	public static String getListaItens(InetAddress ip) {
		return Codificador.codificar(new Mensagem(TipoMensagem.OBTER_LISTA
				.ordinal(), ip.getHostAddress(), VAZIO, VAZIO, String
				.valueOf(TipoLista.LISTA_ITENS.ordinal())));
	}
}
