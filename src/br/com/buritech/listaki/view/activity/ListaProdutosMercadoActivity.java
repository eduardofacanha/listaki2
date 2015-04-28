package br.com.buritech.listaki.view.activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;
import br.com.buritech.listaki.core.view.activity.BaseActivity;
import br.com.buritech.listaki.model.entidade.DadosCotacaoListaUsuario;
import br.com.buritech.listaki.model.entidade.ItensDadoCotacao;
import br.com.buritech.listaki.view.adapter.ListaProdutosMercadoAdapter;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.lista_produtos_mercado)
public class ListaProdutosMercadoActivity extends BaseActivity {

    private static final String TAG = ListaProdutosMercadoActivity.class
            .getSimpleName();


    @InjectView(R.id.grid_lista_prod_usu)
    private ListView listView;


//    private ProdutoPresenter presenterProduto;

    private ItensDadoCotacao itensDadoCotacao;
    private ListaProdutosMercadoAdapter gridProdutosUsuario;
    private AdapterView adapter;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DadosCotacaoListaUsuario dadosCotacaoUsuario = (DadosCotacaoListaUsuario) getIntent().getSerializableExtra("DadosCotacaoUsuario");


//        gridProdutosUsuario = new ListaProdutosMercadoAdapter(this,
//                R.layout.lista_produtos_mercado_row, dadosCotacaoUsuario);
//
//        Log.i(TAG, "handlerSetDadosGridView-->" + dadosCotacaoUsuario);


        for (int i = 0; i < dadosCotacaoUsuario.getItensCotacao().size(); i++) {

            Log.i("Produtos teste", "" + dadosCotacaoUsuario.getItensCotacao().get(i).getProdutoEstabelecimento().getProduto().toJson());
        }

        //------------------------------------CONSTRUIR A LISTVIEW----------------------------------------------------//

//        Log.i("Supermercado Juh", "" + dadosCotacaoUsuario.getItensCotacao().get(0).getProdutoEstabelecimento().getProduto().toJson());
//        Log.i("Supermercado Juh", "" + dadosCotacaoUsuario.getItensCotacao().get(1).getProdutoEstabelecimento().getProduto().toJson());
//        Log.i("Supermercado Juh", "" + dadosCotacaoUsuario.getItensCotacao().get(2).getProdutoEstabelecimento().getProduto().toJson());
//
//        Log.i("Supermercado Buritech", "" + dadosCotacaoUsuario.getItensCotacao().get(0).getProdutoEstabelecimento().getProduto().toJson());
//        Log.i("Supermercado Buritech", "" + dadosCotacaoUsuario.getItensCotacao().get(3).getProdutoEstabelecimento().getProduto().toJson());



    }
}
//
//    public void openNextActivity(Produto os) {
//        // Log.w(TAG, "id-->"+os.getId() + ", nro-->" + os.getNumero());
//        // Intent intent = new Intent(ListaProdutosUsuarioActivity.this,
//        // DetalheProdutoActivity.class);
//        // intent.putExtra(Produto.Produto_ID_BUNDLE, os.getId().longValue());
//        // startActivity(intent);
//    }

//}