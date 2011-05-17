package mensagens.util;

import java.util.StringTokenizer;

import mensagens.Mensagem;

public abstract class Descodificador {

	public static Mensagem descodificar(final String msg) {
		StringTokenizer str = new StringTokenizer(msg, "|");
		return new Mensagem(Integer.parseInt(str.nextToken()), str.nextToken(),
				Integer.parseInt(str.nextToken()), Integer.parseInt(str
						.nextToken()), str.nextToken());
	}
}
