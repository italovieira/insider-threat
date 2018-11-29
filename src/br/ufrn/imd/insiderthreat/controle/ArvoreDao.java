package br.ufrn.imd.insiderthreat.controle;

import br.ufrn.imd.insiderthreat.model.*;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoDispositivos;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoUsuarios;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoHTTP;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoLogon;
import sun.rmi.runtime.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ArvoreDao {

    private ArrayList<ArvoreModelo> usuariosArvore;


    public ArrayList<ArvoreModelo> getUsuariosArvore() {
        return usuariosArvore;
    }

    public void setUsuariosArvore(ArrayList<ArvoreModelo> usuariosArvore) {
        this.usuariosArvore = usuariosArvore;
    }


    public void criarArvoreUsuariosComFiltro(LocalDate filtroDateInicial, LocalDate filtroDateFinal){
        ProcessamentoUsuarios processamentoUsuarios = new ProcessamentoUsuarios();
        //busca todos os usuários e põe na arvore.
        //HashMap<String, String> filtro = new HashMap<String, String>();
        // Filtro para o usuário de id RES0962"
        //filtro.put("id", "RES0962");
        List<Usuario> usuarios = processamentoUsuarios.processarTodos();

        //Cria uma lista com a arvore de usuários
        this.usuariosArvore = new ArrayList<ArvoreModelo>();
        for (Usuario usuario : usuarios){
            //Crio uma pré-arvore de usuário
            ArvoreModelo arvoreUsuario = new ArvoreModelo(usuario);
            //busco os nós de dispositivos para o usuário
            criarNóDispositivosPCComFiltro(arvoreUsuario, filtroDateInicial, filtroDateFinal);

            //caso o usuário tenha filhos ele será adicionado na arvore de usuários
            if(arvoreUsuario.possuiFilhos()){
                usuariosArvore.add(arvoreUsuario);
            }

        }

    }

    public void criarNóDispositivosPCComFiltro(Arvore arvoreUsuario, LocalDate filtroDateInicial, LocalDate filtroDateFinal){
        ProcessamentoDispositivos processamentoDispositivos = new ProcessamentoDispositivos();
        HashMap<String, String> filtro = new HashMap<String, String>();
        filtro.put("usuario", "DTAA/" + ((Usuario)arvoreUsuario.getValor()).getId());
        List<Dispositivo> dispositivos = processamentoDispositivos.processarComFiltro(filtro);

        for (Dispositivo dispositivo : dispositivos) {
            criarNoPcComFiltro(arvoreUsuario, filtroDateInicial, filtroDateFinal, dispositivo);
        }
    }

    public void criarNóHttpPCComFiltro(Arvore arvoreUsuario, LocalDate filtroDateInicial, LocalDate filtroDateFinal){
        ProcessamentoHTTP processamentoHttp = new ProcessamentoHTTP();
        HashMap<String, String> filtro = new HashMap<String, String>();
        filtro.put("usuario", "DTAA/" + ((Usuario)arvoreUsuario.getValor()).getId());
        List<Http> https = processamentoHttp.processarComFiltro(filtro);

        for (Http http : https) {
            criarNoPcComFiltro(arvoreUsuario, filtroDateInicial, filtroDateFinal, http);
        }


    }

    public void criarNóLogonPCComFiltro(Arvore arvoreUsuario, LocalDate filtroDateInicial, LocalDate filtroDateFinal){
        ProcessamentoLogon processamentoLogon = new ProcessamentoLogon();
        HashMap<String, String> filtro = new HashMap<String, String>();
        filtro.put("usuario", "DTAA/" + ((Usuario)arvoreUsuario.getValor()).getId());
        List<Logon> logons = processamentoLogon.processarComFiltro(filtro);

        for (Logon logon : logons) {
            criarNoPcComFiltro(arvoreUsuario, filtroDateInicial, filtroDateFinal, logon);
        }
    }

    public void criarNoPcComFiltro(Arvore arvoreUsuario, LocalDate filtroDateInicial, LocalDate filtroDateFinal, Atributos atributo){
        LocalDate dataAtributo = LocalDate.parse(atributo.getData(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
        //compara se a data do dispositivo está entre a data especificada
        /*
         * -1 - dataDispositivo < filtroDateInicial
         * 0  - dataDispositivo == filtroDateInicial
         * 1  - dataDispositivo > filtroDateInicial
         * */
        if(dataAtributo.compareTo(filtroDateInicial) >= 0 && dataAtributo.compareTo(filtroDateFinal) <= 0){
            Pc pc = new Pc(atributo.getPc());
            ArvoreModelo arvoreAtributo = new ArvoreModelo(atributo);

            if(!arvoreUsuario.getFilhos().isEmpty()){
                //variavel para verificar se o nó de dispositivo já foi inserido em algum nó pc
                boolean dispositivoInserido = false;
                //Buscar dentro da arvore se já existe o pc, caso exista só adiciona um filho para ela
                for(int i = 0; i < arvoreUsuario.getFilhos().size(); i++){
                    Arvore<Modelo> arvorePC = arvoreUsuario.get(i);
                    if(((Pc)arvorePC.getValor()).getId().equals(pc.getId())){
                        arvorePC.adicionar(arvoreAtributo);
                        dispositivoInserido = true;
                        break;
                    }
                }

                //Caso contrário, Cria a arvore de pc com o filho dispositivo e adiciona dentro do filho do usuário
                if(!dispositivoInserido){
                    //cria a arvore para o pc
                    ArvoreModelo arvorePC = new ArvoreModelo(pc);
                    //adiciona a arvore de dispositivo como uma subarvore de PC
                    arvorePC.adicionar(arvoreAtributo);
                    //adiciona arvore pc ao usuário
                    arvoreUsuario.adicionar(arvorePC);
                }
            }else{
                //Cria a arvore de pc com o filho e adiciona dentro do filho do usuário
                //cria a arvore para o pc
                ArvoreModelo arvorePC = new ArvoreModelo(pc);
                //adiciona a arvore de dispositivo como uma subarvore de PC
                arvorePC.adicionar(arvoreAtributo);
                //adiciona arvore pc ao usuário
                arvoreUsuario.adicionar(arvorePC);
            }
        }
    }
}
