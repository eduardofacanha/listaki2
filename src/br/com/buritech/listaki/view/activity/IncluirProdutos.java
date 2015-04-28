package br.com.buritech.listaki.view.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import br.com.buritech.listaki.core.view.activity.BaseActivity;
import br.com.buritech.listaki.model.dao.impl.ItemListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.dao.impl.ListaProdutosUsuarioDAO;
import br.com.buritech.listaki.model.dao.impl.ProdutoDAO;
import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.Produto;
import br.com.buritech.listaki.presenter.ProdutoPresenter;

@ContentView(R.layout.incluir_produtos)
public class IncluirProdutos extends BaseActivity {

	//@InjectView(R.id.btn_add_produto)
	//private Button addProduto;
	
	//@InjectView(R.id.edt_add_codigo_barras)
	//private EditText addCodigoBarras;
	
	@InjectView(R.id.lv_incluir_produto)
	private ListView lvIncluirProduto;
	
	private List<ItemListaProdutosUsuario> listaProduto;
	
	private ArrayAdapter<ItemListaProdutosUsuario> adapter;
	
	private int adapterLayout = android.R.layout.simple_list_item_1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		listaProduto = new ArrayList<ItemListaProdutosUsuario>();
	
		adapter = new ArrayAdapter<ItemListaProdutosUsuario>(this, adapterLayout, listaProduto);
		
		lvIncluirProduto.setAdapter(adapter);
		
		
		/*addProduto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Produto produto = new Produto();
				ProdutoPresenter presenter = new ProdutoPresenter(IncluirProdutos.this);
				ItemListaProdutosUsuario item = new ItemListaProdutosUsuario();
				
				produto.setCodigoBarras(addCodigoBarras.getText().toString());
				item.setProduto(produto);
				
				presenter.incluirItemListaProdutosUsuario(item);
				
				
				
			}
		});*/
	
	}
	
	public void carregarLista(){
		
		ItemListaProdutosUsuarioDAO dao =  ItemListaProdutosUsuarioDAO.getInstance();
		
		this.listaProduto = dao.findAll();
		
		this.adapter = new ArrayAdapter<ItemListaProdutosUsuario>(this, adapterLayout, listaProduto);
		
		this.lvIncluirProduto.setAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		carregarLista();
	}
}
