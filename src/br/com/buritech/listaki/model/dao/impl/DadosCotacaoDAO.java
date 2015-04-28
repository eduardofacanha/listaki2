package br.com.buritech.listaki.model.dao.impl;

import br.com.buritech.listaki.model.dao.BaseDAO;
import br.com.buritech.listaki.model.entidade.DadosCotacaoListaUsuario;
import br.com.buritech.listaki.model.entidade.IEntidade;

public class DadosCotacaoDAO extends BaseDAO<DadosCotacaoListaUsuario> {

	private static DadosCotacaoDAO dao;

	private DadosCotacaoDAO() {
		super();
	}

	public static DadosCotacaoDAO getInstance() {
		if (dao == null) {
			dao = new DadosCotacaoDAO();
		}
		
		return dao;
	}
}
