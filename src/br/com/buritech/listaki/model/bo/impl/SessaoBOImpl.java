package br.com.buritech.listaki.model.bo.impl;

import br.com.buritech.listaki.model.bo.SessaoO;
import br.com.buritech.listaki.model.entidade.Usuario;

public class SessaoBOImpl implements SessaoO {
	
	private Usuario usuarioSessao;

	@Override
	public Usuario getUsuarioSessaoListAki() {
		return usuarioSessao;
	}

	@Override
	public void criarSessaoListAki(Usuario usuario) {
		usuarioSessao = usuario;
		// iniciar outros coisas se necessario
	}

	@Override
	public void atualizarSessaoListAki() {
		// atualizar dados da sessao se necessario
	}

}
