package br.ufrn.imd.insiderthreat.visao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.ufrn.imd.insiderthreat.controle.ArvoreDao;
import br.ufrn.imd.insiderthreat.filtro.FiltroPorData;
import br.ufrn.imd.insiderthreat.filtro.FiltroPorUsuario;
import br.ufrn.imd.insiderthreat.model.Atributos;
import br.ufrn.imd.insiderthreat.model.Dispositivo;
import br.ufrn.imd.insiderthreat.model.Pc;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoAtributos;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoDispositivos;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoUsuarios;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;
import br.ufrn.imd.insiderthreat.visao.Funcionalidades;

public class Main {

	public static void main(String[] args) {
		Funcionalidades funcionalidades = new Funcionalidades();
		funcionalidades.iniciarAplicacao();
		//ArvoreDao arvoreConfiguracoes = new ArvoreDao();
		//arvoreConfiguracoes.processarUsuarios();
		
		
		ProcessamentoUsuarios proc = new ProcessamentoUsuarios();
		List<Usuario> usuarios = proc.processarTodos();
		Map<String, ArvoreModelo> mapUsuarios = usuarios.stream().collect(
				Collectors.toMap(x -> x.getId(), x -> new ArvoreModelo(x))
		);

		ProcessamentoAtributos procAtributos = new ProcessamentoAtributos();
		List<Atributos> atributos = procAtributos.processarComFiltro(new FiltroPorData("1", "2"));
		
		for (Atributos atributo : atributos) {
			ArvoreModelo arvore = mapUsuarios.get(atributo.getUsuario());
			
		}
	}

	public void makeTree() {
		
	}
}

	