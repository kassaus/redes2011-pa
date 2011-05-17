package enumerados;

public enum TipoLista {
	LISTA_BRANCA, LISTA_NEGRA, LISTA_ONLINE, LISTA_ITENS, LISTA_RESULTADOS;

	public static TipoLista getLabel(int index) {
		return values()[index];
	}

}
