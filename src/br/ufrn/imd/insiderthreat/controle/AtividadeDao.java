package br.ufrn.imd.insiderthreat.controle;

import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

import java.util.*;

/**
 * @author italo
 *
 * Responsável por obter quantitativos sobre as tarefas realizadas pelos usuários
 * 
 */
public class AtividadeDao {
    private int quantidadeTarefasUsuario;
    private int quantidadetarefasUsuariosPapel;
    private int totalUsuariosPapel;
    private Usuario usuario;

    public AtividadeDao(){
        this.quantidadeTarefasUsuario = 0;
        this.quantidadetarefasUsuariosPapel = 0;
        this.totalUsuariosPapel = 0;
    }

    /**
     * Indica a quantidade de tarefas realizadas pelo usuários
     * 
     * @return quantidade de tarefas do usuário
     */
    public int getQuantidadeTarefasUsuario() {
        return quantidadeTarefasUsuario;
    }

    /**
     * Indica a quantidade de tarefas realizadas pelos usuários do papel definido previamente
     * 
     * @return quantidade de tarefas dos usuários do papel
     */
    public int getQuantidadetarefasUsuariosPapel() {
        return quantidadetarefasUsuariosPapel;
    }

    /**
     * @return Obtém a quantidade de usuários do papel
     */
    public int getTotalUsuariosPapel() {
        return totalUsuariosPapel;
    }

    /**
     * @return Obtém a instância do usuário
     */
    public Usuario getUsuario() {
        return usuario;
    }


    /**
     * Calcula a média de tarefas dos usuários do papel
     * 
     * @param usuariosArvore floresta de árvores dos usuários de mesmo papel
     * @param usuarioId id do usuários que será comparado com a média das atividades dos usuários do seu papel
     */
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
