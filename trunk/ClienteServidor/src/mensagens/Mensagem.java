package mensagens;

import enumerados.Accao;
import enumerados.Comando;
import enumerados.TipoMensagem;

public class Mensagem {
	private String ip = null;
	private TipoMensagem tipoMensagem;
	private Comando comando;
	private Accao accao;

	private String texto;

	public Mensagem() {

	}

	public Mensagem(int tipoMensagem, String ip, int comando, int accao,
			String texto) {
		this.ip = ip;
		this.tipoMensagem = TipoMensagem.getLabel(tipoMensagem);
		this.comando = Comando.getLabel(comando);
		this.accao = Accao.getLabel(accao);
		this.texto = texto;
	}

	public final String getIp() {
		return ip;
	}

	public final void setIp(String ip) {
		this.ip = ip;
	}

	public final TipoMensagem getTipoMensagem() {
		return tipoMensagem;
	}

	public final void setTipoMensagem(TipoMensagem tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}

	public final Comando getComando() {
		return comando;
	}

	public final void setComando(Comando comando) {
		this.comando = comando;
	}

	public final Accao getAccao() {
		return accao;
	}

	public final void setAccao(Accao accao) {
		this.accao = accao;
	}

	public final String getTexto() {
		return texto;
	}

	public final void setTexto(String texto) {
		this.texto = texto;
	}

}
