package br.ufrn.imd.insiderthreat.filtro;

public class FiltroAprovarTodos implements Filtro {

	@Override
	public boolean validar(Object valor) {
		return true;
	}
}
