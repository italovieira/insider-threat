package br.ufrn.imd.insiderthreat.controle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.ufrn.imd.insiderthreat.filtro.FiltroPorData;
import br.ufrn.imd.insiderthreat.filtro.FiltroPorUsuario;
import br.ufrn.imd.insiderthreat.model.Atividade;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.model.Pc;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoAtividades;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoUsuarios;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

/**
 * @author claudio
 *
 */
public class ArvoreDao {

    private Map<String, ArvoreModelo> usuariosArvore;
    
    public ArvoreDao() {
    }


    /**
     * @return a floresta de árvores dos usuários
     */
    public Map<String, ArvoreModelo> getUsuariosArvore() {
        return usuariosArvore;
    }

    /**
     * Define a floresta de árvores dos usuários como um argumento já existente
     * @param usuariosArvore argumento que indica qual será a floresta de árvores
     */
    public void setUsuariosArvore(Map<String, ArvoreModelo> usuariosArvore) {
        this.usuariosArvore = usuariosArvore;
    }

    /**
     * Gera a floresta de árvore a partir dos usuários filtrados 
     * @param campo qual o campo do usuário que vai ser filtrado 
     * @param filtroBusca qual o filtro vai ser aplicado ao campo do usuário escolhido
     */
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

    /**
     * Gera a floresta de árvores dos usuários que possuem atividades no intervalo de tempo indicado
     * 
     * @param filtroDateInicial início do intervalo de tempo 
     * @param filtroDateFinal fim do intervalo de tempo
     */
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
		removerUsuariosSemAtividade();
    }
    
    /**
     * Deixa na floresta apenas as árvores dos usuários que tem atividades
     */
    private void removerUsuariosSemAtividade() {
    	usuariosArvore.entrySet().removeIf(x -> !x.getValue().possuiFilhos());
    }

    public List<ArvoreModelo> filtrarPorPapel(String papel) {
		// Remove os usuários que não são do papel
    	List<ArvoreModelo> arvoresFiltradas = new ArrayList<>();

		for (Entry<String, ArvoreModelo> entry : this.usuariosArvore.entrySet()) {
			Usuario usuario = (Usuario) entry.getValue().getValor();
    		if (papel.equals(usuario.getPapel())) {
				arvoresFiltradas.add(entry.getValue());
			}
		}

		return arvoresFiltradas;
    }

    /**
     * @param filtroDateInicial
     * @param filtroDateFinal
     */
    public void criarNoAtributosPCComFiltro(LocalDate filtroDateInicial, LocalDate filtroDateFinal){
        ProcessamentoAtividades processamentoAtividades = new ProcessamentoAtividades();
		List<Atividade> atividades = processamentoAtividades.processarComFiltro(new FiltroPorData(filtroDateInicial, filtroDateFinal));

        for (Atividade atributo : atividades) {
            criarNoPcComFiltro(atributo);
        }
    }

    /**
     * Adiciona a atividade a árvore do usuário que a executou
     * @param atividade executada pelo usuário
     */
    public void criarNoPcComFiltro(Atividade atividade) {
        ArvoreModelo arvoreUsuario = getUsuariosArvore().get(atividade.getUsuario());

        //compara se a data do dispositivo está entre a data especificada
        /*
         * -1 - dataDispositivo < filtroDateInicial
         * 0  - dataDispositivo == filtroDateInicial
         * 1  - dataDispositivo > filtroDateInicial
         * */

			Pc pc = new Pc(atividade.getPc());
			ArvoreModelo arvoreAtributo = new ArvoreModelo(atividade);

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
