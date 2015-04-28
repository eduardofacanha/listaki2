package br.com.buritech.listaki.model.entidade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Representa os itens (de produtos) que estao contidos na lista de produtos do
 * usuario.
 * 
 * @author adrianaizel
 * 
 */
@DatabaseTable(tableName = "ItemListaProdUsuario")
public class ItemListaProdutosUsuario implements IEntidade {

	private static final long serialVersionUID = -4636592638796848123L;

	@DatabaseField(generatedId = true)
	private Long id;

	@DatabaseField
	private Long idWeb;

	@DatabaseField
	private String descricao;

	@DatabaseField
	private String codigoBarras;

	@DatabaseField
	private Integer qtdeDesejada;

	@DatabaseField(foreign = true)
	private Produto produto;// TODO @adri avaliar se precisa desse atributo
							// mesmo

	@DatabaseField(canBeNull = false, foreign = true)
	private ListaProdutosUsuario listaUsuario;

	public ItemListaProdutosUsuario() {

	}

	public ItemListaProdutosUsuario(Long id, String descricao, String codigoBarras, Integer qtdeDesejada,
			Produto produto, ListaProdutosUsuario listaUsuario) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.codigoBarras = codigoBarras;
		this.qtdeDesejada = qtdeDesejada;
		this.produto = produto;
		this.listaUsuario = listaUsuario;
	}

	public String toString() {
		return getCodigoBarras();

	}

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Integer getQtdeDesejada() {
		return qtdeDesejada;
	}

	public void setQtdeDesejada(Integer qtdeDesejada) {
		this.qtdeDesejada = qtdeDesejada;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ListaProdutosUsuario getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(ListaProdutosUsuario listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public static ItemListaProdutosUsuario fromJsonToObject(String json) {
		Gson gson = new Gson();
		ItemListaProdutosUsuario obj = gson.fromJson(json, ItemListaProdutosUsuario.class);
		return obj;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static String toJsonArray(Collection<ItemListaProdutosUsuario> listaItens) {
		Gson gson = new Gson();
		return gson.toJson(listaItens);
	}

	public static List<ItemListaProdutosUsuario> fromJsonToObjectArray(String json) {
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();

		List<ItemListaProdutosUsuario> listaItens = new ArrayList<ItemListaProdutosUsuario>();

		for (int i = 0; i < jsonArray.size(); i++) {
			listaItens.add(gson.fromJson(jsonArray.get(i), ItemListaProdutosUsuario.class));
		}

		return listaItens;
		/*
		 * } catch (Throwable e) { Log.e("Error", e.getMessage(), e); return
		 * null; }
		 */
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemListaProdutosUsuario)) return false;

        ItemListaProdutosUsuario that = (ItemListaProdutosUsuario) o;

        if (!codigoBarras.equals(that.codigoBarras)) return false;

        return true;
    }

}