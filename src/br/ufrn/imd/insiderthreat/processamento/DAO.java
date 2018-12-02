package br.ufrn.imd.insiderthreat.processamento;

import java.util.List;

import br.ufrn.imd.insiderthreat.filtro.Filtro;

/**
 * @author italo
 * 
 * Interface para definir os métodos de acesso aos dados do log
 *
 * @param <T> tipo dos dados que serão obtidos do log
 */
public interface DAO<T> {
	public List<T> processarTodos();
	public List<T> processarComFiltro(Filtro<? super T> filtro);
}
