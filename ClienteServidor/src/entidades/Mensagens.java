package entidades;

public abstract class Mensagens {
	private Cliente cliente = null;

	protected Mensagens() {

	}

	public void setCliente(String ip) {
		this.cliente = new Cliente(ip);

	}

}
