package br.com.buritech.listaki.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.buritech.listaki.core.view.activity.BaseActivity;
import br.com.buritech.listaki.model.entidade.DadosCotacaoListaUsuario;
import br.com.buritech.listaki.model.entidade.ItensDadoCotacao;
import br.com.buritech.listaki.model.entidade.Produto;
import br.com.buritech.listaki.model.integracao.ServicoIntegracaoWebThread;
import br.com.buritech.listaki.model.integracao.ServicoIntegracaoWebThread.OnResponseReceivedListener;
import br.com.buritech.listaki.presenter.ProdutoPresenter;
import br.com.buritech.listaki.view.adapter.ListaMercadoAdapter;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.lista_mercado)
public class ListaMercadoActivity extends BaseActivity {

	private static final String TAG = ListaMercadoActivity.class
			.getSimpleName();

	@InjectView(R.id.grid_lista_prod_usu)
	private ListView listView;

	private ListaMercadoAdapter gridProdutosUsuario;

	private ProdutoPresenter presenterProduto;

	private DadosCotacaoListaUsuario dadosCotacao;

    @InjectView(R.id.map)
    private Button btmaps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
//		final String paramJson = this.getIntent().getExtras().getString(Constantes.PARAM_JSON);
//
//		Log.i(TAG, "********* onCreate()-->"+paramJson);

		presenterProduto = new ProdutoPresenter(this);

		dadosCotacao = null;// presenterProduto.listarProdutosUsuario("listaA");
//        dadosCotacao = presenterProduto.listarProdutosUsuario("teste");
		Log.d(TAG, "onCreate dadosCotacao-->" + dadosCotacao);

		// TODO: @adri corrigir logica
		if (dadosCotacao != null) {
			handlerSetDadosGridView(false);

		} else {
			handlerCarregarOSJson();
		}

        btmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ListaMercadoActivity.this, MapaMercadoActivity.class);
                startActivity(intent);
            }
        });

	}

	private void handlerCarregarOSJson() {
		Log.d(TAG,
				"handlerCarregarOSJson --> startNewService SERVICO_LISTAR_PRODUTOS_USUARIO");

		ServicoIntegracaoWebThread threadLoad = new ServicoIntegracaoWebThread(
				this, "http://192.168.1.110:8080/JerseyServidor/json/dadosPost");
				threadLoad.setOnResponseReceivedListener(new OnResponseReceivedListener() {

					@Override
					public void onResponseReceived(String json) {

                        Log.d("TESTE", "ENTROU");
                        Log.d(TAG,
								"listaProdutos.onResponseReceived ---> json<"
										+ json + ">");

//						// TODO: retirar isso daqui, somente para teste

                        json = "{\"dadosCotacaoListaUsuario\":" + json + "}";
//						json = "{\"dadosCotacaoListaUsuario\":{\"dataCotacao\":\"2014-10-14T22:53:59.007-04:00\"," +
//								"\"id\":1,\"itensCotacao\":[{\"id\":11,\"produtoEstabelecimento\":{\"estabelecimento\":{" +
//								"\"id\":50,\"razaoSocial\":\"Supermercado Susan\"},\"id\":21,\"preco\":2.5,\"produto\":{" +
//								"\"codigoBarras\":\"07896224600057\",\"id\":1,\"nome\":\"Arroz\"},\"quantidade\":10}},{" +
//								"\"id\":12,\"produtoEstabelecimento\":{\"estabelecimento\":{\"id\":51,\"razaoSocial\":" +
//								"\"Supermercado Juh\"},\"id\":22,\"preco\":3.5,\"produto\":{\"codigoBarras\":" +
//								"\"07896224600057\",\"id\":1,\"nome\":\"Arroz\"},\"quantidade\":100}},{\"id\":13," +
//								"\"produtoEstabelecimento\":{\"estabelecimento\":{\"id\":52,\"razaoSocial\":" +
//								"\"Supermercado Lucas\"},\"id\":23,\"preco\":2.3,\"produto\":{\"codigoBarras\":" +
//								"\"07896224600057\",\"id\":1,\"nome\":\"Arroz\"},\"quantidade\":40}}],\"qtdeProdutosDesejados" +
//								"\":30,\"qtdeProdutosDisponiveis\":25,\"qtdeProdutosNaoDisponiveis\":5,\"valorLista\":300}}";



						if (json != null && json.trim().length() > 0) {
							dadosCotacao = DadosCotacaoListaUsuario.fromJsonToObject(json);

                            Log.d("TESTE_JUH",
                                    "onResponseReceived dadosCotacao=" + dadosCotacao);
							Log.d(TAG,
									"onResponseReceived dadosCotacao=" + dadosCotacao);
                            presenterProduto.salvarDadosCotacao(dadosCotacao);
							
							//dadosCotacao = presenterProduto.
							handlerSetDadosGridView(true);
						}
					}
				});

		threadLoad.start();
	}

	private void handlerSetDadosGridView(boolean isRunInThread) {
		
		Log.i(TAG, "handlerSetDadosGridView dc-->" + dadosCotacao);
		
		final List<ItensDadoCotacao> itensListaProdutos = dadosCotacao.getItensCotacao();
		
		Log.i(TAG, "handlerSetDadosGridView ic-->" + itensListaProdutos.size());

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Log.i(TAG, "onItemClick item grid -->" + position);
				Intent intent = new Intent(ListaMercadoActivity.this, ListaProdutosMercadoActivity.class);
				intent.putExtra("DadosCotacaoUsuario", dadosCotacao);
				startActivity(intent);

			//	openNextActivity((Produto) (((GridView) parent)
				//		.getItemAtPosition(position)));
			}
		});

		gridProdutosUsuario = new ListaMercadoAdapter(this,
				R.layout.lista_mercado_row, itensListaProdutos);

		Log.i(TAG, "handlerSetDadosGridView-->" + itensListaProdutos);

		if (isRunInThread) {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					
//					qtdeTotalProdutos.setText("Total de: 300");// + (itensListaProdutos != null ? itensListaProdutos.size() : 0));
//					nomeLista.setText("Nome Lista: Lista 1");

					Log.i(TAG, "runOnUiThread 1-->");
					listView.setAdapter(gridProdutosUsuario);

					Log.i(TAG, "runOnUiThread 2-->");
					// Util.setHeightAbsListView(gridView, 950);
				}
			});

		} else {
			Log.i(TAG, "runOnUiThread 3-->");
//			qtdeTotalProdutos.setText("Total de: 300");// + (itensListaProdutos != null ? itensListaProdutos.size() : 0));
//			nomeLista.setText("Nome Lista: Teste");
			listView.setAdapter(gridProdutosUsuario);
			Log.i(TAG, "runOnUiThread 4-->");
			// Util.setHeightAbsListView(gridView, 950);
		}
	}

	public void openNextActivity(Produto os) {
//		Log.w(TAG, "id-->"+os.getId() + ", nro-->" + os.getNumero());
//		Intent intent = new Intent(ListaProdutosUsuarioActivity.this,
//                DetalheProdutoActivity.class);
//		intent.putExtra(Produto.Produto_ID_BUNDLE, os.getId().longValue());
//		startActivity(intent);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_view_menu, menu);
	}
}
