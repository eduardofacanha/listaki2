package br.com.buritech.listaki.model.bo.impl;

import java.util.List;

import br.com.buritech.listaki.model.bo.ProdutoBO;
import br.com.buritech.listaki.model.dao.impl.DadosCotacaoDAO;
import br.com.buritech.listaki.model.dao.impl.EstabelecimentoDAO;
import br.com.buritech.listaki.model.dao.impl.ItemListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.dao.impl.ItensDadosCotacaoDAO;
import br.com.buritech.listaki.model.dao.impl.ListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.dao.impl.ProdutoDAO;
import br.com.buritech.listaki.model.dao.impl.ProdutoEstabelecimentoDAO;
import br.com.buritech.listaki.model.entidade.DadosCotacaoListaUsuario;
import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.ItensDadoCotacao;
import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.Usuario;

public class ProdutoBOImpl implements ProdutoBO {
	
	private static final String TAG = ProdutoBOImpl.class.getSimpleName(); 

	public ProdutoBOImpl() {
		super();
	}
	
	@Override
	public void incluirListaProduto(Usuario usuario, ListaProdutosUsuario listaProdutosUsuario) {
		daoListaProdutosUsuario().createOrUpdate(listaProdutosUsuario);
	}
	
	@Override
	public void incluirCodigoBarrasListaProdutos(Usuario usuario, ItemListaProdutosUsuario itemLista) {
		
		// incluir logica de negocio se houver
		final String codigoBarras = itemLista.getCodigoBarras();
		
		if (codigoBarras != null && codigoBarras.trim().length() > 0 &&
				codigoBarras.trim().length() < 15) {
			
			//TODO: @adri melhorar logica
			List<ListaProdutosUsuario> listaProdUsuario = daoListaProdutosUsuario().
					findForAttribute("id", itemLista.getListaUsuario().getId());
			itemLista.setListaUsuario(listaProdUsuario.get(0));
			daoItemListaProdutosUsuario().createOrUpdate(itemLista);
		}
	}

	@Override
	public void enviarListaProdutosWS(ListaProdutosUsuario dadosPesquisa) {
		// TODO: incluir logica de negocio
	}

    @Override
    public ListaProdutosUsuario getlistaProdutosUsuarioPorId(Usuario usuario, Long idLista) {
        //TODO:@adri deveria carregar direto lazy
        ListaProdutosUsuario listaProdutosUsuarios = daoListaProdutosUsuario().buscarListaDeProdutosPorId(idLista);
        List<ItemListaProdutosUsuario> listaItens = daoItemListaProdutosUsuario().findForAttribute("listaUsuario_id", listaProdutosUsuarios);
        listaProdutosUsuarios.setListaItens(listaItens);
        return listaProdutosUsuarios;
    }

	@Override
	public ListaProdutosUsuario getlistaProdutosUsuario(Usuario usuario, String nomeLista) {
		//TODO:@adri deveria carregar direto lazy
		ListaProdutosUsuario listaProdutosUsuarios = daoListaProdutosUsuario().buscarListaDeProdutosPorNome(nomeLista);
		List<ItemListaProdutosUsuario> listaItens = daoItemListaProdutosUsuario().findForAttribute("listaUsuario_id", listaProdutosUsuarios);
		listaProdutosUsuarios.setListaItens(listaItens);
		return listaProdutosUsuarios;
	}
	
	@Override
	public void incluirDadosCotacao(Usuario usuario, DadosCotacaoListaUsuario dadosCotacao) {
		//TODO@rever isso
		daoDadosCotacao().createOrUpdate(dadosCotacao);
		
		for (ItensDadoCotacao itens : dadosCotacao.getItensCotacao()) {
			daoEstabelecimento().createOrUpdate(itens.getProdutoEstabelecimento().getEstabelecimento());
			daoProduto().createOrUpdate(itens.getProdutoEstabelecimento().getProduto());
			daoProdutoEstabelecimento().createOrUpdate(itens.getProdutoEstabelecimento());
		}
	}

	@Override
	public ProdutoDAO daoProduto() {
		return ProdutoDAO.getInstance();
	}

	@Override
	public ItemListaProdutosUsuarioDAO daoItemListaProdutosUsuario() {
		return ItemListaProdutosUsuarioDAO.getInstance();
	}

	@Override
	public ListaProdutosUsuarioDAO daoListaProdutosUsuario() {
		return ListaProdutosUsuarioDAO.getInstance();
	}

	@Override
	public DadosCotacaoDAO daoDadosCotacao() {
		return DadosCotacaoDAO.getInstance();
	}

	@Override
	public ItensDadosCotacaoDAO daoItensDadosCotacao() {
		return ItensDadosCotacaoDAO.getInstance();
	}

	@Override
	public EstabelecimentoDAO daoEstabelecimento() {
		return EstabelecimentoDAO.getInstance();
	}

	@Override
	public ProdutoEstabelecimentoDAO daoProdutoEstabelecimento() {
		return ProdutoEstabelecimentoDAO.getInstance();
	}
}
