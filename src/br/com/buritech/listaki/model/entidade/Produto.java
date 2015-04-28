package br.com.buritech.listaki.model.entidade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*
 *Create: Bruno Ábia
 *Obs.: Esses atributos são temporarios, podem ser mudados 
 *de acordo com o projeto. 
 */
@DatabaseTable(tableName = "Produto")
public class Produto implements IEntidade {

	private static final long serialVersionUID = -1137483520732111209L;

	@DatabaseField(generatedId=true)
	private Long id;
	
	@DatabaseField
	private Long idWeb;
	
	@DatabaseField(canBeNull = false)
	private String codigoBarras;

	@DatabaseField
	private String nome;

	@DatabaseField
	private String foto;

	public Produto() {
		
	}
	
	public Produto(Long id, String nome, String codigoBarras) {
		super();
		this.id = id;
		this.codigoBarras = codigoBarras;
		this.nome = nome;
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

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public static Produto fromJsonToObject(String json) {
		Gson gson = new Gson();
		Produto produto = gson.fromJson(json, Produto.class);
		return produto;
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
    }
	
	public static String toJsonArray(Collection<Produto> listOSs) {
		Gson gson = new Gson();
		return gson.toJson(listOSs);
	}
	
	public static List<Produto> fromJsonToObjectArray(String json) {
		
		Gson gson = new GsonBuilder().create();
		
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();
		
		List<Produto> listProdutos = new ArrayList<Produto>();
		
		for (int i = 0; i < jsonArray.size(); i++) {
			listProdutos.add(gson.fromJson(jsonArray.get(i), Produto.class));
		}
		
		return listProdutos;
	}
}
