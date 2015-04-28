package br.com.buritech.listaki.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.view.activity.R;


public class IncluirProdutosAdapter extends BaseAdapter {
	
	private final List<ItemListaProdutosUsuario> listaProduto;
	private final Activity activity;
	
	public IncluirProdutosAdapter(Activity activity, List<ItemListaProdutosUsuario> listaProduto) {
		// TODO Auto-generated constructor stub
		
		this.listaProduto = listaProduto;
		this.activity = activity;
		
	}
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		
		return listaProduto.size();
	}

	@Override
	public Object getItem(int posicao) {
		// TODO Auto-generated method stub
		return listaProduto.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		// TODO Auto-generated method stub
		
		
		return listaProduto.get(posicao).getId();
		
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View view = activity.getLayoutInflater().inflate(R.layout.incluir_produtos_row, null);
		
		ItemListaProdutosUsuario produto = listaProduto.get(posicao);
		
		TextView nomeProduto = (TextView) view.findViewById(R.id.tvNomeProduto);
		nomeProduto.setText(produto.getCodigoBarras());
		
		return view;
	}
	
	
	

}
