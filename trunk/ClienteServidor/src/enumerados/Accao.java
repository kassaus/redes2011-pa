package enumerados;

public enum Accao {
	LISTAR_VOTACAO, LISTAR_ONLINE, LISTAR_RESULTADOS, LISTAR_BRANCA, LISTAR_NEGRA, REMOVER_ITEM, ADICIONAR_ITEM, ADICIONAR_BRANCA, ADICIONAR_NEGRA, TMP_RESTANTE, TOTAL, VENCEDOR;

	public static Accao getLabel(int index) {
		return values()[index];
	}
}
