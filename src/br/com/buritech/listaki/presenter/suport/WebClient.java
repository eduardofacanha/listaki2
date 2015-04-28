package br.com.buritech.listaki.presenter.suport;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/*
 * Create: Bruno ??bia
 * Classe Cliente para comunica????o com servidor web
 * 
 */

public class WebClient {

    private final String url;

    public WebClient(String url){
        this.url=url;
    }

    public String post(String json){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Content-type","application/json");
            httpPost.setHeader("Accept","application/json");
            HttpResponse response = httpClient.execute(httpPost);
            String jsonResposta = EntityUtils.toString(response.getEntity());

            return jsonResposta;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
