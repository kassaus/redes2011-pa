package mensagens;

import entidades.Cliente;
import enumerados.Accao;
import enumerados.Comando;
import enumerados.TipoMensagem;

public class Mensagem {
	private Cliente cliente = null;
	private TipoMensagem tipoMensagem;
	private Comando comando;
	private Accao accao;

	private String texto;

	public Mensagem() {

	}

	public Mensagem(int tipoMensagem, String ip, int comando, int accao,
			String texto) {
		setCliente(ip);
		this.tipoMensagem = TipoMensagem.getLabel(tipoMensagem);
		this.comando = Comando.getLabel(comando);
		this.accao = Accao.getLabel(accao);
		this.texto = texto;
	}

	public Mensagem(Cliente cliente, TipoMensagem tipoMensagem,
			Comando comando, Accao accao, String texto) {
		this.cliente = cliente;
		this.tipoMensagem = tipoMensagem;
		this.comando = comando;
		this.accao = accao;
		this.texto = texto;
	}

	public void setCliente(String ip) {
		this.cliente = new Cliente(ip);

	}

	public final Cliente getCliente() {
		return cliente;
	}

	public final void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
