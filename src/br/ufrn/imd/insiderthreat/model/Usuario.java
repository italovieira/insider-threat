package br.ufrn.imd.insiderthreat.model;

import br.ufrn.imd.insiderthreat.util.ValorNo;

public class Usuario implements ValorNo {
	String id;
	String nome;
	String cpf;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
