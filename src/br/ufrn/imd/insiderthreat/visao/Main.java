package br.ufrn.imd.insiderthreat.visao;

import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvorePerfilUsuario;
import br.ufrn.imd.insiderthreat.util.ValorNo;

public class Main {

	public static void main(String[] args) {
		Arvore<ValorNo> a = new ArvorePerfilUsuario(new Usuario());
		a.equals(null);
	}
}