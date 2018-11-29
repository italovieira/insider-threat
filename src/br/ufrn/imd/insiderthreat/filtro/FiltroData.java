package br.ufrn.imd.insiderthreat.filtro;

import br.ufrn.imd.insiderthreat.model.Atributos;

public class FiltroData implements Filtro {
	private String inicio;
	private String fim;

	public FiltroData(String inicio, String fim) {
		this.inicio = inicio;
		this.fim = fim;
	}
	
	public boolean validar(Object valor) {
		try {
			Atributos atributo = (Atributos) valor;
			String data = atributo.getData();
			return data.compareTo(inicio) != -1 && data.compareTo(fim) != 1;
		} catch (Exception e) {
			return false;
		}

	}
}
