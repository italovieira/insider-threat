package br.ufrn.imd.insiderthreat.util;

import br.ufrn.imd.insiderthreat.model.Modelo;

/**
 * @author italo
 *
 * Classe concreta da árvore que terá os valores do nó como o tipo Modelo
 * @see br.ufrn.imd.insiderthreat.model.Modelo
 */
public class ArvoreModelo extends Arvore<Modelo> {
	public ArvoreModelo(Modelo value) {
		super(value);
	}
}