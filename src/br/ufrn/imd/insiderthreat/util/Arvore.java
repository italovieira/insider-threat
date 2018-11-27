package br.ufrn.imd.insiderthreat.util;

import java.util.List;
import java.util.ArrayList;

public class Arvore<T> {
	
	private T valor;
	private List<Arvore<T>> filhos;
	
	public Arvore(T valor) {
		this.valor = valor;
		filhos = new ArrayList<Arvore<T>>();
	}
	
	public Arvore(T valor, List<Arvore<T>> children) {
		this.valor = valor;
		this.filhos = children;
	}

	public Object getValor() {
		return valor;
	}
	
	public void adicionarNo(Arvore<T> value) {
		filhos.add(value);
	}
	
	public Arvore<T> getNo(int index) {
		return filhos.get(index);
	}
}
