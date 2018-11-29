package br.ufrn.imd.insiderthreat.processamento;

import java.util.List;

import br.ufrn.imd.insiderthreat.filtro.Filtro;

public interface DAO<T> {
	public List<T> processarTodos();
	public List<T> processarComFiltro(Filtro filtro);
}
