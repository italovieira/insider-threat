package br.ufrn.imd.insiderthreat.util;

import java.util.List;

import br.ufrn.imd.insiderthreat.model.Modelo;

public class ArvorePerfilUsuario extends Arvore<Modelo> {

	public ArvorePerfilUsuario(Modelo value) {
		super(value);
	}
	
	public ArvorePerfilUsuario(Modelo value, List<Arvore<Modelo>> filhos) {
		super(value, filhos);
	}
}