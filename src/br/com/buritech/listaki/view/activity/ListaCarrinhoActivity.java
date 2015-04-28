package br.com.buritech.listaki.view.activity;

import java.util.Date;
import java.util.List;

import br.com.buritech.listaki.model.dao.impl.ItemListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.dao.impl.UsuarioDAO;
import br.com.buritech.listaki.model.entidade.Usuario;
import br.com.buritech.listaki.view.adapter.ListaCarrinhoAdapter;
import br.com.buritech.listaki.view.validation.SaveSharedPreference;
import br.com.buritech.listaki.view.validation.Validation;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import br.com.buritech.listaki.core.view.activity.BaseActivity;
import br.com.buritech.listaki.model.dao.impl.ItemListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.dao.impl.ListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.dao.impl.UsuarioDAO;
import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.Produto;
import br.com.buritech.listaki.model.entidade.Usuario;
import br.com.buritech.listaki.view.adapter.ListaCarrinhoAdapter;
import br.com.buritech.listaki.view.validation.SaveSharedPreference;
import br.com.buritech.listaki.view.validation.Validation;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.lista_carrinho)
public class ListaCarrinhoActivity extends BaseActivity implements SearchView.OnQueryTextListener{

    @InjectView(R.id.lista_prod_usu_bt_criar_lista)
    private Button btnCriarLista;

    @InjectView(R.id.btn_back)
    private Button btnSair;

    //   @InjectView(R.id.buttMaps)
    //   private Button btmaps;

    //@InjectView(R.id.lista_prod_usu_bt_minhas_listas)
    //private Button buttonMinhasListas;

    @InjectView(R.id.lista_carrinho_usu_list_view_produtos)
    private ListView listView;
    private ListaProdutosUsuario carrinhoSelecionado = null;

    private String addCarrinho;

    private Produto produtoSelecionado=null;

    private List<ListaProdutosUsuario> carrinho;

    private ListaCarrinhoAdapter adapter;

    private SearchView mSearchView;

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint(getString(R.string.cheese_hunt_hint));
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        LoginUsuarioActivity.loginUsuarioActivity.finish();

        listView = (ListView) findViewById(R.id.lista_carrinho_usu_list_view_produtos);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        listView.setTextFilterEnabled(true);
        setupSearchView();

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Intent intent = new Intent(ListaCarrinhoActivity.this, ListaProdutosCarrinhoActivity.class);
                carrinhoSelecionado = (ListaProdutosUsuario)adapter.getItemAtPosition(position);
                intent.putExtra("carrinhoSelecionado", carrinhoSelecionado);
                //produtoSelecionado=(Produto) parent.getItemAtPosition(position);
                startActivity(intent);
            }
        });

        btnCriarLista.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                exibir_Caixa_CriarLista();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                carrinhoSelecionado = (ListaProdutosUsuario) adapter.getItemAtPosition(position);
                return false;
            }
        });

        /*btmaps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ListaCarrinhoActivity.this, MapaMercadoActivity.class);
                startActivity(intent);

                GpsTracker gps;



            }
        });*/


        btnSair.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setUserEmail(ListaCarrinhoActivity.this, null);
                Intent intent = new Intent(ListaCarrinhoActivity.this, LoginUsuarioActivity.class);
                startActivity(intent);
                finish();


            }
        });

    }

    private void carregarLista() {
        ListaProdutosUsuarioDAO listaDAO = ListaProdutosUsuarioDAO.getInstance();
        ItemListaProdutosUsuarioDAO itemDAO = ItemListaProdutosUsuarioDAO.getInstance();
        UsuarioDAO userDao = UsuarioDAO.getInstance();
        Usuario usuario = (Usuario) userDao.findForAttribute("email", SaveSharedPreference.getUserEmail(ListaCarrinhoActivity.this).toString() ).get(0);

        carrinho = listaDAO.buscarListaDeProdutosPorUsuario(usuario);
        //Preenche a quantidade de produtos que estão cadastrados para um determinado carrinho
        for (int i = 0; i < carrinho.size(); i++) {
            carrinho.get(i).setQtdeProdutosCarrinho(itemDAO.consultarQtdeProdutosCarrinho(carrinho.get(i)));
        }

        this.adapter = new ListaCarrinhoAdapter(this, R.layout.lista_carrinho_row, carrinho);
        this.listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        this.carregarLista();
    }

    public void exibir_Caixa_CriarLista() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogo_custom);

        // define o título do Dialog
        dialog.setTitle(" Nome da Lista ");

        // instancia os objetos que estão no layout minhas_lista_usuario.xml
        final Button confirmar = (Button) dialog.findViewById(R.id.btn_Confirmar);
        final EditText editText = (EditText) dialog.findViewById(R.id.etValor);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editText.setError(null
                );
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ListaProdutosUsuario novoCarrinho = new ListaProdutosUsuario();
                ListaProdutosUsuarioDAO dao = ListaProdutosUsuarioDAO.getInstance();
