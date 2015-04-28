package br.com.buritech.listaki.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.view.activity.R;

public class ListaProdutosCarrinhoAdapter extends ArrayAdapter<ItemListaProdutosUsuario> {
	private Context context;
	private int layoutResourceId;
	private List<ItemListaProdutosUsuario> listaItens = new ArrayList<ItemListaProdutosUsuario>();

	
	public ListaProdutosCarrinhoAdapter(Context context, int layoutResourceId, List<ItemListaProdutosUsuario> listaItens) {
		super(context, layoutResourceId, listaItens);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
		this.listaItens = listaItens;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.txtCodigoBarras = (TextView) row.findViewById(R.id.row_list_item_prod_text_view_cod_barras);
			holder.txtQtde = (TextView) row.findViewById(R.id.row_list_item_prod_text_view_qtde);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		ItemListaProdutosUsuario itemLista = listaItens.get(position);
		holder.txtCodigoBarras.setText(itemLista.getCodigoBarras());
		holder.txtQtde.setText("" + itemLista.getQtdeDesejada());
		
		return row;
	}
	
	public List<ItemListaProdutosUsuario> getListaItens() {
		return listaItens;
	}

	static class RecordHolder {
		TextView txtCodigoBarras;
		TextView txtQtde;
	}
}
