package br.com.buritech.listaki.model.dao.impl;

import android.database.Cursor;

import java.util.List;

import br.com.buritech.listaki.model.dao.BaseDAO;
import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;

public class ItemListaProdutosUsuarioDAO extends
		BaseDAO<ItemListaProdutosUsuario> {

	private static ItemListaProdutosUsuarioDAO dao;

	private ItemListaProdutosUsuarioDAO() {
		super();
	}

	public static ItemListaProdutosUsuarioDAO getInstance() {
		if (dao == null) {
			dao = new ItemListaProdutosUsuarioDAO();
		}

		return dao;
	}

    //Retorna a quantidade de produtos que estão cadastrados para um determinado carrinho
    public int consultarQtdeProdutosCarrinho (ListaProdutosUsuario carrinho) {
        Cursor mCount = getHelper().getReadableDatabase().rawQuery(
                "SELECT COUNT(*) FROM ItemListaProdUsuario WHERE listausuario_id = ?",
                new String[] {carrinho.getId().toString()});

        mCount.moveToFirst();
        int qtdeProdutosCarrinho = mCount.getInt(0);
        mCount.close();

        return qtdeProdutosCarrinho;
    }
}
