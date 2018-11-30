package br.ufrn.imd.insiderthreat.visao;

public class Main {

	public static void main(String[] args) {
		Funcionalidades funcionalidades = new Funcionalidades();
		//funcionalidades.buscarUsuariosPorPeriodo();
		//funcionalidades.buscarUsuariosPorPapel();
		//ArvoreDao arvoreConfiguracoes = new ArvoreDao();
		//arvoreConfiguracoes.processarUsuarios();
		
		funcionalidades.buscarUsuariosPorPeriodo();
		funcionalidades.buscarUsuariosPorPapel();
		funcionalidades.test1();
		
		//ProcessamentoUsuarios proc = new ProcessamentoUsuarios();
		//List<Usuario> usuarios = proc.processarTodos();
	}

}

	