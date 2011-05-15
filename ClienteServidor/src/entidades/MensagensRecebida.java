package entidades;

public abstract class MensagensRecebida extends Mensagens {
	private String comando;

	protected MensagensRecebida() {

	}

	public void setComando(String comando) {

		this.comando = comando;
	}

}
