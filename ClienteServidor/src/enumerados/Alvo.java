package enumerados;

public enum Alvo {
    INVALIDO(-1), LISTA_BRANCA, LISTA_NEGRA, LISTA_VOTACAO, LISTA_ONLINE, LISTA_RESULTADOS, DURACAO;

    int index;

    private Alvo() {
        this.index = ordinal();
    }

    private Alvo(int index) {
        this.index = index;
    }

    public static Alvo getLabel(int index) {
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
