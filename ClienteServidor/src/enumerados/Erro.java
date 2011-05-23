package enumerados;

public enum Erro {
    REMOVER(400, "Erro ao remover o item da lista."), ACESSO(401, "Não é possivel aceder ao item, votação em curso."), LISTAR(
            402, "a lista encontra-se vazia."), ENVIAR_MENSAGEM(403, "Utilizador desconectado."), TEMPO_VOTACAO(404,
            "Não existe votação agendada."), TOTAIS_VOTACAO(405, "Não é possivel apresentar totais, votação em curso."), VENCEDOR(
            406, "Não é possivel apresentar vencedor, votação em curso."), ADICIONAR(407,
            "Não foi possivel adicionar o novo item."), VOTAR(408, "Apenas é permitido votar uma vez. Obrigado");

    private final int codigo;
    private final String texto;

    Erro(final int codigo, final String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public String getMensagem() {
        return texto;
    }

    public int getCodigo() {
        return codigo;
    }
}
