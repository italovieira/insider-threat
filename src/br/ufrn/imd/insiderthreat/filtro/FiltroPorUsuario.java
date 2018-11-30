package br.ufrn.imd.insiderthreat.filtro;

import br.ufrn.imd.insiderthreat.model.Atributos;
import br.ufrn.imd.insiderthreat.model.Usuario;

public class FiltroPorUsuario implements Filtro<Atributos> {
	private String idUsuario;

	public FiltroPorUsuario(Usuario usuario) {
		this.idUsuario = usuario.getId();
	}

	@Override
	public boolean validar(Atributos atributo) {
		return atributo.getId() == "DTAA" + idUsuario;
	}
}
