package br.ufrn.imd.insiderthreat.model;

public class Dispositivo implements Modelo {
	private String id;
	private String data;
	private String atividade;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getAtividade() {
		return atividade;
	}
	
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
}
