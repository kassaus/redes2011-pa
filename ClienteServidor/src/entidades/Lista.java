package entidades;

import java.util.ArrayList;

public abstract class Lista {
	private ArrayList<Object> lista = null;

	public Lista() {

		lista = new ArrayList<Object>();
	}

	public Boolean removeItem(final Object objecto) {
		return this.lista.remove(objecto);
	}

	public Boolean adicionarItem(final Object objecto) {
		return lista.add(objecto);
	}

	public Object procuraItem(final Object objecto) {
		final int indice = lista.indexOf(objecto);
		if (indice != -1) {
			return lista.get(indice);
		}
		return null;
	}

}
