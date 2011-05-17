package mensagens.util;

import mensagens.Mensagem;

public abstract class Codificador {

	public static String codificar(final Mensagem msg) {
		return msg.getTipoMensagem().toString() + "|"
				+ msg.getCliente().toString() + "|" + msg.getAccao().toString()
				+ "|" + msg.getComando().toString() + "|"
				+ msg.getTexto().toString();

	}

}
