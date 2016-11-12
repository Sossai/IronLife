package dev42.ironlife.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.model.Evento;

import static android.view.View.GONE;

/**
 * Created by Fernando on 10/10/2016.
 */

public class EventoAdapter extends BaseAdapter {

    Activity activity;
    View layout;
    List<Evento> listEventos;

    public EventoAdapter(List<Evento> listEventos, Activity activity){
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
        //String txt = "Inicio : " + evento.getDataInicio() + " - " + evento.getHoraInicio();
        holder.inicio.setText(evento.getDataInicio());
        holder.hrinicio.setText(evento.getHoraInicio().toString());
        //txt = "Fim : " + evento.getDataInicio() + " - " + evento.getHoraInicio();
        holder.fim.setText(evento.getDataEncerramento());
        holder.hrfim.setText(evento.getHoraEncerramento().toString());
        //String txt = "Organizador : " + evento.getResponsavel();
        holder.responsavel.setText("Organizador : " + evento.getResponsavel());
        holder.descricaoTipoEvento.setText(evento.getDescricaoTipoEvento());
        holder.idtipoevento.setText(evento.getIdTipoEvento().toString());
        holder.tipoevendodescricao.setText(evento.getDescricaoTipoEvento());

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
        TextView hrinicio;
        TextView fim;
        TextView hrfim;
        TextView responsavel;
        ImageView ghost;
        ProgressBar progress;
        TextView descricaoTipoEvento;
        TextView idtipoevento;
        TextView tipoevendodescricao;

        public ViewHolder(View view){
            this.imagem = (ImageView)view.findViewById(R.id.imagemevento);
            this.titulo = (TextView)view.findViewById(R.id.tituloevento);
            this.inicio = (TextView)view.findViewById(R.id.datainiioevento);
            this.hrinicio = (TextView)view.findViewById(R.id.horainicioevento);
            this.fim = (TextView)view.findViewById(R.id.dataencerramentoevento);
            this.hrfim = (TextView)view.findViewById(R.id.horafimevento);
            this.responsavel = (TextView)view.findViewById(R.id.responsavelevento);
            this.ghost = (ImageView)view.findViewById(R.id.ghost);
            this.descricaoTipoEvento = (TextView)view.findViewById(R.id.descricaoTipoEvento);
            this.progress = (ProgressBar) view.findViewById(R.id.progress);
            this.idtipoevento = (TextView) view.findViewById(R.id.idTipoEvento);
            this.tipoevendodescricao = (TextView) view.findViewById(R.id.tipoevendodescricao);
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
