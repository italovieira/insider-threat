package br.ufrn.imd.insiderthreat.controle;

import br.ufrn.imd.insiderthreat.model.*;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoDispositivos;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoUsuarios;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoHTTP;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoLogon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArvoreDao {

    List<Usuario> usuarios;

    // TODO: remover
    // Exemplo exibindo os nomes de todos os usuários
    public void exemplo1() {
        ProcessamentoUsuarios proc = new ProcessamentoUsuarios();
        List<Usuario> usuarios = proc.processarTodos();

        System.out.println("Quantidade total: " + usuarios.size());

        for (Modelo valor : usuarios) {
            Usuario usuario = (Usuario) valor;
            System.out.println(usuario.getNome());
        }
        System.out.println();
    }

    // TODO: remover
    // Exemplo exibindo os nomes dos usuários que atendem o critério de filtragem
    public void exemplo2() {
        ProcessamentoUsuarios proc = new ProcessamentoUsuarios();

        HashMap<String, String> filtro = new HashMap<String, String>();
        // Filtro para os usuários apenas do papel "Engineer"
        filtro.put("papel", "Engineer");

        List<Usuario> usuarios = proc.processarComFiltro(filtro);

        System.out.println("Qtd com filtro: " + usuarios.size());

        for (Usuario usuario : usuarios) {
            System.out.println(usuario.getNome());
        }
    }

    // TODO: remover
    // Exemplo exibindo os nomes dos usuários que atendem o critério de filtragem
    public void processarUsuarios() {
        ProcessamentoUsuarios processamentoUsuarios = new ProcessamentoUsuarios();

        HashMap<String, String> filtro = new HashMap<String, String>();
        // Filtro para o usuário de id RES0962"
        filtro.put("id", "RES0962");

        this.usuarios = processamentoUsuarios.processarComFiltro(filtro);

        //Cria uma lista com a arvore de usuários
        ArrayList<ArvoreModelo> usuariosArvore = new ArrayList<ArvoreModelo>();
        for (Usuario usuario : this.usuarios){
            ArvoreModelo arvoreUsuario = new ArvoreModelo(usuario);
            usuariosArvore.add(arvoreUsuario);
        }

        for(ArvoreModelo arvoreUsuario : usuariosArvore){
            System.out.println(((Usuario)arvoreUsuario.getValor()).getNome());
            processarDispositivosPC(arvoreUsuario);
            processarHttpPC(arvoreUsuario);
            processarLogonPC(arvoreUsuario);
        }

        // TODO: remover
        //System.out.println("Usuário: " + ((Usuario)arvoreUsuario.getValor()).getNome());

			/*for (Usuario usuario : usuarios) {
				System.out.println(usuario.getNome());
			}*/
    }

    public void processarDispositivosPC(Arvore arvoreUsuario){
        ProcessamentoDispositivos processamentoDispositivos = new ProcessamentoDispositivos();

        HashMap<String, String> filtro = new HashMap<String, String>();
        filtro.put("usuario", "DTAA/" + ((Usuario)arvoreUsuario.getValor()).getId());

        List<Dispositivo> dispositivos = processamentoDispositivos.processarComFiltro(filtro);

        //System.out.println("Qtd com filtro: " + dispositivos.size());

        for (Dispositivo dispositivo : dispositivos) {
            //cria o objeto para o pc
            Pc pc = new Pc(dispositivo.getPc());
            //cria a arvore de dispositivo
            ArvoreModelo arvoreDispositivo = new ArvoreModelo(dispositivo);

            if(!arvoreUsuario.getFilhos().isEmpty()){
                //variavel para verificar se o nó de dispositivo já foi inserido em algum nó pc
                boolean dispositivoInserido = false;
                //Buscar dentro da arvore se já existe o pc, caso exista só adiciona um filho para ela
                for(int i = 0; i < arvoreUsuario.getFilhos().size(); i++){
                    Arvore<Modelo> arvorePC = arvoreUsuario.get(i);
                    if(((Pc)arvorePC.getValor()).getId().equals(pc.getId())){
                        arvorePC.adicionar(arvoreDispositivo);
                        dispositivoInserido = true;
                        break;
                    }
                }

                //Caso contrário, Cria a arvore de pc com o filho dispositivo e adiciona dentro do filho do usuário
                if(!dispositivoInserido){
                    //cria a arvore para o pc
                    ArvoreModelo arvorePC = new ArvoreModelo(pc);
                    //adiciona a arvore de dispositivo como uma subarvore de PC
                    arvorePC.adicionar(arvoreDispositivo);
                    //adiciona arvore pc ao usuário
                    arvoreUsuario.adicionar(arvorePC);
                }
            }else{
                //Cria a arvore de pc com o filho e adiciona dentro do filho do usuário
                //cria a arvore para o pc
                ArvoreModelo arvorePC = new ArvoreModelo(pc);
                //adiciona a arvore de dispositivo como uma subarvore de PC
                arvorePC.adicionar(arvoreDispositivo);
                //adiciona arvore pc ao usuário
                arvoreUsuario.adicionar(arvorePC);
            }
        }
    }


    public void processarHttpPC(Arvore arvoreUsuario){
        ProcessamentoHTTP processamentoHttp = new ProcessamentoHTTP();

        HashMap<String, String> filtro = new HashMap<String, String>();
        filtro.put("usuario", "DTAA/" + ((Usuario)arvoreUsuario.getValor()).getId());

        List<Http> https = processamentoHttp.processarComFiltro(filtro);

        //System.out.println("Qtd com filtro: " + dispositivos.size());

        for (Http http : https) {
            //cria o objeto para o pc
            Pc pc = new Pc(http.getPc());
            //cria a arvore de dispositivo
            ArvoreModelo arvoreHttp = new ArvoreModelo(http);

            if(!arvoreUsuario.getFilhos().isEmpty()){
                //variavel para verificar se o nó de dispositivo já foi inserido em algum nó pc
                boolean dispositivoInserido = false;
                //Buscar dentro da arvore se já existe o pc, caso exista só adiciona um filho para ela
                for(int i = 0; i < arvoreUsuario.getFilhos().size(); i++){
                    Arvore<Modelo> arvorePC = arvoreUsuario.get(i);
                    if(((Pc)arvorePC.getValor()).getId().equals(pc.getId())){
                        arvorePC.adicionar(arvoreHttp);
                        dispositivoInserido = true;
                        break;
                    }
                }

                //Caso contrário, Cria a arvore de pc com o filho dispositivo e adiciona dentro do filho do usuário
                if(!dispositivoInserido){
                    //cria a arvore para o pc
                    ArvoreModelo arvorePC = new ArvoreModelo(pc);
                    //adiciona a arvore de dispositivo como uma subarvore de PC
                    arvorePC.adicionar(arvoreHttp);
                    //adiciona arvore pc ao usuário
                    arvoreUsuario.adicionar(arvorePC);
                }
            }else{
                //Cria a arvore de pc com o filho e adiciona dentro do filho do usuário
                //cria a arvore para o pc
                ArvoreModelo arvorePC = new ArvoreModelo(pc);
                //adiciona a arvore de dispositivo como uma subarvore de PC
                arvorePC.adicionar(arvoreHttp);
                //adiciona arvore pc ao usuário
                arvoreUsuario.adicionar(arvorePC);
            }
        }
    }

    public void processarLogonPC(Arvore arvoreUsuario){
        ProcessamentoLogon processamentoLogon = new ProcessamentoLogon();

        HashMap<String, String> filtro = new HashMap<String, String>();
        filtro.put("usuario", "DTAA/" + ((Usuario)arvoreUsuario.getValor()).getId());

        List<Logon> logons = processamentoLogon.processarComFiltro(filtro);

        //System.out.println("Qtd com filtro: " + dispositivos.size());

        for (Logon logon : logons) {
            //cria o objeto para o pc
            Pc pc = new Pc(logon.getPc());
            //cria a arvore de dispositivo
            ArvoreModelo arvoreLogon = new ArvoreModelo(logon);

            if(!arvoreUsuario.getFilhos().isEmpty()){
                //variavel para verificar se o nó de dispositivo já foi inserido em algum nó pc
                boolean dispositivoInserido = false;
                //Buscar dentro da arvore se já existe o pc, caso exista só adiciona um filho para ela
                for(int i = 0; i < arvoreUsuario.getFilhos().size(); i++){
                    Arvore<Modelo> arvorePC = arvoreUsuario.get(i);
                    if(((Pc)arvorePC.getValor()).getId().equals(pc.getId())){
                        arvorePC.adicionar(arvoreLogon);
                        dispositivoInserido = true;
                        break;
                    }
                }

                //Caso contrário, Cria a arvore de pc com o filho dispositivo e adiciona dentro do filho do usuário
                if(!dispositivoInserido){
                    //cria a arvore para o pc
                    ArvoreModelo arvorePC = new ArvoreModelo(pc);
                    //adiciona a arvore de dispositivo como uma subarvore de PC
                    arvorePC.adicionar(arvoreLogon);
                    //adiciona arvore pc ao usuário
                    arvoreUsuario.adicionar(arvorePC);
                }
            }else{
                //Cria a arvore de pc com o filho e adiciona dentro do filho do usuário
                //cria a arvore para o pc
                ArvoreModelo arvorePC = new ArvoreModelo(pc);
                //adiciona a arvore de dispositivo como uma subarvore de PC
                arvorePC.adicionar(arvoreLogon);
                //adiciona arvore pc ao usuário
                arvoreUsuario.adicionar(arvorePC);
            }
        }
    }
}
