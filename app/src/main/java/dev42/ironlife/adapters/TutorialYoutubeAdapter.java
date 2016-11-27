package dev42.ironlife.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.model.Evento;
import dev42.ironlife.model.TutorialYoutube;

import static android.view.View.GONE;

/**
 * Created by Fernando on 10/10/2016.
 */

public class TutorialYoutubeAdapter extends BaseAdapter {

    Activity activity;
    View layout;
    List<TutorialYoutube> listYoutube;

    public TutorialYoutubeAdapter(List<TutorialYoutube> listYoutube, Activity activity){
        this.activity = activity;
        this.listYoutube = listYoutube;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        TutorialYoutube tutorialYoutube = listYoutube.get(position);

        ViewHolder holder;
        if(convertView == null){
            layout = inflater.inflate(R.layout.list_tutorial_youtube, parent, false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else{
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        holder.titulo.setText(tutorialYoutube.getTitulo());
        holder.link.setText(tutorialYoutube.getLink());

        Picasso.with(this.activity)
                .load(geraThumbYoutube(tutorialYoutube.getLink()))
                .into(holder.imagem);
        return layout;
    }

    protected String geraThumbYoutube(String linkYoutube){
        Integer pos = linkYoutube.indexOf("watch?v=");
        String codigo = linkYoutube.substring(pos,linkYoutube.length()).replace("watch?v=","");
        String thumbYoutube = "http://img.youtube.com/vi/" + codigo + "/0.jpg";

        return thumbYoutube;
    }

    @Override
    public int getCount() {
        return listYoutube.size();
    }

    @Override
    public Object getItem(int position) {
        return listYoutube.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        ImageView imagem;
        TextView titulo;
        TextView link;

        public ViewHolder(View view){
            this.imagem = (ImageView)view.findViewById(R.id.img_video);
            this.titulo = (TextView)view.findViewById(R.id.titulovideo);
            this.link = (TextView)view.findViewById(R.id.link_youtube);
        }
    }


}
