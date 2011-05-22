package enumerados;

public enum TipoMensagem {
	INVALIDO(-1), LISTA, VOTO, SISTEMA, NUMERO, INFORMACAO;

	int index;

	private TipoMensagem() {
		this.index = ordinal();
	}

	private TipoMensagem(int index) {
		this.index = index;
	}

	public static TipoMensagem getLabel(int index) {
		if (index == -1) {
			return INVALIDO;
		} else {
			return values()[index];
		}
	}

	public int getIndice() {
		return index;
	}
}
