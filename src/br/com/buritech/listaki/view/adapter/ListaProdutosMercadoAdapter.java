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

/**
 * Created by susan on 19/02/15.
 */
public class ListaProdutosMercadoAdapter extends ArrayAdapter<ItensDadoCotacao> {

    private Context context;
    private int layoutResourceId;
    private List<ItensDadoCotacao> listaItensProdutos = new ArrayList<ItensDadoCotacao>();


    public ListaProdutosMercadoAdapter(Context context, int layoutResourceId, List<ItensDadoCotacao> listaItensProdutos) {
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
            holder.textNomeProduto  = (TextView) row.findViewById(R.id.grid_cotacao_row_nome_produto);
            holder.textValorTotal = (TextView) row.findViewById(R.id.grid_cotacao_row_valor_total);
            holder.textQtdeDisponivel = (TextView) row.findViewById(R.id.grid_cotacao_row_qtde_disponivel);
            row.setTag(holder);

        } else {
            holder = (RecordHolder) row.getTag();
        }

        ItensDadoCotacao itemListaProdutos = listaItensProdutos.get(position);

        holder.textNomeProduto.setText("Farinha de Trigo");//itemListaProdutos.getProdutoEstabelecimento().getEstabelecimento().getRazaoSocial());
        holder.textValorTotal.setText("R$ 10,00");
        holder.textQtdeDisponivel.setText("Quantidade 15");
        return row;
    }

    static class RecordHolder {
        TextView textNomeProduto;
        TextView textValorTotal;
        TextView textQtdeDisponivel;
    }
}

