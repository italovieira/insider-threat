package br.ufrn.imd.insiderthreat.processamento;

import java.util.ArrayList;
import java.util.List;
import br.ufrn.imd.insiderthreat.filtro.Filtro;
import br.ufrn.imd.insiderthreat.filtro.FiltroAprovarTodos;
import br.ufrn.imd.insiderthreat.model.Atributos;
import br.ufrn.imd.insiderthreat.model.Http;
import br.ufrn.imd.insiderthreat.model.Logon;

public class ProcessamentoAtributos implements DAO<Atributos> {
	@Override
	public List<Atributos> processarTodos() {
		return processarComFiltro(new FiltroAprovarTodos());
	}

	@Override
	public List<Atributos> processarComFiltro(Filtro<? super Atributos> filtro) {
		List<Http> https = new ProcessamentoHTTP().processarComFiltro(filtro);
		List<Logon> logons = new ProcessamentoLogon().processarComFiltro(filtro);
		
		List<Atributos> atributos = new ArrayList<>();
		atributos.addAll(https);
		atributos.addAll(logons);
		
		return atributos;
	}
}