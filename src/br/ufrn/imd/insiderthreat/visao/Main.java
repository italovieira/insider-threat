package br.ufrn.imd.insiderthreat.visao;

import java.util.HashMap;
import java.util.List;

import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.processamento.Processamento;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoUsuarios;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvorePerfilUsuario;

public class Main {

	public static void main(String[] args) {
		Arvore<Modelo> a = new ArvorePerfilUsuario(new Usuario());
		a.equals(null);
		
		exemplo1();
		exemplo2();
	}
	
	// TODO: remover
	// Exemplo exibindo os nomes de todos os usuários
	private static void exemplo1() {
		Processamento proc = new ProcessamentoUsuarios();
		List<Modelo> usuarios = proc.processarTodos();
		
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
			Processamento proc = new ProcessamentoUsuarios();
			
			HashMap<String, String> filtro = new HashMap<String, String>();
			// Filtro para os usuários apenas do papel "Engineer"
			filtro.put("papel", "Engineer");
			
			List<Modelo> usuarios = proc.processarComFiltro(filtro);
			
			System.out.println("Qtd com filtro: " + usuarios.size());
			
			for (Modelo valor : usuarios) {
				Usuario usuario = (Usuario) valor;
				System.out.println(usuario.getNome());
			}
		}
}

	