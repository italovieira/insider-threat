package br.ufrn.imd.insiderthreat.visao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufrn.imd.insiderthreat.model.Dispositivo;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.processamento.Processamento;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoDispositivos;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoUsuarios;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

public class Main {

	public static void main(String[] args) {
		//exemplo1();
		//exemplo2();
		exemplo3();
	}
	
	// TODO: remover
	// Exemplo exibindo os nomes de todos os usuários
	private static void exemplo1() {
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
		private static void exemplo2() {
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
		private static void exemplo3() {
			ProcessamentoUsuarios processamentoUsuarios = new ProcessamentoUsuarios();
			
			HashMap<String, String> filtro = new HashMap<String, String>();
			// Filtro para os usuários apenas do papel "Engineer"
			filtro.put("id", "RES0962");
			
			List<Usuario> usuarios = processamentoUsuarios.processarComFiltro(filtro);

			//Cria uma lista com a arvore de usuários
			ArrayList<ArvoreModelo> usuariosArvore = new ArrayList<ArvoreModelo>();
			for (Usuario usuario : usuarios){
				ArvoreModelo arvoreUsuario = new ArvoreModelo(usuario);
				usuariosArvore.add(arvoreUsuario);
			}

			// TODO: remover
			//System.out.println("Usuário: " + ((Usuario)arvoreUsuario.getValor()).getNome());

			/*for (Usuario usuario : usuarios) {
				System.out.println(usuario.getNome());
			}*/

			//processarPC(arvoreUsuario);
		}

		private static void processarPC(Arvore arvoreUsuario){
			ProcessamentoDispositivos processamentoDispositivos = new ProcessamentoDispositivos();

			HashMap<String, String> filtro = new HashMap<String, String>();
			filtro.put("usuario", "DTAA/RES0962");

			List<Dispositivo> dispositivos = processamentoDispositivos.processarComFiltro(filtro);

			System.out.println("Qtd com filtro: " + dispositivos.size());

			for (Dispositivo dispositivo : dispositivos) {
				System.out.println(dispositivo.getPc());
			}
		}
}

	