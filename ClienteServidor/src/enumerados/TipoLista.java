package enumerados;

public enum TipoLista {
	INVALIDO(-1), LISTA_BRANCA, LISTA_NEGRA, LISTA_ONLINE, LISTA_ITENS, LISTA_RESULTADOS;

	int index;

	private TipoLista() {
		this.index = ordinal();
	}

	private TipoLista(int index) {
		this.index = index;
	}

	public static TipoLista getLabel(int index) {
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
