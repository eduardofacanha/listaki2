package br.com.buritech.listaki.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.buritech.listaki.core.view.activity.BaseActivity;
import br.com.buritech.listaki.model.dao.impl.ItemListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;
import br.com.buritech.listaki.model.integracao.ServicoIntegracaoWebThread;
import br.com.buritech.listaki.model.integracao.ServicoIntegracaoWebThread.OnResponseReceivedListener;
import br.com.buritech.listaki.presenter.ProdutoPresenter;
import br.com.buritech.listaki.view.adapter.ListaProdutosCarrinhoAdapter;
import br.com.buritech.listaki.view.util.Constantes;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.lista_produtos_carrinho)
public class ListaProdutosCarrinhoActivity extends BaseActivity {
    private static final String TAG = ListaProdutosCarrinhoActivity.class.getSimpleName();

    // baixar no playstore
    private static final String ZXING_MARKET = "market://search?q=pname:com.google.zxing.client.android";
    // baixar no site offical
    private static final String ZXING_DIRECT = "https://zxing.googlecode.com/files/BarcodeScanner4.5.apk";

    @InjectView(R.id.lista_prod_usu_bt_buscar_cotacao)
    private Button buttonBuscarCotacao;

    //Botão de Scanner
    @InjectView(R.id.scan)
    private Button buttonScan;

    @InjectView(R.id.lista_prod_usu_list_view_produtos)
    private ListView listViewProdutos;

    @InjectView(R.id.lista_carrinho_nome)
    private TextView txtNomeCarrinho;

    private ProdutoPresenter presenterProduto;
    private ListaProdutosUsuario listaProdutosUsuario;
    private ListaProdutosCarrinhoAdapter adapterListaProdutosUsuario;
    private ItemListaProdutosUsuario produtoSelecionado;

    //Esse atributo evita o conflito da ação do botão scan com a app em landscape, ambos abrem o Scannner (antes, acontecia de o Scannner abrir 2x)
    boolean scannerAberto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//            //Se a app estiver em landscape, o Scanner é chamado
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            chamaLeitor();
//        }

        presenterProduto = new ProdutoPresenter(this);
        //Verifica se o usuário quer criar um novo carrinho ou se quer visualizar algum já existente, preenchendo os itens do carrinho de acordo com a sua escolha
        if ((ListaProdutosUsuario) getIntent().getSerializableExtra("novoCarrinho") != null) {
            listaProdutosUsuario = (ListaProdutosUsuario) getIntent().getSerializableExtra("novoCarrinho");
            presenterProduto.criarListaProdutoUsuario(listaProdutosUsuario);//TODO:
        } else {
            listaProdutosUsuario = (ListaProdutosUsuario) getIntent().getSerializableExtra("carrinhoSelecionado");
        }
        presenterProduto.buscarListaProdutosPorId(listaProdutosUsuario.getId());
        txtNomeCarrinho.setText(listaProdutosUsuario.getNomeLista());

        buttonScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaLeitor();
            }
        });

        buttonBuscarCotacao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO: @adri 1- chamar WS para passar a lista
//                final String paramJson = "teste_param_json_url";// "http://localhost:8080/ListAkiWeb/servico/listar"
                // +
                // listaProdutosUsuario.toJson();

                final String paramJson = "http://192.168.1.110:8080/JerseyServidor/json/dadosPost"; //+ listaProdutosUsuario.toJson();

                ServicoIntegracaoWebThread servico = new ServicoIntegracaoWebThread(
                        ListaProdutosCarrinhoActivity.this, paramJson,listaProdutosUsuario);

                servico.setOnResponseReceivedListener(new OnResponseReceivedListener() {

                    @Override
                    public void onResponseReceived(String json) {
                        Intent intent = new Intent(ListaProdutosCarrinhoActivity.this, ListaMercadoActivity.class);
                        // TODO:@adri retirar, apenas p teste
                        Log.i("TESTE", json);
                        //json = "teste_param_json_value";

                        intent.putExtra(Constantes.PARAM_JSON, json);
                        startActivity(intent);
                    }
                });

                servico.start();
            }
        });

        registerForContextMenu(listViewProdutos);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        //Se a app estiver em landscape, o Scanner é chamado
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            chamaLeitor();
//        }
//    }

    //Pega a resposta do Scanner
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i(TAG, "*********  onActivityResult()");

        //Evita o conflito da ação do botão scan com a app em landscape, ambos abrem o Scannner (antes, acontecia de o Scannner abrir 2x)
        scannerAberto = false;

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String qrcode = intent.getStringExtra("SCAN_RESULT");

            incluirCodigoBarras(qrcode);
            Toast toast = Toast.makeText(getApplicationContext(), "Código de barras lido com sucesso!", Toast.LENGTH_SHORT);
            toast.show();

            chamaLeitor();
        } else if (resultCode == RESULT_CANCELED) {
            //Quando o usuário clica em "voltar", não acontece nada (volta automaticamente para a tela anterior)
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Leitura sem sucesso. Tente novamente.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_view_menu, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Definicao do objeto Inflater
        //MenuInflater inflater = this.getMenuInflater();

        // Inflar um XML em um Menu vazio
        //inflater.inflate(R.menu.menu_incluir_produtos_lista_usuario, menu);

        // Exibir o menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        // Verifica o item do menu selecionado
        switch (item.getItemId()) {
            // Verifica se foi selecionado o item NOVO
            case R.id.scan:
                chamaLeitor();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ItemListaProdutosUsuarioDAO itemProdutoDAO = ItemListaProdutosUsuarioDAO.getInstance();


    /** This will be invoked when a menu item is selected */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId()) {
            case R.id.list_prod_menu_edit:

                produtoSelecionado=(ItemListaProdutosUsuario) listViewProdutos.getAdapter().getItem(info.position);

                final Dialog dialog = new Dialog(this);



                dialog.setContentView(R.layout.dialogo_custom_number);

                // define o título do Dialog
                dialog.setTitle("Editar Quantidade");

                // instancia os objetos que estão no layout minhas_lista_usuario.xml
                final Button confirmar = (Button) dialog.findViewById(R.id.btn_Confirmar);
                final EditText editText = (EditText) dialog.findViewById(R.id.etValor);

                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Abre o teclado quando o dialog é chamado.
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                    }
                });

