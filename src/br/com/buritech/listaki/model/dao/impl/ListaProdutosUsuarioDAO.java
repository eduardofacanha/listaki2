package br.com.buritech.listaki.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;
import br.com.buritech.listaki.model.dao.BaseDAO;
import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.Produto;
import br.com.buritech.listaki.model.entidade.Usuario;

public class ListaProdutosUsuarioDAO extends BaseDAO<ListaProdutosUsuario> {

	private static ListaProdutosUsuarioDAO instance;

	private ListaProdutosUsuarioDAO() {
		super();
	}

	public static ListaProdutosUsuarioDAO getInstance() {
		if (instance == null) {
			instance = new ListaProdutosUsuarioDAO();
		}

		return instance;
	}

    public ListaProdutosUsuario buscarListaDeProdutosPorId(Long idLista) {
        List<ListaProdutosUsuario> listaProdutosUsuarios = findForAttribute("id", idLista);

        return listaProdutosUsuarios.get(0);
    }
	
	public ListaProdutosUsuario buscarListaDeProdutosPorNome(String nomeLista) {
		
		// TODO: fazer o nome do atributos pegar de uma constante
		List<ListaProdutosUsuario> listaProdutosUsuarios = findForAttribute("nomeLista", nomeLista);
		
		if (listaProdutosUsuarios != null && listaProdutosUsuarios.size() > 0) {
			//TODO:@deveria carregar o list de itens aqui
			ListaProdutosUsuario listaProdutosUsuario = listaProdutosUsuarios.get(0);
			return listaProdutosUsuario;
		
		} else {//TODO @adri: retirar, apenas para teste
			
			final ListaProdutosUsuario listaProdutosUsuario = new ListaProdutosUsuario();
			
			final List<Produto> listaProdutos = new ArrayList<Produto>();
			listaProdutos.add(new Produto(new Long(1), "Macarrão", "07896224600057"));
			listaProdutos.add(new Produto(new Long(2), "Farinha", "07896224600118"));
			listaProdutos.add(new Produto(new Long(3), "Arroz Integral", "07896224603225"));
			listaProdutos.add(new Produto(new Long(4), "Feijão", "07896224601580"));
			listaProdutos.add(new Produto(new Long(5), "Açúcar", "07896224601597"));
			

			final List<ItemListaProdutosUsuario> itensListaProdutos = new ArrayList<ItemListaProdutosUsuario>();
			itensListaProdutos.add(new ItemListaProdutosUsuario(new Long(10), "Desc1", "07896224600057", new Integer(15), listaProdutos.get(0), listaProdutosUsuario));
			itensListaProdutos.add(new ItemListaProdutosUsuario(new Long(11), "Desc2", "07896224600118", new Integer(25), listaProdutos.get(1), listaProdutosUsuario));
			itensListaProdutos.add(new ItemListaProdutosUsuario(new Long(12), "Desc3", "07896224603225", new Integer(365), listaProdutos.get(2), listaProdutosUsuario));
			itensListaProdutos.add(new ItemListaProdutosUsuario(new Long(13), "Desc4", "07896224600057", new Integer(105), listaProdutos.get(0), listaProdutosUsuario));
			itensListaProdutos.add(new ItemListaProdutosUsuario(new Long(14), "Desc5", "07896224600118", new Integer(205), listaProdutos.get(1), listaProdutosUsuario));
			itensListaProdutos.add(new ItemListaProdutosUsuario(new Long(15), "Desc6", "07896224603225", new Integer(100), listaProdutos.get(2), listaProdutosUsuario));
			
			
			listaProdutosUsuario.setDataCriacaoLista(new Date());
			listaProdutosUsuario.setDataEnvioListaWS(new Date());
			listaProdutosUsuario.setId(new Long(1));
			listaProdutosUsuario.setNomeLista("Lista Maio");
			listaProdutosUsuario.setUsuario(new Usuario());
			listaProdutosUsuario.setListaItens(itensListaProdutos);
			
			return listaProdutosUsuario;
		}
		
		//return null;
	}

    public List<ListaProdutosUsuario> buscarListaDeProdutosPorUsuario (Usuario usuario) {
        List<ListaProdutosUsuario> listaProdutosUsuarios = findForAttribute("usuario_id", usuario.getId());

        return listaProdutosUsuarios;
    }


}
