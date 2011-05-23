package mensagens;

import enumerados.Accao;
import enumerados.Alvo;
import enumerados.TipoMensagem;

public class Mensagem {
	private String ip = null;
	private TipoMensagem tipoMensagem;
	private Alvo alvo;
	private Accao accao;
	private String texto;

	public Mensagem() {
		tipoMensagem = TipoMensagem.INVALIDO;
		alvo = Alvo.INVALIDO;
		accao = Accao.INVALIDO;
	}

	public Mensagem(final Mensagem mensagem) {
		ip = mensagem.getIp();
		tipoMensagem = mensagem.getTipoMensagem();
		alvo = mensagem.getAlvo();
		accao = mensagem.getAccao();
		texto = mensagem.getTexto();
	}

	public Mensagem(int tipoMensagem, String ip, int accao, int alvo,
			String texto) {
		this.ip = ip;
		this.tipoMensagem = TipoMensagem.getLabel(tipoMensagem);
		this.alvo = Alvo.getLabel(alvo);
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

	public final Alvo getAlvo() {
		return alvo;
	}

	public final void setAlvo(Alvo alvo) {
		this.alvo = alvo;
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
