package br.ufrn.imd.insiderthreat.filtro;

/**
 * @author italo
 *
 * Não importa qual o valor, sempre será validado para filtro. Ou seja, filtrará todos os registros
 */
public class FiltroAprovarTodos implements Filtro<Object> {

	@Override
	public boolean validar(Object valor) {
		return true;
	}
}
