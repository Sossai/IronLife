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
import dev42.ironlife.model.EventoUsuariosView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Fernando on 10/10/2016.
 */

public class EventoUsuariosViewAdapter extends BaseAdapter {

    Activity activity;
    View layout;
    List<EventoUsuariosView> listEventoUsuariosView;

    public EventoUsuariosViewAdapter(List<EventoUsuariosView> listEventoUsuariosView, Activity activity){
        this.activity = activity;
        this.listEventoUsuariosView = listEventoUsuariosView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        EventoUsuariosView eventoUsuariosView = listEventoUsuariosView.get(position);
        ViewHolder holder;
        if(convertView == null){
            layout = inflater.inflate(R.layout.list_usuarios_par_impar, parent, false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else{
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        holder.nickpsnPar.setText(eventoUsuariosView.getUsuarioPar().getNickpsn());
        if(eventoUsuariosView.getUsuarioImpar() == null) {
            //holder.nickpsnImpar.setText("");
            //holder.imgImpar.setVisibility(GONE);
            holder.celulaImpar.setVisibility(View.INVISIBLE);
        }else
            holder.nickpsnImpar.setText(eventoUsuariosView.getUsuarioImpar().getNickpsn());
        return layout;
    }

    @Override
    public int getCount() {
        return listEventoUsuariosView.size();
    }

    @Override
    public Object getItem(int position) {
        return listEventoUsuariosView.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView nickpsnPar;
        TextView nickpsnImpar;
        ImageView imgPar;
        ImageView imgImpar;
        View celulaImpar;

        public ViewHolder(View view){
            this.nickpsnPar = (TextView)view.findViewById(R.id.usuariopar);
            this.nickpsnImpar = (TextView)view.findViewById(R.id.usuarioimpar);
            this.imgPar = (ImageView)view.findViewById(R.id.ghostU1);
            this.imgImpar = (ImageView)view.findViewById(R.id.ghostU2);
            this.celulaImpar = (View) view.findViewById(R.id.celulaImpar);
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
