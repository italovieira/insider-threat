package br.ufrn.imd.insiderthreat.filtro;

public interface Filtro<T> {
	boolean validar(T valor);
}
