package dev42.ironlife.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.security.PrivateKey;
import java.util.List;
import java.util.PriorityQueue;

import dev42.ironlife.R;
import dev42.ironlife.model.TipoEvento;

/**
 * Created by Fernando on 17/10/2016.
 */

public class TipoEventoAdapter extends BaseAdapter {

    private List<TipoEvento> listTp;
    private Activity activity;
    private View layout;

    public TipoEventoAdapter(List<TipoEvento> listTp, Activity activity){
        this.listTp = listTp;
        this.activity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        TipoEvento tipoEvento = listTp.get(position);
        ViewHolder holder;
        if(convertView == null){
            layout = inflater.inflate(R.layout.list_tipo_eventos, parent,false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else{
            layout = convertView;
            holder = (ViewHolder) layout.getTag();
        }

        holder.id.setText(tipoEvento.getId().toString());
        holder.descricao.setText(tipoEvento.getDescricao().toString());
        holder.imagem.setText(tipoEvento.getImagem());

        return layout;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listTp.get(position);
    }

    @Override
    public int getCount() {
        return listTp.size();
    }

    class ViewHolder{
        TextView descricao;
        TextView id;
        TextView imagem;
        public ViewHolder(View view){
            this.id = (TextView) view.findViewById(R.id.id_tipo_evento);
            this.descricao = (TextView)view.findViewById(R.id.tipo_evento_descricao);
            this.imagem = (TextView)view.findViewById(R.id.tipo_evento_imagem);
        }
    }
}

