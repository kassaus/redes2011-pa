package mensagens;

import enumerados.TipoLista;

public class MensagemObterLista extends MensagensRecebida {
	TipoLista tipoDeLista = null;

	public MensagemObterLista(String mensagem) {
		descodicarMensagem(mensagem);
	}

	public void descodicarMensagem(String mensagem) {
		int indiceLista = 0;
		String[] tmp = mensagem.split("\\|");
		setCliente(tmp[0]);
		setComando(tmp[1]);
		indiceLista = Integer.parseInt(tmp[2].toString());
		TipoLista[] tiposLista = TipoLista.values();

		setTipoLista(tiposLista[indiceLista]);

	}

	public void setTipoLista(TipoLista tipo) {
		tipoDeLista = tipo;
	}

}
