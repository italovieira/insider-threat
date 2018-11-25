package br.ufrn.imd.insiderthreat.processamento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.imd.insiderthreat.model.Modelo;

public abstract class Processamento {
	private String file;
	
	Map<String, Object> criterios = new HashMap<String, Object>(); 
	
	public Processamento(String file) {
		this.file = file; 
	} 
			
	abstract protected Map<String, String> processarLinha(String linha);
	
	abstract protected Modelo converter(Map<String, String> map);
	
	public List<Modelo> processarComFiltro(Map<String, String> filtros) {
		ArrayList<Modelo> valores = new ArrayList<Modelo>();
		BufferedReader reader = null;
				
		try {
			// TODO: lembrete: fechar reader
			reader = new BufferedReader(new FileReader(file));
		
			String linha = reader.readLine();
			
			while (linha != null) {
				Map<String, String> map = processarLinha(linha);
				
				boolean inserir = true;
				for (String chave : filtros.keySet()) {
					if (!filtros.get(chave).equals(map.get(chave))) {
						inserir = false;
						break;
					}
				}
	
				if (inserir) {
					valores.add(converter(map));
				}
				
				linha = reader.readLine();				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return valores;
	}
	
	public List<Modelo> processarTodos() {
		return processarComFiltro(new HashMap<String, String>());
	}
}
