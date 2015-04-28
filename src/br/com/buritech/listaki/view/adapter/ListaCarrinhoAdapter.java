package br.com.buritech.listaki.view.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;
import br.com.buritech.listaki.view.activity.R;

public class ListaCarrinhoAdapter extends ArrayAdapter<ListaProdutosUsuario> implements Filterable {
    int i = 3;
    private int layoutResourceId;
    private List<ListaProdutosUsuario> carrinho;
    public Context context;
    public List<ListaProdutosUsuario> orig;

    public ListaCarrinhoAdapter(Context context, int layoutResourceId, List<ListaProdutosUsuario> carrinho) {
        super(context, layoutResourceId, carrinho);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.carrinho = carrinho;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return carrinho.size();
    }

    @Override
    public long getItemId(int posicao) {
        // TODO Auto-generated method stub

        return carrinho.get(posicao).getId();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            if (posicao % 2 == 0)
                row.setBackgroundColor(((Activity) context).getResources().getColor(R.color.linha_par));
            else
                row.setBackgroundColor(((Activity) context).getResources().getColor(R.color.linha_impar));
            holder = new RecordHolder();
            holder.nomeLista = (TextView) row.findViewById(R.id.row_list_item_prod_text_view_nome_lista);
            holder.qtdeProdutosCarrinho = (TextView) row.findViewById(R.id.row_list_total_prod);
            row.setTag(holder);

            Bitmap bmp;
            bmp = BitmapFactory.decodeResource(((Activity) context).getResources(), R.drawable.list);
       //     bmp = Bitmap.createScaledBitmap(bmp, 15, 20, true);
            ImageView imagemLista = (ImageView) row.findViewById(R.id.ivIconeLista);
            imagemLista.setImageBitmap(bmp);

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            Bitmap bmp2;
            bmp2 = BitmapFactory.decodeResource(((Activity) context).getResources(), R.drawable.transparent);
            bmp2 = Bitmap.createScaledBitmap(bmp2, 40, 50, true);
            ImageView fundo = (ImageView) row.findViewById(R.id.fundo);
            if (i == 0)
                fundo.setBackground(((Activity) context).getResources().getDrawable(R.drawable.rosa));
            else if (i == 1)
                fundo.setBackground(((Activity) context).getResources().getDrawable(R.drawable.azul));
            else if (i == 2)
                fundo.setBackground(((Activity) context).getResources().getDrawable(R.drawable.amarelo));
            else if (i == 3)
                fundo.setBackground(((Activity) context).getResources().getDrawable(R.drawable.verde));
            else if (i == 4)
                fundo.setBackground(((Activity) context).getResources().getDrawable(R.drawable.roxo));
            else if (i == 5)
                fundo.setBackground(((Activity) context).getResources().getDrawable(R.drawable.lilaz));
            else {
                fundo.setBackground(((Activity) context).getResources().getDrawable(R.drawable.scarlat));
                i = 0;
            }
            fundo.setImageBitmap(bmp2);

            i++;

        } else {
            holder = (RecordHolder) row.getTag();
        }

        ListaProdutosUsuario listaCarrinho = carrinho.get(posicao);
        holder.nomeLista.setText(listaCarrinho.getNomeLista());
        holder.qtdeProdutosCarrinho.setText(String.valueOf(listaCarrinho.getQtdeProdutosCarrinho()));

        //  View view = ((Activity) context).getLayoutInflater().inflate(R.layout.lista_carrinho_row, null);

        return row;
    }



    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<ListaProdutosUsuario> results = new ArrayList<ListaProdutosUsuario>();
                if (orig == null)
                    orig = carrinho;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final ListaProdutosUsuario g : orig) {
                            if (g.getNomeLista().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                carrinho = (List<ListaProdutosUsuario>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    static class RecordHolder {
        TextView nomeLista;
        TextView qtdeProdutosCarrinho;
    }




}

	/*private Context context;
	private int layoutResourceId;
	private List<ListaProdutosUsuario> listaNomeCarrinho = new ArrayList<ListaProdutosUsuario>();

	
	public ListaNomeCarrinhoUsuarioAdapter(Context context, int layoutResourceId,
			List<ListaProdutosUsuario> listaNomeCarrinho) {
		super(context, layoutResourceId, listaNomeCarrinho);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.listaNomeCarrinho = listaNomeCarrinho;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.txtNomeCarrinho = (TextView) row.findViewById(R.id.row_list_item_prod_text_view_nome_lista);
			row.setTag(holder);

		} else {
			holder = (RecordHolder) row.getTag();
		}

		ListaProdutosUsuario carrinhoListaNome = listaNomeCarrinho.get(position);
		holder.txtNomeCarrinho.setText(carrinhoListaNome.getNomeLista());
		
		return row;
	}
	
	public List<ListaProdutosUsuario> getListaCarrinho() {
		return listaNomeCarrinho;
	}

	static class RecordHolder {
		TextView txtNomeCarrinho;
	}
}*/