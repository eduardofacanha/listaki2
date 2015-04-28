package br.com.buritech.listaki.model.entidade;

import android.util.Log;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class DadosCotacaoListaUsuario implements IEntidade {

    private static final long serialVersionUID = -91582675374978954L;

    @DatabaseField(generatedId=true)
    private Long id;

    @DatabaseField
    private Date dataCotacao;

    @DatabaseField
    private Integer qtdeProdutosDesejados;

    @DatabaseField
    private Integer qtdeProdutosDisponiveis;

    @DatabaseField
    private Integer qtdeProdutosNaoDisponiveis;

    @DatabaseField
    private Float valorLista;

    private List<ItensDadoCotacao> itensCotacao;

    public DadosCotacaoListaUsuario() {

    }

    public DadosCotacaoListaUsuario(Long id, Date dataCotacao,
                                    Integer qtdeProdutosDesejados, Integer qtdeProdutosDisponiveis,
                                    Integer qtdeProdutosNaoDisponiveis, Float valorLista,
                                    List<ItensDadoCotacao> itensDadoCotacao) {
        super();
        this.id = id;
        this.dataCotacao = dataCotacao;
        this.qtdeProdutosDesejados = qtdeProdutosDesejados;
        this.qtdeProdutosDisponiveis = qtdeProdutosDisponiveis;
        this.qtdeProdutosNaoDisponiveis = qtdeProdutosNaoDisponiveis;
        this.valorLista = valorLista;
        this.itensCotacao = itensDadoCotacao;
    }

    public Integer getQtdeProdutosDesejados() {
        return qtdeProdutosDesejados;
    }

    public void setQtdeProdutosDesejados(Integer qtdeProdutosDesejados) {
        this.qtdeProdutosDesejados = qtdeProdutosDesejados;
    }

    public Integer getQtdeProdutosDisponiveis() {
        return qtdeProdutosDisponiveis;
    }

    public void setQtdeProdutosDisponiveis(Integer qtdeProdutosDisponiveis) {
        this.qtdeProdutosDisponiveis = qtdeProdutosDisponiveis;
    }

    public Integer getQtdeProdutosNaoDisponiveis() {
        return qtdeProdutosNaoDisponiveis;
    }

    public void setQtdeProdutosNaoDisponiveis(Integer qtdeProdutosNaoDisponiveis) {
        this.qtdeProdutosNaoDisponiveis = qtdeProdutosNaoDisponiveis;
    }

    public Float getValorLista() {
        return valorLista;
    }

    public void setValorLista(Float valorLista) {
        this.valorLista = valorLista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataCotacao() {
        return dataCotacao;
    }

    public void setDataCotacao(Date dataCotacao) {
        this.dataCotacao = dataCotacao;
    }

    public List<ItensDadoCotacao> getItensCotacao() {
        return itensCotacao;
    }

    public void setItensCotacao(List<ItensDadoCotacao> itensCotacao) {
        this.itensCotacao = itensCotacao;
    }

    public static DadosCotacaoListaUsuario fromJsonToObject(String json) {

        Gson gson = new GsonBuilder().
                registerTypeAdapter(DadosCotacaoListaUsuario.class, new ItensCotacaoDeserializer()).
                registerTypeAdapter(Date.class, new DateTypeAdapter()).create();

        DadosCotacaoListaUsuario obj = gson.fromJson(json, DadosCotacaoListaUsuario.class);

        return obj;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static String toJsonArray(Collection<DadosCotacaoListaUsuario> lista) {
        Gson gson = new Gson();
        return gson.toJson(lista);
    }
}

class ItensCotacaoDeserializer implements JsonDeserializer<DadosCotacaoListaUsuario> {

    public DadosCotacaoListaUsuario deserialize(JsonElement json, Type typeOfT,
                                                JsonDeserializationContext ctx) {

        JsonElement element = json.getAsJsonObject().get("dadosCotacaoListaUsuario");
		JsonElement elementField = element.getAsJsonObject().get("itensCotacao");
//        JsonElement elementField = element.getAsJsonObject().get("value");

        List<ItensDadoCotacao> elementList = new ArrayList<ItensDadoCotacao>();

        if (elementField.isJsonArray()) {

            for (JsonElement item : elementField.getAsJsonArray()) {
                elementList.add((ItensDadoCotacao) ctx.deserialize(
                        item, ItensDadoCotacao.class));
            }

        } else if (elementField.isJsonObject()) {
            elementList.add((ItensDadoCotacao) ctx.deserialize(
                    elementField, ItensDadoCotacao.class));
        }

        //TODO @adri avaliar depois esse parse
        DadosCotacaoListaUsuario obj = new DadosCotacaoListaUsuario();
        obj.setQtdeProdutosDesejados(new Integer(element.getAsJsonObject().get("qtdeProdutosDesejados").toString()));
        obj.setQtdeProdutosDisponiveis(new Integer(element.getAsJsonObject().get("qtdeProdutosDisponiveis").toString()));
        obj.setQtdeProdutosNaoDisponiveis(new Integer(element.getAsJsonObject().get("qtdeProdutosNaoDisponiveis").toString()));
        obj.setValorLista(new Float(element.getAsJsonObject().get("valorLista").toString()));
        obj.setItensCotacao(elementList);

        return obj;
    }
}


class DateTypeAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {

    @Override
    public JsonElement serialize(Date date, Type type,
                                 JsonSerializationContext context) {
        return new JsonPrimitive(date.getTime());
    }

    @Override
    public Date deserialize(JsonElement element, Type type,
                            JsonDeserializationContext context) throws JsonParseException {
        Date date = new Date(element.getAsLong());
        return date;
    }
}