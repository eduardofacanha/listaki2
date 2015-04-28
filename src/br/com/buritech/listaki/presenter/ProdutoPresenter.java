package br.com.buritech.listaki.presenter;

import roboguice.RoboGuice;
import android.util.Log;
import br.com.buritech.listaki.core.presenter.AbstractBasePresenter;
import br.com.buritech.listaki.core.view.activity.BaseActivity;
import br.com.buritech.listaki.model.bo.ProdutoBO;
import br.com.buritech.listaki.model.entidade.DadosCotacaoListaUsuario;
import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;
import br.com.buritech.listaki.view.activity.ListaProdutosCarrinhoActivity;

import com.google.inject.Inject;

public class ProdutoPresenter extends AbstractBasePresenter {
	
	private static final String TAG = null;
	@Inject
	private ProdutoBO produtoBO;
	private BaseActivity view;
	
	public ProdutoPresenter(BaseActivity view) {
			this.view = view;
			RoboGuice.getInjector(view).injectMembers(this);
	}
	
	public void criarListaProdutoUsuario(ListaProdutosUsuario listaProdutosUsuario) {
		Log.i(TAG, "Criar Lista Produto Usuario - Produto presenter");
		produtoBO.incluirListaProduto(super.getUsuarioSessaoListAki(), listaProdutosUsuario);
	}

	public void incluirItemListaProdutosUsuario(ItemListaProdutosUsuario itemLista) {
		
		produtoBO.incluirCodigoBarrasListaProdutos(super.getUsuarioSessaoListAki(), itemLista);
		
		((ListaProdutosCarrinhoActivity)view).atualizarListaItensProdutos(produtoBO.getlistaProdutosUsuarioPorId(
				super.getUsuarioSessaoListAki(), itemLista.getListaUsuario().getId()));
	}

	public ListaProdutosUsuario listarProdutosUsuario(String nomeLista) {
		return produtoBO.getlistaProdutosUsuario(super.getUsuarioSessaoListAki(), nomeLista);
	}

    public void buscarListaProdutosPorId(Long idLista) {
        ((ListaProdutosCarrinhoActivity)view).atualizarListaItensProdutos(produtoBO.getlistaProdutosUsuarioPorId(
                super.getUsuarioSessaoListAki(), idLista));
    }

	public void buscarListaProdutosPorNome(String nomeLista) {//TODO @adri: alterar logica para pegar por nome
		((ListaProdutosCarrinhoActivity)view).atualizarListaItensProdutos(produtoBO.getlistaProdutosUsuario(
                super.getUsuarioSessaoListAki(), nomeLista));
	}
	
	public void salvarDadosCotacao(DadosCotacaoListaUsuario dadosCotacao) {
		produtoBO.incluirDadosCotacao(super.getUsuarioSessaoListAki(), dadosCotacao);
	}
}
