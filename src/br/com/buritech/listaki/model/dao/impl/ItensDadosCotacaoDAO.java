package br.com.buritech.listaki.model.dao.impl;

import br.com.buritech.listaki.model.dao.BaseDAO;
import br.com.buritech.listaki.model.entidade.ItensDadoCotacao;

public class ItensDadosCotacaoDAO extends BaseDAO<ItensDadoCotacao> {

	private static ItensDadosCotacaoDAO dao;

	private ItensDadosCotacaoDAO() {
		super();
	}

	public static ItensDadosCotacaoDAO getInstance() {
		if (dao == null) {
			dao = new ItensDadosCotacaoDAO();
		}
		
		return dao;
	}
}
