package br.ufrn.imd.insiderthreat.filtro;

public class FiltroAprovarTodos implements Filtro<Object> {

	@Override
	public boolean validar(Object valor) {
		return true;
	}
}
