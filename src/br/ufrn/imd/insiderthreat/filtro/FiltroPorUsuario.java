package br.ufrn.imd.insiderthreat.filtro;

import br.ufrn.imd.insiderthreat.model.Atividade;
import br.ufrn.imd.insiderthreat.model.Usuario;

/**
 * @author italo
 *
 * Verifica se uma atividade pertence ao usuário especificado
 */
public class FiltroPorUsuario implements Filtro<Atividade> {
	private String idUsuario;

	/**
	 * @param usuario usuário usado para a verificação
	 */
	public FiltroPorUsuario(Usuario usuario) {
		this.idUsuario = usuario.getId();
	}

	/* (non-Javadoc)
	 * @see br.ufrn.imd.insiderthreat.filtro.Filtro#validar(java.lang.Object)
	 */
	@Override
	public boolean validar(Atividade atributo) {
		return atributo.getId() == "DTAA" + idUsuario;
	}
}
