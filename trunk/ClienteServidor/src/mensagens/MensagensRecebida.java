package mensagens;

public abstract class MensagensRecebida extends Mensagem {
	private String comando;

	protected MensagensRecebida() {

	}

	public void setComando(String comando) {

		this.comando = comando;
	}

}
