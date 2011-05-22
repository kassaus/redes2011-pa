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

	public static String obterListaNegra(final String ip) {

		return Codificador.codificar(new Mensagem(TipoMensagem.LISTA.ordinal(),
				ip, Accao.LISTAR.ordinal(), Alvo.LISTA_NEGRA.ordinal(),
				STRING_VAZIA));
	}

	public static String obterListaVotantes(final String ip) {

		return Codificador.codificar(new Mensagem(TipoMensagem.LISTA.ordinal(),
				ip, Accao.LISTAR.ordinal(), Alvo.LISTA_ONLINE.ordinal(),
				STRING_VAZIA));
	}

	public static String obterListaVotos(final String ip) {
		return Codificador.codificar(new Mensagem(TipoMensagem.LISTA.ordinal(),
				ip, Accao.LISTAR.ordinal(), Alvo.LISTA_RESULTADOS.ordinal(),
				STRING_VAZIA));
	}

	public static String obterItemVencedor(final String ip) {
		return Codificador.codificar(new Mensagem(TipoMensagem.INFORMACAO
				.ordinal(), ip, Accao.VENCEDOR.ordinal(), VAZIO, STRING_VAZIA));
	}

	public static String obterItensVotacao(final String ip) {
		return Codificador.codificar(new Mensagem(TipoMensagem.LISTA.ordinal(),
				ip, Accao.LISTAR.ordinal(), Alvo.LISTA_VOTACAO.ordinal(),
				STRING_VAZIA));

	}

	public static String adicionarItem(final String sigla,
			final String descricao, final String ip) {

		return Codificador.codificar(new Mensagem(TipoMensagem.LISTA.ordinal(),
				ip, Accao.ADICIONAR.ordinal(), Alvo.LISTA_VOTACAO.ordinal(),
				sigla + "§" + descricao));
	}

	public static String removerItemLista(final String index, final String ip) {
		return Codificador.codificar(new Mensagem(TipoMensagem.LISTA.ordinal(),
				ip, Accao.REMOVER.ordinal(), Alvo.LISTA_VOTACAO.ordinal(),
				index));

	}

	public static String adicionarVotanteBranca(final String ip,
			final String ipVotante) {

		return Codificador.codificar(new Mensagem(TipoMensagem.LISTA.ordinal(),
				ip, Accao.ADICIONAR.ordinal(), Alvo.LISTA_BRANCA.ordinal(),
				ipVotante));
	}

	public static String adicionarVotanteNegra(final String ip,
			final String ipVotanteNegra) {
		return Codificador.codificar(new Mensagem(TipoMensagem.LISTA.ordinal(),
				ip, Accao.ADICIONAR.ordinal(), Alvo.LISTA_NEGRA.ordinal(),
				ipVotanteNegra));
	}

	public static String tempoRestanteVotacao(final String ip) {
		return Codificador.codificar(new Mensagem(
				TipoMensagem.NUMERO.ordinal(), ip,
				Accao.TMP_RESTANTE.ordinal(), VAZIO, STRING_VAZIA));
	}

	public static String numeroTotalVotos(final String ip) {
		return Codificador.codificar(new Mensagem(
				TipoMensagem.NUMERO.ordinal(), ip, Accao.TOTAL.ordinal(),
				VAZIO, STRING_VAZIA));
	}

}
