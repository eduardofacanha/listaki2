package br.com.buritech.listaki.model.entidade;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Representa a lista de produtos cadastradas por um determinado usuario.
 *
 * @author adrianaizel
 *
 */
@DatabaseTable(tableName = "ListaProdutosUsuario")
public class ListaProdutosUsuario implements IEntidade {

	private static final long serialVersionUID = -1180211679248479202L;

	@DatabaseField(generatedId=true)
	private Long id;

	@DatabaseField
	private Long idWeb;

	@DatabaseField(canBeNull = false)
	private String nomeLista;

	@DatabaseField(canBeNull = false)
	private Date dataCriacaoLista;

	@DatabaseField
	private Date dataEnvioListaWS;

	@DatabaseField(canBeNull = false, foreign = true)
	private Usuario usuario;

    private int qtdeProdutosCarrinho;

    // TODO: avaliar depois esse relacionamento
	// @ForeignCollectionField(eager = false)
	private List<ItemListaProdutosUsuario> listaItens;
	private List<ListaProdutosUsuario> listanome;


	public ListaProdutosUsuario() {

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

	public String getNomeLista() {
		return nomeLista;
	}

	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}

	public Date getDataCriacaoLista() {
		return dataCriacaoLista;
	}

	public void setDataCriacaoLista(Date dataCriacaoLista) {
		this.dataCriacaoLista = dataCriacaoLista;
	}

	public Date getDataEnvioListaWS() {
		return dataEnvioListaWS;
	}

	public void setDataEnvioListaWS(Date dataEnvioListaWS) {
		this.dataEnvioListaWS = dataEnvioListaWS;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemListaProdutosUsuario> getListaItens() {
		return listaItens;
	}

	public List<ListaProdutosUsuario> getListaNome() {
		return listanome;
	}

	public void setListaItens(List<ItemListaProdutosUsuario> listaItens) {
		this.listaItens = listaItens;
	}

    public int getQtdeProdutosCarrinho() {
        return qtdeProdutosCarrinho;
    }

    public void setQtdeProdutosCarrinho(int qtdeProdutosCarrinho) {
        this.qtdeProdutosCarrinho = qtdeProdutosCarrinho;
    }

	public static ListaProdutosUsuario fromJsonToObject(String json) {

		Gson gson = new GsonBuilder().registerTypeAdapter(
				ListaProdutosUsuario.class, new ListaProdutosItensDeserializer()).create();

//		Gson gson = new GsonBuilder().registerTypeAdapter(
//				ArrayList.class, new CollectionSerializer<ItemListaProdutosUsuario>()).create();

		ListaProdutosUsuario obj = gson.fromJson(json, ListaProdutosUsuario.class);

		return obj;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static String toJsonArray(Collection<ListaProdutosUsuario> lista) {
		Gson gson = new Gson();
		return gson.toJson(lista);
	}

//	public String toString() {
//		return "id<" +id + ">"+
//				"nomeLista<"+nomeLista + ">"+
//				"listaItens<"+(listaItens !=null ? listaItens.size() : "null")+">";
//				
//	}

	public String toString() {
		return getNomeLista();

	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListaProdutosUsuario that = (ListaProdutosUsuario) o;

        if (!nomeLista.equals(that.nomeLista)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nomeLista.hashCode();
    }
}

class ListaProdutosItensDeserializer implements JsonDeserializer<ListaProdutosUsuario> {

	public ListaProdutosUsuario deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext ctx) {

		JsonElement element = json.getAsJsonObject().get("listaProdutosUsuario");
		JsonElement elementField = element.getAsJsonObject().get("listaItens");

		List<ItemListaProdutosUsuario> elementList = new ArrayList<ItemListaProdutosUsuario>();

		if (elementField.isJsonArray()) {

			for (JsonElement item : elementField.getAsJsonArray()) {
				elementList.add((ItemListaProdutosUsuario) ctx.deserialize(
						item, ItemListaProdutosUsuario.class));
			}

		} else if (elementField.isJsonObject()) {
			elementList.add((ItemListaProdutosUsuario) ctx.deserialize(
					elementField, ItemListaProdutosUsuario.class));
		}

		//TODO @adri avaliar depois esse parse
		ListaProdutosUsuario obj = new ListaProdutosUsuario();
		obj.setNomeLista(element.getAsJsonObject().get("nomeLista").toString());
		obj.setListaItens(elementList);

		return obj;
	}


}
/*
class CollectionSerializer<E> implements JsonSerializer<Collection<E>>, JsonDeserializer<Collection<E>>{
 
    public JsonElement serialize(Collection<E> collection, Type type,
            JsonSerializationContext context) {
        JsonArray result = new JsonArray();
        for(E item : collection){
            result.add(context.serialize(item));
        }
        return new JsonPrimitive(result.toString());
    }
 
     
    @SuppressWarnings("unchecked")
    public Collection<E> deserialize(JsonElement element, Type type,
            JsonDeserializationContext context) throws JsonParseException {
        JsonArray items = (JsonArray) new JsonParser().parse(element.getAsString());
        ParameterizedType deserializationCollection = ((ParameterizedType) type);
        Type collectionItemType = deserializationCollection.getActualTypeArguments()[0];
        Collection<E> list = null;
         
        try {
            list = (Collection<E>)((Class<?>) deserializationCollection.getRawType()).newInstance();
            for(JsonElement e : items){
                list.add((E)context.deserialize(e, collectionItemType));
            }
        } catch (InstantiationException e) {
            throw new JsonParseException(e);
        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);
        }
         
        return list;
    }
}*/