package dev42.ironlife.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.adapters.EventoUsuariosViewAdapter;
import dev42.ironlife.converters.EventoUsuariosViewConverter;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.Evento;
import dev42.ironlife.model.EventoUsuariosView;
import dev42.ironlife.tasks.GetDadosTask;

public class ViewEventoActivity extends AppCompatActivity implements RetornoDelegate {

    private Evento eventoSelecionado;
    private List<EventoUsuariosView> eventoUsuariosViews;
    ListView listViewUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_evento);

        //  **  Action Bar return   **
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        eventoSelecionado = (Evento) bundle.getSerializable("eventoSelecionado");
        helperEvento();
        carregaEvento();

        listViewUsuarios = (ListView)findViewById(R.id.usuarios);
    }

    protected void helperEvento(){
        TextView titulo = (TextView)findViewById(R.id.addtitulo);
        TextView dataInicio = (TextView)findViewById(R.id.datainicio);
        TextView horaInicio = (TextView)findViewById(R.id.horainicio);
        TextView dataFim = (TextView)findViewById(R.id.datafim);
        TextView horaFim = (TextView)findViewById(R.id.horafim);
        TextView tipo = (TextView)findViewById(R.id.tipo);

        titulo.setText(eventoSelecionado.getTitulo());
        dataInicio.setText(eventoSelecionado.getDataInicio());
        horaInicio.setText(eventoSelecionado.getHoraInicio());
        dataFim.setText(eventoSelecionado.getDataEncerramento());
        horaFim.setText(eventoSelecionado.getHoraEncerramento());
        tipo.setText(eventoSelecionado.getDescricaoTipoEvento());

    }

    protected void carregaEvento(){
        //String url = getString(R.string.url_evento_com_usuarios);
        String url = getString(R.string.url_lista_usuarios_evento);
        url += eventoSelecionado.getId();    //  **  Pegar por parametro **

        GetDadosTask task = new GetDadosTask(this, url,null, "GET");
        task.execute();
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
    public void LidaComRetorno(String retorno) {

        EventoUsuariosViewConverter eventoUsuariosViewConverter = new EventoUsuariosViewConverter();
        eventoUsuariosViews = eventoUsuariosViewConverter.converte(retorno);

        if(eventoUsuariosViews != null){
            EventoUsuariosViewAdapter adapter = new EventoUsuariosViewAdapter(eventoUsuariosViews, this );
            listViewUsuarios.setAdapter(adapter);
        }
    }

    @Override
    public void LidaComErro(String erro) {
        Log.e("Erro", erro);
        Toast.makeText( this, "Lamento Guardião, erro ao conectar no servidor.", Toast.LENGTH_LONG).show();
    }
}
