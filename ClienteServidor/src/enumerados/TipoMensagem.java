package enumerados;

public enum TipoMensagem {
	INVALIDO(-1), OBTER_LISTA, OBTER_NUMERO, VOTAR, MODIFICAR_LISTA, DESCONECTAR;

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
