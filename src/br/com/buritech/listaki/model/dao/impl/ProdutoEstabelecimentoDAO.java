package br.com.buritech.listaki.model.dao.impl;

import br.com.buritech.listaki.model.dao.BaseDAO;
import br.com.buritech.listaki.model.entidade.ProdutoEstabelecimento;

public class ProdutoEstabelecimentoDAO extends BaseDAO<ProdutoEstabelecimento> {

	private static ProdutoEstabelecimentoDAO dao;

	private ProdutoEstabelecimentoDAO() {
		super();
	}

	public static ProdutoEstabelecimentoDAO getInstance() {
		if (dao == null) {
			dao = new ProdutoEstabelecimentoDAO();
		}

		return dao;
	}
}
