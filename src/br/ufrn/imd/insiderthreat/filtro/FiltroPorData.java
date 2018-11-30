package br.ufrn.imd.insiderthreat.filtro;

import br.ufrn.imd.insiderthreat.model.Atributos;

public class FiltroPorData implements Filtro<Atributos> {
	private String inicio;
	private String fim;

	public FiltroPorData(String inicio, String fim) {
		this.inicio = inicio;
		this.fim = fim;
	}
	
	public boolean validar(Atributos valor) {
		String data = valor.getData();
		return data.compareTo(inicio) != -1 && data.compareTo(fim) != 1;
	}
}
