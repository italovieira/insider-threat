package br.ufrn.imd.insiderthreat.processamento;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.insiderthreat.model.Dispositivo;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.model.Modelo;

public class ProcessamentoDispositivos extends Processamento {
	private static final String FILE = "log/LDAP.csv";
	private static final String DELIMITADOR = ",";
	
	public ProcessamentoDispositivos() {
		this(FILE);
	}

	private ProcessamentoDispositivos(String file) {
		super(file);	
	}

	@Override
	protected Map<String, String> processarLinha(String linha) {
		// TODO: Checar `atributos` e verificar se há mais ou menos atributos do que deviam
		// talvez devesse ser lançada exceções nesses casos
		String[] atributos = linha.split(DELIMITADOR);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", atributos[0]);
		map.put("data", atributos[1]);
		map.put("atividade", atributos[2]);
		
		return map;
	}

	@Override
	protected Modelo converter(Map<String, String> map) {
		Dispositivo dispositivo = new Dispositivo();
		dispositivo.setId(map.get("id"));
		dispositivo.setData(map.get("data"));
		dispositivo.setAtividade(map.get("atividade"));
		
		return dispositivo;
	}	
}
