package br.com.buritech.listaki.core.presenter;

import com.google.inject.Inject;

import br.com.buritech.listaki.model.bo.SessaoO;
import br.com.buritech.listaki.model.entidade.Usuario;

public abstract class AbstractBasePresenter {

	@Inject
	protected SessaoO sessionBO;


	public Usuario getUsuarioSessaoListAki() {
		return sessionBO.getUsuarioSessaoListAki();
	}
	
	public void atualizarSessaoListAki() {
		sessionBO.atualizarSessaoListAki();

	}
}
