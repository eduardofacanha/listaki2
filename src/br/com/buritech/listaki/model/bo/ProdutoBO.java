package br.com.buritech.listaki.model.bo;

import br.com.buritech.listaki.model.dao.impl.DadosCotacaoDAO;
import br.com.buritech.listaki.model.dao.impl.EstabelecimentoDAO;
import br.com.buritech.listaki.model.dao.impl.ItemListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.dao.impl.ItensDadosCotacaoDAO;
import br.com.buritech.listaki.model.dao.impl.ListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.dao.impl.ProdutoDAO;
import br.com.buritech.listaki.model.dao.impl.ProdutoEstabelecimentoDAO;
import br.com.buritech.listaki.model.entidade.DadosCotacaoListaUsuario;
import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.Usuario;

/**
 * Classe de Negocio, onde deve ficar toda a logica de negocio da aplicacao
 * referente a Entidade Produto.
 * 
 * @author adrianaizel
 * 
 */
public interface ProdutoBO {

	//TDODO: corrigir impelementacao dos getDAO
	public EstabelecimentoDAO daoEstabelecimento();
	public ProdutoDAO daoProduto();
	public ProdutoEstabelecimentoDAO daoProdutoEstabelecimento();
	public ItemListaProdutosUsuarioDAO daoItemListaProdutosUsuario();
	public ListaProdutosUsuarioDAO daoListaProdutosUsuario();
	public DadosCotacaoDAO daoDadosCotacao();
	public ItensDadosCotacaoDAO daoItensDadosCotacao();
	
	public void incluirListaProduto(Usuario usuario, ListaProdutosUsuario listaProdutosUsuario);
	
	public void incluirCodigoBarrasListaProdutos(Usuario usuario,
			ItemListaProdutosUsuario itemLista);

	public void enviarListaProdutosWS(ListaProdutosUsuario dadosPesquisa);

	public ListaProdutosUsuario getlistaProdutosUsuario(Usuario usuario, String nomeLista);

    public ListaProdutosUsuario getlistaProdutosUsuarioPorId(Usuario usuario, Long idLista);

	public void incluirDadosCotacao(Usuario usuario, DadosCotacaoListaUsuario dadosCotacao);
}
