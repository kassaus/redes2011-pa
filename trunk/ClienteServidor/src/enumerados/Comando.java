package enumerados;

public enum Comando {
	LISTAR, REMOVER, ADICIONAR, DESCONECTAR, OBTER;

	public static Comando getLabel(int index) {
		return values()[index];
	}
}
