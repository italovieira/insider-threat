package br.ufrn.imd.insiderthreat.util;

import java.util.List;

public class ArvorePerfilUsuario extends Arvore<ValorNo> {

	public ArvorePerfilUsuario(ValorNo value) {
		super(value);
	}
	
	public ArvorePerfilUsuario(ValorNo value, List<Arvore<ValorNo>> filhos) {
		super(value, filhos);
	}
}