package enumerados;

public enum Comando {
	INVALIDO(-1), LISTAR, REMOVER, ADICIONAR, DESCONECTAR, OBTER;

	int index;

	private Comando() {
		this.index = ordinal();
	}

	private Comando(int index) {
		this.index = index;
	}

	public static Comando getLabel(int index) {
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
