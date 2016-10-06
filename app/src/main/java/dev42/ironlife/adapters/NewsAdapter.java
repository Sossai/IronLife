package dev42.ironlife.adapters;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

import dev42.ironlife.R;
import dev42.ironlife.model.News;

/**
 * Created by Fernando on 05/10/2016.
 */

public class NewsAdapter extends BaseAdapter {

    private Activity activity;
    private List<News> listanews;
    private View layout;

    public NewsAdapter(List<News> listaNews, Activity activity){
        this.activity = activity;
        this.listanews = listaNews;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        News news = listanews.get(position);
        ViewHolder holder;

        if(convertView == null){
            layout = inflater.inflate(R.layout.list_news, parent, false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else {
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        holder.titulo.setText(news.getTitulo());
        //holder.imagem.setImageBitmap();
        holder.data.setText(news.getData());
        return layout;
    }

    @Override
    public int getCount() {
        return listanews.size();
    }

    @Override
    public Object getItem(int position) {
        return listanews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView titulo;
        ImageView imagem;
        TextView data;

        public ViewHolder(View view){
            this.titulo = (TextView)view.findViewById(R.id.titulonews);
            this.imagem = (ImageView) view.findViewById(R.id.imagemnews);
            this.data = (TextView)view.findViewById(R.id.datanews);
        }
    }
}
