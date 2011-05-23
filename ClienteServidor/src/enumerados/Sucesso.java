package enumerados;

public enum Sucesso {
    OK("Ok"), SAUDACAO("Hello"), REMOVER("Item removido."), LISTAR("Lista apresentada"), ADICIONAR(
            "Item adicionado."), MENSAGEM("Mensagem enviada"), DESCONECTAR("Ligação terminada."), TEMPO_VOTACAO(
            "Tempo de votação"), TOTAL_VOTOS("Total de votos"), VENCEDOR("Vencedor"), DEFINIR(
            "O valor foi actualizado.");

    private final String texto;

    Sucesso(final String texto) {
        this.texto = texto;
    }

    public String getMensagem() {
        return texto;
    }
}