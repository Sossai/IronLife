package dev42.ironlife.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.model.Evento;

import static android.view.View.GONE;

/**
 * Created by Fernando on 10/10/2016.
 */

public class EventoUsuariosAdapter extends BaseAdapter {

    Activity activity;
    View layout;
    List<Evento> listEventos;

    public EventoUsuariosAdapter(List<Evento> listEventos, Activity activity){
        this.activity = activity;
        this.listEventos = listEventos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        Evento evento = listEventos.get(position);
        ViewHolder holder;
        if(convertView == null){
            layout = inflater.inflate(R.layout.list_eventos, parent, false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else{
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        holder.titulo.setText(evento.getTitulo());
        String txt = "Inicio : " + evento.getDataInicio() + " - " + evento.getHoraInicio();
        holder.inicio.setText(txt);
        txt = "Fim : " + evento.getDataInicio() + " - " + evento.getHoraInicio();
        holder.fim.setText(txt);
        txt = "Organizador : " + evento.getResponsavel();
        holder.responsavel.setText(txt);

        if(evento.isUsuarioRegistrado()){
            holder.ghost.setImageResource(R.drawable.ic_ghost);
            holder.ghost.setVisibility(View.VISIBLE);
        }else {
            holder.ghost.setVisibility(GONE);
        }

        Picasso.with(this.activity)
                .load(evento.getImagem())
                //.fit()
                .into(holder.imagem, new VerificadorRetorno(holder.progress));

        return layout;
    }

    @Override
    public int getCount() {
        return listEventos.size();
    }

    @Override
    public Object getItem(int position) {
        return listEventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        ImageView imagem;
        TextView titulo;
        TextView inicio;
        TextView fim;
        TextView responsavel;
        ImageView ghost;
        ProgressBar progress;

        public ViewHolder(View view){
            this.imagem = (ImageView)view.findViewById(R.id.imagemevento);
            this.titulo = (TextView)view.findViewById(R.id.tituloevento);
            this.inicio = (TextView)view.findViewById(R.id.datainiioevento);
            this.fim = (TextView)view.findViewById(R.id.dataencerramentoevento);
            this.responsavel = (TextView)view.findViewById(R.id.responsavelevento);
            this.ghost = (ImageView)view.findViewById(R.id.ghost);
            this.progress = (ProgressBar) view.findViewById(R.id.progress);
        }
    }

    class VerificadorRetorno implements Callback {
        private ProgressBar holder;

        public VerificadorRetorno(ProgressBar holder){
            this.holder = holder;
        }

        @Override
        public void onSuccess() {
            holder.setVisibility(View.GONE);
        }

        @Override
        public void onError() {
            holder.setVisibility(View.GONE);
        }
    }

}
