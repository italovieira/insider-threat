package br.ufrn.imd.insiderthreat.util.files;

import br.ufrn.imd.insiderthreat.model.Usuario;

public class ProcessamentoUsuarios extends Processamento {

	@Override
	protected Usuario processarLinha(String linha) {
		// Checar `atributos` e verificar se há mais ou menos atributos do que deviam
		// talvez devesse ser lançada exceções nesses casos
		String[] atributos = linha.split("\\|");
		
		Usuario usuario = new Usuario();
		usuario.setId(atributos[0]);
		usuario.setNome(atributos[1]);
		usuario.setCpf(atributos[2]);
		
		return usuario;
	}	
}
