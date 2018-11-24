package br.ufrn.imd.insiderthreat.util.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.insiderthreat.util.ValorNo;

public abstract class Processamento {
	private String file;
	
	public Processamento(String file) {
		this.file = file; 
	} 
			
	protected abstract ValorNo processarLinha(String linha);
	
	public List<ValorNo> processarArquivo() {
		ArrayList<ValorNo> valores = new ArrayList<ValorNo>();
		BufferedReader reader = null;
				
		try {
			// TODO: lembrete: fechar reader
			reader = new BufferedReader(new FileReader(file));
		
			String linha = reader.readLine();
			
			while (linha != null) {
				ValorNo valor = processarLinha(linha);
				valores.add(valor);
				
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
}
