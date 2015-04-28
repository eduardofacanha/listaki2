package br.com.buritech.listaki.model.entidade;

import java.io.Serializable;
import java.util.Collection;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ItensDadoCotacao implements IEntidade {

	private static final long serialVersionUID = -3046788739195566331L;

	@DatabaseField(generatedId = true)
	private Long id;

	@DatabaseField(foreign = true)
	private DadosCotacaoListaUsuario dadosCotacaoListaUsuario;

	@DatabaseField(foreign = true)
	private ProdutoEstabelecimento produtoEstabelecimento;

	public ItensDadoCotacao() {

	}

	public ItensDadoCotacao(Long id,
			DadosCotacaoListaUsuario dadosCotacaoListaUsuario,
			ProdutoEstabelecimento produtoEstabelecimento) {
		super();
		this.id = id;
		this.dadosCotacaoListaUsuario = dadosCotacaoListaUsuario;
		this.produtoEstabelecimento = produtoEstabelecimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DadosCotacaoListaUsuario getDadosCotacaoListaUsuario() {
		return dadosCotacaoListaUsuario;
	}

	public void setDadosCotacaoListaUsuario(
			DadosCotacaoListaUsuario dadosCotacaoListaUsuario) {
		this.dadosCotacaoListaUsuario = dadosCotacaoListaUsuario;
	}

	public ProdutoEstabelecimento getProdutoEstabelecimento() {
		return produtoEstabelecimento;
	}

	public void setProdutoEstabelecimento(
			ProdutoEstabelecimento produtoEstabelecimento) {
		this.produtoEstabelecimento = produtoEstabelecimento;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static String toJsonArray(Collection<ItensDadoCotacao> lista) {
		Gson gson = new Gson();
		return gson.toJson(lista);
	}
}
