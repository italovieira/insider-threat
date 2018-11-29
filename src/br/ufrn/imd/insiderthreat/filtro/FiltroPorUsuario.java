package br.ufrn.imd.insiderthreat.filtro;

import br.ufrn.imd.insiderthreat.model.Atributos;
import br.ufrn.imd.insiderthreat.model.Usuario;

public class FiltroPorUsuario implements Filtro {
	private String idUsuario;

	public FiltroPorUsuario(Usuario usuario) {
		this.idUsuario = usuario.getId();
	}

	@Override
	public boolean validar(Object obj) {
		try {
			Atributos atributo = (Atributos) obj;
			return atributo.getId() == "DTAA" + idUsuario;
		}
		catch (Exception e) {
			return false;
		}
	}
}
