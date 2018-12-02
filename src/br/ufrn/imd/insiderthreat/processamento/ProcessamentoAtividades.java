package br.ufrn.imd.insiderthreat.processamento;

import java.util.ArrayList;
import java.util.List;
import br.ufrn.imd.insiderthreat.filtro.Filtro;
import br.ufrn.imd.insiderthreat.filtro.FiltroAprovarTodos;
import br.ufrn.imd.insiderthreat.model.Atividade;
import br.ufrn.imd.insiderthreat.model.Dispositivo;
import br.ufrn.imd.insiderthreat.model.Http;
import br.ufrn.imd.insiderthreat.model.Logon;

/**
 * @author italo
 *
 * Implementação concreta do processamento para a entidade de Atividade
 */
public class ProcessamentoAtividades implements DAO<Atividade> {
	/* (non-Javadoc)
	 * @see br.ufrn.imd.insiderthreat.processamento.DAO#processarTodos()
	 */
	@Override
	public List<Atividade> processarTodos() {
		return processarComFiltro(new FiltroAprovarTodos());
	}

	/* (non-Javadoc)
	 * @see br.ufrn.imd.insiderthreat.processamento.DAO#processarComFiltro(br.ufrn.imd.insiderthreat.filtro.Filtro)
	 */
	@Override
	public List<Atividade> processarComFiltro(Filtro<? super Atividade> filtro) {
		List<Http> https = new ProcessamentoHTTP().processarComFiltro(filtro);
		List<Logon> logons = new ProcessamentoLogon().processarComFiltro(filtro);
		List<Dispositivo> dispositivos = new ProcessamentoDispositivos().processarComFiltro(filtro);
		
		List<Atividade> atividades = new ArrayList<>();
		atividades.addAll(https);
		atividades.addAll(logons);
		atividades.addAll(dispositivos);
		
		return atividades;
	}
}
