package br.ufrn.imd.insiderthreat.controle;

import br.ufrn.imd.insiderthreat.model.Atividade;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class AtividadeDAO {
    private int quantidadeTarefasUsuario;
    private int quantidadetarefasUsuariosPapel;
    private int totalUsuariosPapel;
    private Usuario usuario;

    public int getQuantidadeTarefasUsuario() {
        return quantidadeTarefasUsuario;
    }

    public int getQuantidadetarefasUsuariosPapel() {
        return quantidadetarefasUsuariosPapel;
    }

    public int getTotalUsuariosPapel() {
        return totalUsuariosPapel;
    }

    public Usuario getUsuario() {
        return usuario;
    }


    public AtividadeDAO(){
        this.quantidadeTarefasUsuario = 0;
        this.quantidadetarefasUsuariosPapel = 0;
        this.totalUsuariosPapel = 0;
    }

    public void mediaTarefasUsuarioPapel(Map<String, ArvoreModelo> usuariosArvore, String usuarioId){
        Arvore<Modelo> arvoreUsuario = usuariosArvore.get("DTAA/" + usuarioId);
        for(Arvore<Modelo> arvorePc : arvoreUsuario.getFilhos()){
            this.quantidadeTarefasUsuario += arvorePc.getFilhos().size();
        }

        this.usuario = ((Usuario)arvoreUsuario.getValor());
        for (Map.Entry<String, ArvoreModelo> arvoreModelo : usuariosArvore.entrySet()) {
            if(((Usuario) arvoreModelo.getValue().getValor()).getPapel().equals(this.usuario.getPapel())){
                this.totalUsuariosPapel ++;
                for(Arvore<Modelo> arvorePcUsuarios : arvoreModelo.getValue().getFilhos()){
                    this.quantidadetarefasUsuariosPapel += arvorePcUsuarios.getFilhos().size();
                }
            }
            // ...
        }
    }
}
