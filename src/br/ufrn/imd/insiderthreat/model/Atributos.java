package br.ufrn.imd.insiderthreat.model;

public class Atributos implements Modelo {
    private String id;
    private String data;
    private String usuario;
    private String pc;
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


    public String getUsuario() {return usuario;}

    public void setUsuario(String usuario) {this.usuario = usuario;}

    public String getPc() {return pc;}

    public void setPc(String pc) {this.pc = pc;}

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }
}
