package enumerados;

public enum Accao {
	INVALIDO(-1), LISTAR, REMOVER, ADICIONAR, TMP_RESTANTE, TOTAL, VENCEDOR, DESCONECTAR, SAIR;

	int index;

	private Accao() {
		this.index = ordinal();
	}

	private Accao(int index) {
		this.index = index;
	}

	public static Accao getLabel(int index) {
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
