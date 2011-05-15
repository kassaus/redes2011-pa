package entidades;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Lista {
	private ArrayList<Object> lista = null;

	public Lista() {

		lista = new ArrayList<Object>();
	}

	public void setLista(ArrayList<Object> lista) {
		this.lista = lista;
	}

	public void addLista(ArrayList<Object> lista) {
		int repeticoes = 0;

		Iterator<Object> iterator = lista.iterator();
		while (iterator.hasNext()) {
			if (this.lista.contains(iterator.next())) {
				repeticoes++;
			} else {
				this.lista.add(iterator);
			}
		}

	}

	public Boolean procuraLista(Object objecto) {
		return this.lista.contains(lista);
	}

	public Boolean removeLista(Object objecto) {
		return this.lista.remove(objecto);
	}

}
