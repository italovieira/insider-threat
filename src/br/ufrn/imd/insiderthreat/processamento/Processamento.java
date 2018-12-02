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

/**
 * @author italo
 * 
 * Faz a leitura dos arquivos dos logs para o uso como objetos Modelo
 *
 * @param <T> tipo dos dados que serão obtidos do log
 */
public abstract class Processamento<T> implements DAO<T> {
	private String file;
	
	Map<String, Object> criterios = new HashMap<String, Object>(); 
	
	public Processamento(String file) {
		this.file = file; 
	} 
			
	/**
	 * Transformará os dados da linha num map com o chave sendo o campo e o valor sendo o valor do campo  
	 * 
	 * @param linha a ser transformada no map
	 * @return map do valor transformado que será usado para conversão da entidade
	 */
	abstract protected Map<String, String> processarLinha(String linha);
	
	/**
	 * Converte do map (chave: campo, valor: valor do campo) para a própria entidade 
	 * 
	 * @param map registro chave/valor a ser convertido
	 * @return nova entidade criada a partir do map
	 */
	abstract protected T converter(Map<String, String> map);
	
	/**
	 * Possibilitará filtrar (a partir das chaves definidas no método) uma lista de entidades diretamente do log que atendem um determinado critério baseado nos valores do seu campo
	 * 
	 * @param filtros
	 * 
	 * @return lista de valores filtrados do log
	 */
	public List<T> processarComFiltro(Map<String, String> filtros) {
		ArrayList<T> valores = new ArrayList<T>();
		BufferedReader reader = null;
				
		try {
			// TODO: lembrete: fechar reader
			reader = new BufferedReader(new FileReader(file));
		
			// Pular linha de cabeçalho
			reader.readLine();

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
	
	/* (non-Javadoc)
	 * @see br.ufrn.imd.insiderthreat.processamento.DAO#processarComFiltro(br.ufrn.imd.insiderthreat.filtro.Filtro)
	 */
	@Override
	public List<T> processarComFiltro(Filtro<? super T> filtro) {
		ArrayList<T> valores = new ArrayList<T>();
		BufferedReader reader = null;
				
		try {
			// TODO: lembrete: fechar reader
			reader = new BufferedReader(new FileReader(file));
		
			// Pular linha de cabeçalho
			reader.readLine();

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
	
	/* (non-Javadoc)
	 * @see br.ufrn.imd.insiderthreat.processamento.DAO#processarTodos()
	 */
	public List<T> processarTodos() {
		FiltroAprovarTodos filtro = new FiltroAprovarTodos();
		return processarComFiltro(filtro);
	}
}
