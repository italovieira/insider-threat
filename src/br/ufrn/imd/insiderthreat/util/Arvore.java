package br.ufrn.imd.insiderthreat.util;

import java.util.List;
import java.util.ArrayList;

public abstract class Arvore<T> {
	
	private T valor;
	private List<Arvore<T>> children;
	
	public Arvore(T valor) {
		this.valor = valor;
		children = new ArrayList<Arvore<T>>();
	}
	
	public Arvore(T valor, List<Arvore<T>> children) {
		this.valor = valor;
		this.children = children;
	}

	public Object getValor() {
		return valor;
	}
	
	public void adicionarNo(Arvore<T> value) {
		children.add(value);
	}
}