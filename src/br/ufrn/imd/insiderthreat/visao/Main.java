package br.ufrn.imd.insiderthreat.visao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
		
		//funcionalidades.buscarUsuariosPorPeriodo();
		//funcionalidades.test1();
        connect();

	}

    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:insider-threat-database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}

	