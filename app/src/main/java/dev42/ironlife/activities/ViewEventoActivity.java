package dev42.ironlife.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
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
import dev42.ironlife.model.UsuarioLogadoBung;
import dev42.ironlife.tasks.GetDadosTask;

public class ViewEventoActivity extends AppCompatActivity implements RetornoDelegate {

    private Evento eventoSelecionado;
    private List<UsuarioLogadoBung> listaUsuarios;
    private ListView listViewUsuarios;
    private GridView gridViewUsuarios;
    private Integer passo;
    private UsuarioLogadoBung usuarioLogadoBung;
    private View frameLoad;
    private boolean permiteConvocar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_evento);

        //  **  Action Bar return   **
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        frameLoad = (View)findViewById(R.id.frameload);

        usuarioLogadoBung = new UsuarioLogadoBung(this);
        usuarioLogadoBung.getDadosShared();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        eventoSelecionado = (Evento) bundle.getSerializable("eventoSelecionado");

        //  **  Apenas o criador pode convocar  **
        if(!eventoSelecionado.getIdResponsavel().equals(usuarioLogadoBung.getMembershipId())){
            //  **  Chama o oncreate novamente para desabilitar a opção **
            permiteConvocar = false;
            invalidateOptionsMenu();
        }

        helperEvento();
        carregaEvento();

        listViewUsuarios = (ListView)findViewById(R.id.usuarios);
        gridViewUsuarios = (GridView)findViewById(R.id.usuariosgrid);
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

        frameLoad.setVisibility(View.VISIBLE);
        passo = 1;
        GetDadosTask task = new GetDadosTask(this, url,null, "GET");
        task.execute();
    }

    protected void convocar(){
        //String url = getString(R.string.url_evento_com_usuarios);
        String url = getString(R.string.url_convocar_usuarios_bungie);
        url += eventoSelecionado.getId();
        url += '/' + usuarioLogadoBung.getMembershipId();
//        url += "/1" ;

        frameLoad.setVisibility(View.VISIBLE);
        passo = 2;
        GetDadosTask task = new GetDadosTask(this, url,null, "GET");
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_view_evento, menu);
        if(!permiteConvocar){
            menu.getItem(0).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                //finish();
                super.onBackPressed();
                return true;
            case R.id.convocar:
                convocar();
                return true;
        }
        return true;
    }

    @Override
    public void LidaComRetorno(String retorno) {

        frameLoad.setVisibility(View.GONE);
        switch (passo){
            case 1:
                EventoUsuariosViewConverter eventoUsuariosViewConverter = new EventoUsuariosViewConverter();
                listaUsuarios = eventoUsuariosViewConverter.converte(retorno);

                if(listaUsuarios != null){
                    EventoUsuariosViewAdapter adapter = new EventoUsuariosViewAdapter(listaUsuarios, this );
//                    listViewUsuarios.setAdapter(adapter);
                    gridViewUsuarios.setAdapter(adapter);
                }
                break;
            case 2:
                Toast.makeText(this, "Guardiões, convocados com sucesso.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void LidaComErro(String erro) {
        frameLoad.setVisibility(View.GONE);
        Log.e("Erro", erro);
        switch (passo) {
            case 1:
                Toast.makeText(this, "Lamento Guardião, erro ao conectar no servidor.", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "Lamento Guardião, não foi possível notificar os guardiões.", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
