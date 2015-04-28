package br.com.buritech.listaki.model.dao.impl;

import br.com.buritech.listaki.model.dao.BaseDAO;
import br.com.buritech.listaki.model.entidade.Estabelecimento;

public class EstabelecimentoDAO extends BaseDAO<Estabelecimento> {

	private static EstabelecimentoDAO dao;

	private EstabelecimentoDAO() {
		super();
	}

	public static EstabelecimentoDAO getInstance() {
		if (dao == null) {
			dao = new EstabelecimentoDAO();
		}

		return dao;
	}
}
