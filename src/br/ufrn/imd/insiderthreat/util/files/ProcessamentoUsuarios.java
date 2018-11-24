package br.ufrn.imd.insiderthreat.util.files;

import br.ufrn.imd.insiderthreat.model.Usuario;

public class ProcessamentoUsuarios extends Processamento {
	private static final String FILE = "LDAP.csv";
	
	public ProcessamentoUsuarios() {
		this(FILE);
	}

	private ProcessamentoUsuarios(String file) {
		super(file);	
	}

	@Override
	protected Usuario processarLinha(String linha) {
		// TODO: Checar `atributos` e verificar se há mais ou menos atributos do que deviam
		// talvez devesse ser lançada exceções nesses casos
		String[] atributos = linha.split("\\|");
		
		Usuario usuario = new Usuario();
		usuario.setId(atributos[0]);
		usuario.setNome(atributos[1]);
		usuario.setDominio(atributos[2]);
		usuario.setEmail(atributos[3]);
		usuario.setPapel(atributos[4]);
		
		return usuario;
	}	
}
