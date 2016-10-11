package dev42.ironlife.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.adapters.NewsAdapter;
import dev42.ironlife.model.News;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ListView listaN = (ListView)findViewById(R.id.listviewnews);

        //List<News> listaNews = new ArrayList<>();
        List<News> listaNews = simulaListaNews();

        Integer tam = listaNews.size();
        NewsAdapter adapter = new NewsAdapter(listaNews, this);
        listaN.setAdapter(adapter);
    }

    private List<News> simulaListaNews(){

        List<News> listaNews = new ArrayList<>();

        News news = new News();
        news.setTitulo("Bem vindo");
        news.setData("29 de setembro de 2016 - 20:35");
        listaNews.add(news);

        news = new News();
        news.setTitulo("Novidades do clã");
        news.setData("21 de setembro de 2016 - 15:21");
        listaNews.add(news);

        return listaNews;
    }
}
