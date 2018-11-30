package br.ufrn.imd.insiderthreat.controle;

import java.util.Arrays;
import java.util.List;

import br.ufrn.imd.insiderthreat.model.Atributos;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

public class Histograma {
	private int[] horas = new int[24];
	private Usuario usuario;
	private String papel;
	
	public Histograma(ArvoreModelo arvore) throws Exception {
		if (!(arvore.getValor() instanceof Usuario)) {
			throw new Exception("Não é uma árvore de usuários");
		}

		this.usuario = (Usuario) arvore.getValor();
		gerarHistograma(arvore);
	}

	public Histograma(List<ArvoreModelo> arvores)  {
		gerarHistograma(arvores);
		Usuario usuario = (Usuario) arvores.get(0).getValor();
		this.papel = usuario.getPapel();
	}

	private void gerarHistograma(Arvore<Modelo> arvore) {
		Modelo modelo = arvore.getValor();

		if (modelo instanceof Atributos) {
			Atributos atributo = (Atributos) modelo;
			somarAtividade(atributo);
			return;
		}
		
		for (Arvore<Modelo> arvoreTemp : arvore.getFilhos()) {
			gerarHistograma(arvoreTemp);
		}
	} 

	private void gerarHistograma(List<ArvoreModelo> arvores) {
		for (ArvoreModelo arvore : arvores) {
			gerarHistograma(arvore);
		}
		
		int n = arvores.size();
		
		for (int i = 0; i < horas.length; i++) {
			horas[i] = Math.round((float) horas[i] / n);
		}
	} 
	
	private void somarAtividade(Atributos atributo) {
		String data = atributo.getData();
		// Extrai apenas o valor das horas da data
		short hora = Short.parseShort(data.split(" ")[1].split(":")[0]);
		horas[hora]++;
	}
	
	public double calcularDistancia(Histograma histograma) {
		double distancia = 0;

		for (int i = 0; i < 24; i++) {
			distancia += Math.pow(this.horas[i] - histograma.horas[i], 2);
		}

		return Math.sqrt(distancia);
	}
	
	// TODO: apenas para testes
	public void imprimir() {
		System.out.println(Arrays.toString(horas));
	}
	
	
}
