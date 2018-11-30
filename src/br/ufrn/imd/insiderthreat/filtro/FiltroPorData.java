package br.ufrn.imd.insiderthreat.filtro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.ufrn.imd.insiderthreat.model.Atributos;

public class FiltroPorData implements Filtro<Atributos> {
	private LocalDate filtroDateInicial;
	private LocalDate filtroDateFinal;

	public FiltroPorData(LocalDate filtroDateInicial, LocalDate filtroDateFinal) {
		this.filtroDateInicial = filtroDateInicial;
		this.filtroDateFinal = filtroDateFinal;
	}
	
	public boolean validar(Atributos atributo) {
		LocalDate dataAtributo = null;
        try {
        	dataAtributo = LocalDate.parse(atributo.getData(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
        } catch (DateTimeParseException e) {
        	System.out.println(atributo.getData() + "  " + atributo.getId());
        	System.exit(0);
        }
        //compara se a data do dispositivo está entre a data especificada
        /*
         * -1 - dataDispositivo < filtroDateInicial
         * 0  - dataDispositivo == filtroDateInicial
         * 1  - dataDispositivo > filtroDateInicial
         * */
        return dataAtributo.compareTo(filtroDateInicial) >= 0 && dataAtributo.compareTo(filtroDateFinal) <= 0;
	}
}
