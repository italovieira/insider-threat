package br.ufrn.imd.insiderthreat.util;

import java.util.List;
import java.util.ArrayList;

/**
 * @author italo
 * 
 * Implementação da TAD árvore
 *
 * @param <T> o tipo dos valores de cada nó da árvore
 */
public abstract class Arvore<T> {
	
	private T valor;
	private List<Arvore<T>> filhos;
	
	public Arvore(T valor) {
		this.valor = valor;
		filhos = new ArrayList<Arvore<T>>();
	}
	
	public T getValor() {
		return valor;
	}
	
	/**
	 * Adicionar um novo nó filho a essa árvore
	 * 
	 * @param value novo nó
	 */
	public void adicionar(Arvore<T> value) {
		filhos.add(value);
	}
	
	/**
	 * Incluir uma lista de novos filhos a essa árvore
	 * 
	 * @param filhos a serem incluídos
	 */
	public void adicionar(List<Arvore<T>> filhos) {
		this.filhos.addAll(filhos);
	} 
	
	/**
	 * Obter um nó filho a partir de um índice
	 * 
	 * @param index indice do nó filho
	 * @return
	 */
	public Arvore<T> get(int index) {
		return filhos.get(index);
	}

	/**
	 * Obter todos os nós filhos da árvore
	 * 
	 * @return uma lista de nós filhos da árvore
	 */
	public List<Arvore<T>> getFilhos() {
		return filhos;
	}

	/**
	 * Indica se o nó possui filhos
	 * @return se possui filho o retorno é verdadeiro, em caso contrário é falso
	 */
	public boolean possuiFilhos() {
		return !this.filhos.isEmpty();
	}
}
