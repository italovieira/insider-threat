package br.ufrn.imd.insiderthreat.controle;

import java.util.Arrays;
import java.util.List;

import br.ufrn.imd.insiderthreat.model.Atividade;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

/**
 * @author italo
 *
 * Representação do histograma dos perfis dos usuários ou do perfil médio
 */
public class HistogramaDao {
	private int[] horas = new int[24];

	/**
	 * @see gerarHistograma
	 * @throws Exception
	 */
	public HistogramaDao(ArvoreModelo arvore) throws Exception {
		if (!(arvore.getValor() instanceof Usuario)) {
			throw new Exception("Não é uma árvore de usuários");
		}

		gerarHistograma(arvore);
	}

	/**
	 * @see gerarHistograma
	 * @param arvores
	 */
	public HistogramaDao(List<ArvoreModelo> arvores)  {
		gerarHistograma(arvores);
	}

	/**
	 * Popula a quantidade para cada hora de atividades encontradas na árvore
	 * @param arvore
	 */
	private void gerarHistograma(Arvore<Modelo> arvore) {
		Modelo modelo = arvore.getValor();

		if (modelo instanceof Atividade) {
			Atividade atributo = (Atividade) modelo;
			somarAtividade(atributo);
			return;
		}
		
		for (Arvore<Modelo> arvoreTemp : arvore.getFilhos()) {
			gerarHistograma(arvoreTemp);
		}
	} 

	
	/**
	 * Popula para cada hora a média de atividades para cada árvore do conjunto de árvores
	 * @param arvores
	 */
	private void gerarHistograma(List<ArvoreModelo> arvores) {
		for (ArvoreModelo arvore : arvores) {
			gerarHistograma(arvore);
		}
		
		int n = arvores.size();
		
		for (int i = 0; i < horas.length; i++) {
			horas[i] = Math.round((float) horas[i] / n);
		}
	} 
	
	/**
	 * Acumula a quantidade de quantidades em 1 para a hora em que ocorreu a atividade
	 * @param atributo
	 */
	private void somarAtividade(Atividade atributo) {
		String data = atributo.getData();
		// Extrai apenas o valor das horas da data
		short hora = Short.parseShort(data.split(" ")[1].split(":")[0]);
		horas[hora]++;
	}
	
	/**
	 * Calcula a distância euclidiana dos dados deste histograma para outro determinado
	 * 
	 * @param histogramaDao
	 * @return a distância euclidiana dos histogramas
	 */
	public double calcularDistancia(HistogramaDao histogramaDao) {
		double distancia = 0;

		for (int i = 0; i < 24; i++) {
			distancia += Math.pow((this.horas[i] - histogramaDao.horas[i]), 2);
		}

		return Math.sqrt(distancia);
	}
	
	/**
	 * Imprime separado por vírgulas a quantidade de atividades para cada hora (de 0h a 23h)
	 */
	public void imprimir() {
		System.out.println(Arrays.toString(horas));
	}
}
