package br.ufrn.imd.insiderthreat.util.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.insiderthreat.model.Tipos;
import br.ufrn.imd.insiderthreat.util.ValorNo;

public abstract class Processamento {
	private String file; 
			
	protected abstract ValorNo processarLinha(String linha);
	
	public List<ValorNo> processarArquivo() {
		BufferedReader reader = null;
		try {
			// lembrete: fechar
			reader = new BufferedReader(new FileReader(file));
		
			ArrayList<ValorNo> valores = new ArrayList<ValorNo>();
			String linha = reader.readLine();
			
			while (linha != null) {
				ValorNo valor = processarLinha(linha);
				valores.add(valor);
				
				linha = reader.readLine();				
			}
			
			return valores;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
