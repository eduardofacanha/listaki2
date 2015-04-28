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

import br.com.buritech.listaki.model.entidade.ItensDadoCotacao;
import br.com.buritech.listaki.view.activity.R;

public class ListaMercadoAdapter extends ArrayAdapter<ItensDadoCotacao> {
	private Context context;
	private int layoutResourceId;
	private List<ItensDadoCotacao> listaItensProdutos = new ArrayList<ItensDadoCotacao>();

	
	public ListaMercadoAdapter(Context context, int layoutResourceId, List<ItensDadoCotacao> listaItensProdutos) {
		super(context, layoutResourceId, listaItensProdutos);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.listaItensProdutos = listaItensProdutos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			if (position % 2 == 0)
				row.setBackgroundColor(((Activity) context).getResources().getColor(R.color.linha_par));
			else row.setBackgroundColor(((Activity) context).getResources().getColor(R.color.linha_impar));
			
			holder = new RecordHolder();
			holder.textNomeSupermercado  = (TextView) row.findViewById(R.id.grid_cotacao_row_nome_supermercado);
//			holder.textQtdeDesejada = (TextView) row.findViewById(R.id.grid_cotacao_row_qtde_desejada);
//			holder.textQtdeDisponivel = (TextView) row.findViewById(R.id.grid_cotacao_row_qtde_disponivel);
//			holder.textQtdeNaoDisponivel = (TextView) row.findViewById(R.id.grid_cotacao_row_qtde_nao_disponivel);
			holder.textValorTotal = (TextView) row.findViewById(R.id.grid_cotacao_row_valor_total);
			row.setTag(holder);

		} else {
			holder = (RecordHolder) row.getTag();
		}

		ItensDadoCotacao itemListaProdutos = listaItensProdutos.get(position);
		
//		holder.textNomeSupermercado.setText("Supermercado Carrefour");//itemListaProdutos.getProdutoEstabelecimento().getEstabelecimento().getRazaoSocial());
        holder.textNomeSupermercado.setText(itemListaProdutos.getProdutoEstabelecimento().getEstabelecimento().getRazaoSocial());//itemListaProdutos.getProdutoEstabelecimento().getEstabelecimento().getRazaoSocial());
//		holder.textQtdeDesejada.setText("20");
//		holder.textQtdeDisponivel.setText("15");
//		holder.textQtdeNaoDisponivel.setText("5");
		holder.textValorTotal.setText("R$ 1000,00");
		return row;
	}

	static class RecordHolder {
		TextView textNomeSupermercado;
//		TextView textQtdeDesejada;
//		TextView textQtdeDisponivel;
//		TextView textQtdeNaoDisponivel;
		TextView textValorTotal;
	}
}