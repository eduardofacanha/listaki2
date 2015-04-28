package br.com.buritech.listaki.model.entidade;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Representa a entidade intermediaria do relacionamento N:N entre Produto e
 * Estabelecimento.
 * 
 * @author adrianaizel
 * 
 */
@DatabaseTable(tableName = "ProdutoEstabelecimento")
public class ProdutoEstabelecimento implements IEntidade {

	private static final long serialVersionUID = 1867136453798935792L;

	@DatabaseField(generatedId=true)
	private Long id;

	@DatabaseField
	private Long idWeb;

	@DatabaseField(foreign = true, columnName = "prod_id")
	private Produto produto;

	@DatabaseField(foreign = true, columnName = "estab_id")
	private Estabelecimento estabelecimento;
	
	@DatabaseField
	private Float preco;
	
	@DatabaseField
	private Integer quantidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdWeb() {
		return idWeb;
	}

	public void setIdWeb(Long idWeb) {
		this.idWeb = idWeb;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
