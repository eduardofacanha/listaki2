package br.com.buritech.listaki.model.bo;

import br.com.buritech.listaki.model.entidade.Usuario;

public interface SessaoO {

	public Usuario getUsuarioSessaoListAki();

	public void criarSessaoListAki(Usuario usuario);
	
	public void atualizarSessaoListAki();

}
