package br.ufrn.imd.insiderthreat.visao;

import java.util.InputMismatchException;

public class Main {

	public static void main(String[] args) {
		Funcionalidades funcionalidades = new Funcionalidades();
		try{
		    funcionalidades.iniciarAplicacao();
        }catch (
        InputMismatchException ex){
            System.err.println("O número digitado não corresponde a um inteiro.");
            System.exit(0);
        }
		//funcionalidades.buscarUsuariosPorPeriodo();
		//funcionalidades.buscarUsuariosPorPapelPerfil();
		//ArvoreDao arvoreConfiguracoes = new ArvoreDao();
		//arvoreConfiguracoes.processarUsuarios();

		//funcionalidades.buscarUsuariosPorPeriodo();
		//funcionalidades.buscarUsuariosPorIdPerfil();
		//funcionalidades.test1();
        //funcionalidades.test1();
	}

}

	