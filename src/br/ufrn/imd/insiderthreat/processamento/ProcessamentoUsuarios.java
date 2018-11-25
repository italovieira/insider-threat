package br.ufrn.imd.insiderthreat.processamento;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.insiderthreat.model.Usuario;

public class ProcessamentoUsuarios extends Processamento {
	private static final String FILE = "log/LDAP.csv";
	private static final String DELIMITADOR = ",";
	
	public ProcessamentoUsuarios() {
		this(FILE);
	}

	private ProcessamentoUsuarios(String file) {
		super(file);	
	}
	
	@Override
	protected Map<String, String> processarLinha(String linha) {
		// TODO: Checar `atributos` e verificar se há mais ou menos atributos do que deviam
		// talvez devesse ser lançada exceções nesses casos
		
		String[] atributos = linha.split(DELIMITADOR);
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("nome", atributos[0]);
		map.put("id", atributos[1]);
		map.put("dominio", atributos[2]);
		map.put("email", atributos[3]);
		map.put("papel", atributos[4]);
		
		return map;
	}
	
	@Override
	protected Usuario converter(Map<String, String> usuarioMap) {
		Usuario usuario = new Usuario();
		usuario.setId(usuarioMap.get("id"));
		usuario.setNome(usuarioMap.get("nome"));
		usuario.setDominio(usuarioMap.get("dominio"));
		usuario.setEmail(usuarioMap.get("email"));
		usuario.setPapel(usuarioMap.get("papel"));
		
		return usuario;
	}
}
