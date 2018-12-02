package br.ufrn.imd.insiderthreat.filtro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.ufrn.imd.insiderthreat.model.Atividade;

/**
 * @author italo
 *
 * Fara a validação da atividade para posterior filtragem, verificando se a data da atividade está no intervalo de tempo determinado  
 */
public class FiltroPorData implements Filtro<Atividade> {
	private LocalDate filtroDateInicial;
	private LocalDate filtroDateFinal;

	/**
	 * @param filtroDateInicial Início do intervalo de tempo
	 * @param filtroDateFinal Fim do intervalo de tempo
	 */
	public FiltroPorData(LocalDate filtroDateInicial, LocalDate filtroDateFinal) {
		this.filtroDateInicial = filtroDateInicial;
		this.filtroDateFinal = filtroDateFinal;
	}
	
	/*
	 * @see br.ufrn.imd.insiderthreat.filtro.Filtro#validar(java.lang.Object)
	 */
	@Override
	public boolean validar(Atividade atributo) {
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
