package dev42.ironlife.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.adapters.TutorialYoutubeAdapter;
import dev42.ironlife.converters.TutorialYoutubeConverter;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.TutorialYoutube;
import dev42.ironlife.tasks.GetDadosTask;


public class TutorialYoutubeActivity extends AppCompatActivity implements RetornoDelegate {

    private List<TutorialYoutube> listTutorialYoutube = null;
    private ListView listView;
    private Integer ultimoId = 0;
    private Boolean carregarMaisItens = true;
    private TutorialYoutubeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_youtube);

        //  **  Action Bar return   **
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listViewYoutube);


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 ){

                    if(carregarMaisItens == true){
                        Log.e("load more", "load");
                        carregaLista(ultimoId);
                        carregarMaisItens = false;
                    }


/*                    if(carregarMaisItens == true){
                        Toast.makeText(getActivity(), "dar Load",Toast.LENGTH_SHORT ).show();
                        Log.e("load more", "load");
                        //  **  Carregar mais oficinas por cidade estado    **


                    }*/

                }
            }
        });



        carregaLista(ultimoId);
    }

    protected void carregaLista(Integer ultimoId){
        String url = getString(R.string.url_lista_tutoriais_youtube);
        /*if(ultimoId == null)
            ultimoId = 0;*/

        url += ultimoId;
        GetDadosTask task = new GetDadosTask(this, url, null, "GET");
        task.execute();
    }
    @Override
    public void LidaComRetorno(String retorno) {
        Log.e("Sucesso", retorno);

        List<TutorialYoutube> listTutorialYoutubeAux = listTutorialYoutube;

        TutorialYoutubeConverter converter = new TutorialYoutubeConverter();
        listTutorialYoutube = converter.converte(retorno);

//        listTutorialYoutube.addAll(converter.converte(retorno));
        ultimoId = listTutorialYoutube.get(listTutorialYoutube.size()-1).getId();
        Log.e("Ultimo ID", ultimoId.toString());

        if(listView.getAdapter()==null){
            Log.e("LidaComRetorno", "insert");
            adapter = new TutorialYoutubeAdapter(listTutorialYoutube, this);
            listView.setAdapter(adapter);
        }else{
            Log.e("LidaComRetorno", "Update");
            listTutorialYoutubeAux.addAll(listTutorialYoutube);
            listTutorialYoutube = listTutorialYoutubeAux;
            adapter.notifyDataSetChanged();
        }


/*        Integer tam = listTutorialYoutube.size();
        Log.e("Tam", tam.toString());*/

    }
    @Override
    public void LidaComErro(String erro) {
        Log.e("Erro", erro);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                //finish();
                super.onBackPressed();
                return true;
        }
        return true;
    }
}
