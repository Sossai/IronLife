package dev42.ironlife.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.converters.TutorialYoutubeConverter;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.TutorialYoutube;
import dev42.ironlife.tasks.GetDadosTask;

public class TutorialYoutubeActivity extends AppCompatActivity implements RetornoDelegate {

    List<TutorialYoutube> listTutorialYoutube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_youtube);

        //  **  Action Bar return   **
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        carregaLista(0);
    }

    protected void carregaLista(Integer ultimoId){
        String url = getString(R.string.url_lista_tutoriais_youtube);
        if(ultimoId == null)
            ultimoId = 0;

//        Log.e("Url", url);

        url += ultimoId;
        GetDadosTask task = new GetDadosTask(this, url, null, "GET");
        task.execute();
    }
    @Override
    public void LidaComRetorno(String retorno) {
        Log.e("Sucesso", retorno);

        TutorialYoutubeConverter converter = new TutorialYoutubeConverter();
        listTutorialYoutube = converter.converte(retorno);

        Integer tam = listTutorialYoutube.size();
        Log.e("Tam", tam.toString());

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
