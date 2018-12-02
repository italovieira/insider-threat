package br.ufrn.imd.insiderthreat.filtro;

/**
 * @author italo
 *
 * Interface que implementa o padrão Specification
 * Indicará se o dado será validado ou não
 * Será usada como parâmetro para as buscas/consultas/filtragem
 *
 * @param <T> Tipo do valor a ser validado para posterior filtragem
 */
public interface Filtro<T> {
	/**
	 * Por meio deste será possível verificar a validação do 
	 * 
	 * @param dado a ser validado
	 * @return Indica se o dado foi validado ou não
	 */
	boolean validar(T dado);
}
