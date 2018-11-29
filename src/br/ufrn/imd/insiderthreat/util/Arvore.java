package br.ufrn.imd.insiderthreat.util;

import java.util.List;
import java.util.ArrayList;

public abstract class Arvore<T> {
	
	private T valor;
	private List<Arvore<T>> filhos;
	
	public Arvore(T valor) {
		this.valor = valor;
		filhos = new ArrayList<Arvore<T>>();
	}
	
	public Object getValor() {
		return valor;
	}
	
	// Adicionar um novo nó filho a essa árvore
	public void adicionar(Arvore<T> value) {
		filhos.add(value);
	}
	
	// Incluir uma lista de novos filhos a essa árvore
	public void adicionar(List<Arvore<T>> filhos) {
		this.filhos.addAll(filhos);
	} 
	
	// Obter um nó filho a partir de um índice
	public Arvore<T> get(int index) {
		return filhos.get(index);
	}

	//Obter todos os nós filhos da árvore
	public List<Arvore<T>> getFilhos() {
		return filhos;
	}

	//Returna se o nó possui filhos
	public boolean possuiFilhos(){ return !this.filhos.isEmpty();}
}
