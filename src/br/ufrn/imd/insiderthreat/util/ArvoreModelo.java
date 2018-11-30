package br.ufrn.imd.insiderthreat.util;

import java.util.Map;

import br.ufrn.imd.insiderthreat.model.Modelo;

public class ArvoreModelo extends Arvore<Modelo> {
	private Map<String, ArvoreModelo> map;

	public ArvoreModelo(Modelo value) {
		super(value);
	}
	
	@Override
	public void adicionar(Arvore<Modelo> value) {
		super.adicionar(value);
	}
}