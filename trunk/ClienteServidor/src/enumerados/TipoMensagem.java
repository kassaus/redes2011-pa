package enumerados;

public enum TipoMensagem {
	OBTER_LISTA, OBTER_NUMERO, VOTAR, MODIFICAR_LISTA, DESCONECTAR;

	public static TipoMensagem getLabel(int index) {
		return values()[index];
	}

}
