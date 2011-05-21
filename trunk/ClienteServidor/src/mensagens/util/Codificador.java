package mensagens.util;

import mensagens.Mensagem;

public abstract class Codificador {

	public static String codificar(final Mensagem msg) {
		return msg.getTipoMensagem().getIndice() + "|" + msg.getIp() + "|"
				+ msg.getAccao().getIndice() + "|"
				+ msg.getAlvo().getIndice() + "|" + msg.getTexto();

	}

}