//                ProdutoPresenter presenterProduto = new ProdutoPresenter(ListaCarrinhoActivity.this);
                String usuarioEmail = SaveSharedPreference.getUserEmail(ListaCarrinhoActivity.this).toString();
                UsuarioDAO userDao = UsuarioDAO.getInstance();


                if (Validation.hasText(editText)) {

                    novoCarrinho.setNomeLista(editText.getText().toString());

                    if(!carrinho.contains(novoCarrinho)){
                        if (userDao.consultaIgual(usuarioEmail)) {

                            Usuario usuario = new Usuario();
                            usuario = (Usuario) userDao.findForAttribute("email", SaveSharedPreference.getUserEmail(ListaCarrinhoActivity.this).toString()).get(0);

                            addCarrinho = (editText.getText().toString());
                            novoCarrinho.setNomeLista(addCarrinho);
                            novoCarrinho.setDataCriacaoLista(new Date());
                            novoCarrinho.setUsuario(usuario);
//                presenterProduto.criarListaProdutoUsuario(novaLista);


                            if (dao.createOrUpdate(novoCarrinho)) {
                                Intent intent = new Intent(ListaCarrinhoActivity.this, ListaProdutosCarrinhoActivity.class);
                                intent.putExtra("novoCarrinho", novoCarrinho);
                                startActivity(intent);

                                carregarLista();
                            } else {
                                Toast.makeText(ListaCarrinhoActivity.this, "Seu carrinho não pode ser criado com sucesso!",
                                        Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();

                        }
                    }else {

                        editText.setError("Essa lista já existe.");

                    }
                } else {

                    editText.setError("Preencha Corretamente.");

                }
            }

        });

        // exibe na tela o dialog
        dialog.show();

    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.listacarrinho_view_menu, menu);
    }


    ListaProdutosUsuarioDAO listaProdutosUsuarioDAO = ListaProdutosUsuarioDAO.getInstance();

    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.list_prod_menu_edit:
                carrinhoSelecionado = (ListaProdutosUsuario) listView.getAdapter().getItem(info.position);
                final Dialog dialog = new Dialog(this);



                dialog.setContentView(R.layout.dialogo_custom);

                // define o título do Dialog
                dialog.setTitle(" Nome da Lista ");

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

                editText.setText(carrinhoSelecionado.getNomeLista());

                editText.selectAll();



                confirmar.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        carrinhoSelecionado.setNomeLista(editText.getText().toString());
                        listaProdutosUsuarioDAO.createOrUpdate(carrinhoSelecionado);
                        carregarLista();

                    }
                });
                dialog.show();


                break;
            case R.id.list_prod_menu_delete:

                carrinhoSelecionado = (ListaProdutosUsuario) listView.getAdapter().getItem(info.position);
                listaProdutosUsuarioDAO.delete(carrinhoSelecionado);
                carregarLista();


                break;
        }
        return true;
    }
}
