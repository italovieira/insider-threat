package br.ufrn.imd.insiderthreat.controle;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.ufrn.imd.insiderthreat.filtro.FiltroPorData;
import br.ufrn.imd.insiderthreat.filtro.FiltroPorUsuario;
import br.ufrn.imd.insiderthreat.model.Atributos;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.model.Pc;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoAtributos;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoUsuarios;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

public class ArvoreDao {

    private Map<String, ArvoreModelo> usuariosArvore;


    public Map<String, ArvoreModelo> getUsuariosArvore() {
        return usuariosArvore;
    }

    public void setUsuariosArvore(Map<String, ArvoreModelo> usuariosArvore) {
        this.usuariosArvore = usuariosArvore;
    }


    public void criarArvorePerfisUsuarios(String campo, String filtroBusca){
        ProcessamentoUsuarios processamentoUsuarios = new ProcessamentoUsuarios();
        HashMap<String, String> filtro = new HashMap<String, String>();

        filtro.put(campo, filtroBusca);
        List<Usuario> usuarios = processamentoUsuarios.processarComFiltro(filtro);

        Map<String, ArvoreModelo> usuariosArvore = new HashMap<>();
        for (Usuario usuario : usuarios) {
            usuariosArvore.put("DTAA/" + usuario.getId(), new ArvoreModelo(usuario));
        }

        this.usuariosArvore = usuariosArvore;
    }

    public void criarArvoreUsuariosComFiltro(LocalDate filtroDateInicial, LocalDate filtroDateFinal){
        ProcessamentoUsuarios processamentoUsuarios = new ProcessamentoUsuarios();
        //busca todos os usuários e põe na arvore.
        //HashMap<String, String> filtro = new HashMap<String, String>();
        // Filtro para o usuário de id RES0962"
        //filtro.put("id", "YCB0005");
        List<Usuario> usuarios = processamentoUsuarios.processarTodos();
        //List<Usuario> usuarios = processamentoUsuarios.processarComFiltro(filtro);
        
        Map<String, ArvoreModelo> usuariosArvore = new HashMap<>();
        for (Usuario usuario : usuarios) {
        	usuariosArvore.put("DTAA/" + usuario.getId(), new ArvoreModelo(usuario));
        }
        
        this.usuariosArvore = usuariosArvore;

		criarNoAtributosPCComFiltro(filtroDateInicial, filtroDateFinal);
    }

    public Map<String, ArvoreModelo> filtrarPorPapel(String papel) {
		// Remove os usuários que não são do papel
    	Map<String, ArvoreModelo> arvoresFiltradas = new HashMap<>();

		for (Entry<String, ArvoreModelo> entry : this.usuariosArvore.entrySet()) {
			Usuario usuario = (Usuario) entry.getValue().getValor();
    		if (papel.equals(usuario.getPapel())) {
				arvoresFiltradas.put(entry.getKey(), entry.getValue());
			}
		}

		return arvoresFiltradas;
    }

    public void criarNoAtributosPCComFiltro(LocalDate filtroDateInicial, LocalDate filtroDateFinal){
        ProcessamentoAtributos processamentoAtributos = new ProcessamentoAtributos();
		List<Atributos> atributos = processamentoAtributos.processarComFiltro(new FiltroPorData(filtroDateInicial, filtroDateFinal));

        for (Atributos atributo : atributos) {
            criarNoPcComFiltro(atributo);
        }
    }

    public void criarNoPcComFiltro(Atributos atributo) {
        ArvoreModelo arvoreUsuario = getUsuariosArvore().get(atributo.getUsuario());

        //compara se a data do dispositivo está entre a data especificada
        /*
         * -1 - dataDispositivo < filtroDateInicial
         * 0  - dataDispositivo == filtroDateInicial
         * 1  - dataDispositivo > filtroDateInicial
         * */

			Pc pc = new Pc(atributo.getPc());
			ArvoreModelo arvoreAtributo = new ArvoreModelo(atributo);

            if(!arvoreUsuario.getFilhos().isEmpty()){
                //variavel para verificar se o nó de dispositivo já foi inserido em algum nó pc
                boolean dispositivoInserido = false;
                //Buscar dentro da arvore se já existe o pc, caso exista só adiciona um filho para ela
                
                for(int i = 0; i < arvoreUsuario.getFilhos().size(); i++){
                    Arvore<Modelo> arvorePC = arvoreUsuario.get(i);
                    if (((Pc) arvorePC.getValor()).getId().equals(pc.getId())) {
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
