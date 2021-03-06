package dev42.ironlife.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
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

import static dev42.ironlife.R.id.swipe;


public class TutorialYoutubeActivity extends AppCompatActivity implements RetornoDelegate, SwipeRefreshLayout.OnRefreshListener {

    private List<TutorialYoutube> listTutorialYoutube = null;
    private ListView listView;
    private Integer ultimoId = 0;
    private Boolean carregarMaisItens = true;
    private TutorialYoutubeAdapter adapter;
    private FrameLayout frameload;
    private SwipeRefreshLayout swipe;
    private SearchView mSearchView;
    private String palavraBuscada ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_youtube);

        //  **  Action Bar return   **
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listViewYoutube);
        frameload = (FrameLayout)findViewById(R.id.frameload);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TutorialYoutube tutorialYoutube = (TutorialYoutube)listTutorialYoutube.get(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tutorialYoutube.getLink())));
//                Log.e("LidaComRetorno", "click :" + tutorialYoutube.getTitulo());
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 ){

                    if(carregarMaisItens == true){
//                        Log.e("LidaComRetorno", "load MORE");
                        frameload.setVisibility(View.VISIBLE);
                        carregaLista(ultimoId, mSearchView.getQuery().toString());
                        carregarMaisItens = false;
                    }
                }
            }
        });

        frameload.setVisibility(View.VISIBLE);
        carregaLista(ultimoId, "");
        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_tutotial_youtube, menu);
        super.onCreateOptionsMenu(menu);

        //Pega o Componente.
        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //Define um texto de ajuda:
        mSearchView.setQueryHint("Pesquisar");
        mSearchView.setIconifiedByDefault(false);
        //mSearchView.setSubmitButtonEnabled(true);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

/*                palavraBuscada = mSearchView.getQuery().toString();
                listTutorialYoutube.clear();
                listView.setAdapter(null);
                carregaLista(0, mSearchView.getQuery().toString());*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                palavraBuscada = mSearchView.getQuery().toString();
                listTutorialYoutube.clear();
                listView.setAdapter(null);
//                carregarMaisItens = false;
                carregaLista(0, mSearchView.getQuery().toString());
                //adapter.notifyDataSetChanged();

                return false;
            }
        });

        return true;
    }

    protected void carregaLista(Integer ultimoId, String palavraBuscada){
        String url = getString(R.string.url_lista_tutoriais_youtube);
        url += ultimoId;
        if(palavraBuscada == null)
            url += "/";
        else
            url += "/" + palavraBuscada;

        GetDadosTask task = new GetDadosTask(this, url, null, "GET");
        task.execute();
    }
    @Override
    public void LidaComRetorno(String retorno) {
//        Log.e("Sucesso", retorno);

        TutorialYoutubeConverter converter = new TutorialYoutubeConverter();
        List<TutorialYoutube> listTutorialYoutubeAux = converter.converte(retorno);

        //  **  Se não tiver vazio baixou mais itens    **
        if(!listTutorialYoutubeAux.isEmpty()){
//            Log.e("LidaComRetorno", "baixou mais");

            ultimoId = listTutorialYoutubeAux.get(listTutorialYoutubeAux.size()-1).getId();
//            Log.e("LidaComRetorno", "Ultimo id"+ultimoId.toString());

            if(listView.getAdapter()==null){
//                Log.e("LidaComRetorno", "insert");
                listTutorialYoutube = listTutorialYoutubeAux;
                adapter = new TutorialYoutubeAdapter(listTutorialYoutube, this);
                listView.setAdapter(adapter);
            }else{
//                Log.e("LidaComRetorno", "Update");
                listTutorialYoutube.addAll(listTutorialYoutubeAux);
                adapter.notifyDataSetChanged();
            }

            carregarMaisItens = true;
        }//else
//            Log.e("LidaComRetorno", "baixou mais NAO!");

        frameload.setVisibility(View.GONE);
        this.swipe.setRefreshing(false);
        this.swipe.clearAnimation();


/*        Integer tam = listTutorialYoutube.size();
        Log.e("Tam", tam.toString());*/

    }
    @Override
    public void LidaComErro(String erro) {
        Log.e("Erro", erro);
        frameload.setVisibility(View.GONE);
        this.swipe.setRefreshing(false);
        this.swipe.clearAnimation();
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

    @Override
    public void onRefresh() {

        listTutorialYoutube.clear();
        listView.setAdapter(null);
        carregaLista(0, mSearchView.getQuery().toString());
        //adapter.notifyDataSetChanged();
    }

}
