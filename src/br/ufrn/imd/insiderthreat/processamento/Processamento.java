package br.ufrn.imd.insiderthreat.processamento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import br.ufrn.imd.insiderthreat.filtro.Filtro;
import br.ufrn.imd.insiderthreat.filtro.FiltroAprovarTodos;

public abstract class Processamento<T> implements DAO<T> {
	private String file;
	
	Map<String, Object> criterios = new HashMap<String, Object>(); 
	
	public Processamento(String file) {
		this.file = file; 
	} 
			
	abstract protected Map<String, String> processarLinha(String linha);
	
	abstract protected T converter(Map<String, String> map);
	
	public List<T> processarComFiltro(Map<String, String> filtros) {
		ArrayList<T> valores = new ArrayList<T>();
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
	
	public List<T> processarComFiltro(Filtro<? super T> filtro) {
		ArrayList<T> valores = new ArrayList<T>();
		BufferedReader reader = null;
				
		try {
			// TODO: lembrete: fechar reader
			reader = new BufferedReader(new FileReader(file));
		
			String linha = reader.readLine();
			
			while (linha != null) {
				T valor = converter(processarLinha(linha));
				
				if (filtro.validar((T) valor)) {
					valores.add(valor);
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
	
	public List<T> processarTodos() {
		FiltroAprovarTodos filtro = new FiltroAprovarTodos();
		return processarComFiltro(filtro);
	}
}
