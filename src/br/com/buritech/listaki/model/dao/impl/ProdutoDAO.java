package br.com.buritech.listaki.model.dao.impl;

import br.com.buritech.listaki.model.dao.BaseDAO;
import br.com.buritech.listaki.model.entidade.Produto;

public class ProdutoDAO extends BaseDAO<Produto> {

	private static ProdutoDAO dao;

	private ProdutoDAO() {
		super();
	}

	public static ProdutoDAO getInstance() {
		if (dao == null) {
			dao = new ProdutoDAO();
		}
		
		return dao;
	}
}
