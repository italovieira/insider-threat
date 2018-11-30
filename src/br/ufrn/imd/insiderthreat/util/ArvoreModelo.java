package br.ufrn.imd.insiderthreat.util;

import br.ufrn.imd.insiderthreat.model.Modelo;

public class ArvoreModelo extends Arvore<Modelo> {
	public ArvoreModelo(Modelo value) {
		super(value);
	}
	
	@Override
	public void adicionar(Arvore<Modelo> value) {
		super.adicionar(value);
	}
}