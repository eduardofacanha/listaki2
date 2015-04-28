package br.com.buritech.listaki.model.integracao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;

/**
 * Classe para gerenciar a conexao com os servicos de integracao a aplicacao WEB
 * @author adrianaizel
 *
 */
public class ServicoIntegracaoWebThread extends Thread {

    private static final String TAG = ServicoIntegracaoWebThread.class.getSimpleName();
    private Context contexto;
    private int code = 0;
    private OnResponseReceivedListener listener;
    private boolean isRun;

    private String url;
    private String json;
    ListaProdutosUsuario params = new ListaProdutosUsuario();

    /**
     * constantes que representam os servicos
     */
    public static final short SERVICO_LISTAR_PRODUTOS_USUARIO = 1;
    public static final short SERVICO_PESQUISAR_PRODUTOS_USUARIO = 2;

    /**
     * constantes das urls dos servicos e servidor
     */
//	private static final String IP_SERVER = "http://localhost:8080/ListAkiWeb/";
//	private static final String URL_LISTAR_PRODUTOS_USUARIO = IP_SERVER + "servico/pesquisar";
//	private static final String URL_PESQUISAR_PRODUTOS_USUARIO = IP_SERVER + "servico/pesquisar";


    private static final String IP_SERVER = "http://192.168.1.110:8080/JerseyServidor/json/dadosPost";

    private static final String URL_LISTAR_PRODUTOS_USUARIO = IP_SERVER;
    private static final String URL_PESQUISAR_PRODUTOS_USUARIO = IP_SERVER;

    public ServicoIntegracaoWebThread(Context contexto, final short metodoServico) {
        this.contexto = contexto;
        this.url = (metodoServico == SERVICO_LISTAR_PRODUTOS_USUARIO ? URL_LISTAR_PRODUTOS_USUARIO
                : (metodoServico == SERVICO_PESQUISAR_PRODUTOS_USUARIO ? URL_PESQUISAR_PRODUTOS_USUARIO :null));
    }

    public ServicoIntegracaoWebThread(Context contexto, final String urlWithParam) {
        this.contexto = contexto;
        this.url = urlWithParam;
    }

    public ServicoIntegracaoWebThread(Context contexto, final String urlWithParam, ListaProdutosUsuario params) {
        this.contexto = contexto;
        this.url = urlWithParam;
        this.params = params;
    }

    @Override
    public void run() {

        setIsRun(true);

        Log.d(TAG, "*** start thread para url <"+url+">");

        InputStream is = null;

        // Conexao HTTP request
        try {

            HttpParams httpParameters = new BasicHttpParams();// defaultHttpClient
            int timeoutConnection = 30000; //30s para timeout

            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

//            HttpGet httpGet = new HttpGet(url);
            HttpPost httpGet = new HttpPost(url);


            String json = new GsonBuilder().create().toJson(params, ListaProdutosUsuario.class);


            StringEntity se = new StringEntity(json);
            se.setContentType("application/json;charset=UTF-8");

            httpGet.setEntity(se);


            HttpResponse httpResponse = httpClient.execute(httpGet);
            code = httpResponse.getStatusLine().getStatusCode();

            HttpEntity httpEntity = httpResponse.getEntity();

            is = httpEntity.getContent();

            //recuperando dados de retorno do acesso ao servico

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

//                    is, "iso-8859-1"));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // lendo cada linha do stream retornado
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            is.close();
            this.json = sb.toString();

            Log.d(TAG, "*** Dados recebidos do servico <"+this.json+"> codigo de retorno <"+code+">");

            if (code == 200) {// conexao com servico realizada com sucesso
                listener.onResponseReceived(this.json);

            } else {// conexao com servico realizada com erro
                //TODO: tratar codigo de erro
            }

            setIsRun(false);

        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncodingException Error", "Error converting result " + e.toString(), e);

        } catch (ClientProtocolException e) {
            Log.e(TAG, "ClientProtocolException", e);

        } catch (UnknownHostException e) {
            Log.e(TAG, "UnknownHostException Error", e);

        } catch (IOException e) {
            Log.e(TAG, "IOException Error", e);

        } catch (Exception e) {
            Log.e(TAG, "Exception Error", e);
        } finally {//TODO: @adri retirar, apenas para teste
//			listener.onResponseReceived(this.json);
        }
    }

    private synchronized void setIsRun(boolean isRun) {
        this.isRun = isRun;
    }

    public synchronized boolean isRun() {
        return this.isRun;
    }

    public void setOnResponseReceivedListener(
            OnResponseReceivedListener listener) {
        this.listener = listener;
    }

    public interface OnResponseReceivedListener {
        public void onResponseReceived(String json);
    }
}