package br.ufrn.imd.insiderthreat.visao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufrn.imd.insiderthreat.controle.ArvoreDao;
import br.ufrn.imd.insiderthreat.model.Dispositivo;
import br.ufrn.imd.insiderthreat.model.Pc;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoDispositivos;
import br.ufrn.imd.insiderthreat.processamento.ProcessamentoUsuarios;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;
import br.ufrn.imd.insiderthreat.visao.Funcionalidades;

public class Main {

	public static void main(String[] args) {
		Funcionalidades funcionalidades = new Funcionalidades();
		//funcionalidades.buscarUsuariosPorPeriodo();
		//funcionalidades.buscarUsuariosPorPapel();
		//ArvoreDao arvoreConfiguracoes = new ArvoreDao();
		//arvoreConfiguracoes.processarUsuarios();
		
		funcionalidades.buscarUsuariosPorPeriodo();
		funcionalidades.test1();
	}

}

	