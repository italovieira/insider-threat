package br.ufrn.imd.insiderthreat.model;

/**
 * @author claudio
 *
 * Entidade Pc
 */
public class Pc implements Modelo{
    private String id;

    public Pc(String id){
        this.id = id;
    }

    public String getId() { return id;}

    public void setId(String id) {this.id = id;}
}