//                editText.setText(produtoSelecionado.getQtdeDesejada());
//
//                editText.selectAll();

                confirmar.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        produtoSelecionado.setQtdeDesejada(Integer.parseInt(editText.getText().toString()));
                        itemProdutoDAO.createOrUpdate(produtoSelecionado);
                        atualizarListaItensProdutos(listaProdutosUsuario);
                    }
                });

                dialog.show();


                break;
            case R.id.list_prod_menu_delete:
                produtoSelecionado = (ItemListaProdutosUsuario) listViewProdutos.getAdapter().getItem(info.position);
                itemProdutoDAO.delete(produtoSelecionado);

                presenterProduto.buscarListaProdutosPorNome(listaProdutosUsuario.getNomeLista());

//                Toast.makeText(this, "Delete : " + listViewProdutos.getAdapter().getItem(info.position), Toast.LENGTH_SHORT)
//                        .show();

                break;
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //Se a app estiver em landscape, o Scanner é chamado
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            chamaLeitor();
        }
    }

    private void chamaLeitor() {
        if (!scannerAberto) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");

            //Evita o conflito da ação do botão scan com a app em landscape, ambos abrem o Scannner (antes, acontecia de o Scannner abrir 2x)
            scannerAberto = true;

            try {
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException e) {
                mostrarMensagem();
            }
        }
    }

    //Pergunta se o usuário deseja instalar o Scanner (se não tiver)
    private void mostrarMensagem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instalar Barcode Scanner?");
        builder.setMessage("Para escanear o código de barras, você precisa instalar o ZXing barcode scanner.");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("Instalar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ZXING_MARKET));

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ZXING_DIRECT));
                    startActivity(intent);
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void incluirCodigoBarras(String codigoBarras) {
        ItemListaProdutosUsuario item = new ItemListaProdutosUsuario();
        item.setCodigoBarras(codigoBarras);

        ItemListaProdutosUsuario itemLista;
        //Se codigo de barras não existe
        if (!listaProdutosUsuario.getListaItens().contains(item)) {
            itemLista = new ItemListaProdutosUsuario();
            itemLista.setCodigoBarras(codigoBarras);
            itemLista.setQtdeDesejada(1);
        }
        //Senão acrescenta a quantidade
        else{
            int index = listaProdutosUsuario.getListaItens().indexOf(item);
            itemLista = listaProdutosUsuario.getListaItens().get(index);
            itemLista.setQtdeDesejada(itemLista.getQtdeDesejada() + 1);
        }
        itemLista.setListaUsuario(listaProdutosUsuario);
        presenterProduto.incluirItemListaProdutosUsuario(itemLista);
    }

    public void atualizarListaItensProdutos(ListaProdutosUsuario listaProdutosUsuario) {
        Log.i(TAG, "atualizarListaItensProdutos");
        this.listaProdutosUsuario = listaProdutosUsuario;
        adapterListaProdutosUsuario = new ListaProdutosCarrinhoAdapter(this, R.layout.lista_produtos_carrinho_row, listaProdutosUsuario.getListaItens());
        this.listViewProdutos.setAdapter(adapterListaProdutosUsuario);
    }

}
