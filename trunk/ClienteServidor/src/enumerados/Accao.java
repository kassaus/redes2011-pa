package enumerados;

public enum Accao {
	INVALIDO(-1), LISTAR_VOTACAO, LISTAR_ONLINE, LISTAR_RESULTADOS, LISTAR_BRANCA, LISTAR_NEGRA, REMOVER_ITEM, ADICIONAR_ITEM, ADICIONAR_BRANCA, ADICIONAR_NEGRA, TMP_RESTANTE, TOTAL, VENCEDOR;

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
