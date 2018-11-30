package br.ufrn.imd.insiderthreat.filtro;

import java.time.LocalDate;

import br.ufrn.imd.insiderthreat.model.Atributos;

public class FiltroPorData implements Filtro<Atributos> {
	private LocalDate inicio;
	private LocalDate fim;

	public FiltroPorData(LocalDate filtroDateInicial, LocalDate filtroDateFinal) {
		this.inicio = filtroDateInicial;
		this.fim = filtroDateFinal;
	}
	
	public boolean validar(Atributos valor) {
		String data = valor.getData();
		return data.compareTo(inicio) != -1 && data.compareTo(fim) != 1;
	}
}
