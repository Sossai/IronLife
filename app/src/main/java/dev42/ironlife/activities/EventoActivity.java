package dev42.ironlife.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.adapters.EventoAdapter;
import dev42.ironlife.converters.EventoConverter;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.Evento;
import dev42.ironlife.model.Usuario;
import dev42.ironlife.tasks.GetDadosTask;

public class EventoActivity extends AppCompatActivity implements RetornoDelegate, SwipeRefreshLayout.OnRefreshListener {

    private List<Evento> eventos;
    private ListView listView;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipe;
    private Boolean progress;
    private Integer tipoRetorno;
    private EventoAdapter adapter;
    private Integer posicaoEventoSelecionado;
    private Evento eventoSelecionado;
    private final String PREF_NOME = "UsuarioShared";
    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        listView = (ListView)findViewById(R.id.listvieweventos);
        progress = true;
        carregaDadosShared();
        carregaLista();

        registerForContextMenu(listView);
        //  **  Chamo o ContextMenu com 1 clique    **
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.showContextMenu();
            }
        });

        Button addEvento = (Button)findViewById(R.id.btnadd);
        addEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventoActivity.this, AddEventoActivity.class );
                startActivity(intent);
            }
        });

        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        posicaoEventoSelecionado = info.position;
        eventoSelecionado = (Evento)listView.getItemAtPosition(info.position);

        MenuItem inscrever = menu.add("Inscrever");
        MenuItem cancelar = menu.add("Cancelar Inscrição");
        MenuItem sobre = menu.add("Sobre");

        if(eventoSelecionado.isUsuarioRegistrado()){
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }
        else{
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        }

        inscrever.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                inscreverEvento(eventoSelecionado);
                return false;
            }
        });

        cancelar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                cancelarEvento(eventoSelecionado);
                return false;
            }
        });

        sobre.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(EventoActivity.this, ViewEventoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("eventoSelecionado",eventoSelecionado);
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }
        });
    }

    public void inscreverEvento(Evento evento){
        tipoRetorno = 2;
        String url = getString(R.string.url_inscrever_evento);
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("idevento",evento.getId().toString());
        postDataParams.put("idusuario",usuarioLogado.getId().toString());    //  **  Alterar ** Id logado
        GetDadosTask task = new GetDadosTask(this, url, postDataParams, "POST");
        task.execute();
    }

    public void cancelarEvento(Evento evento){
        tipoRetorno = 3;
        String url = getString(R.string.url_cancelar_evento);
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("idevento",evento.getId().toString());
        postDataParams.put("idusuario",usuarioLogado.getId().toString());    //  **  Alterar ** Id logado
        GetDadosTask task = new GetDadosTask(this, url, postDataParams, "POST");
        task.execute();
    }

    public void carregaLista(){
        tipoRetorno = 1;
        if(progress) {
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog = ProgressDialog.show(this, "", "Contactando o servidor, por favor, aguarde alguns instantes.", true, false);
        }

        String url = getString(R.string.url_lista_eventos) + usuarioLogado.getId().toString();
        //url += 1;   //  **  Id do usuario logado    **
        GetDadosTask task = new GetDadosTask(this, url, null, "GET");
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_evento, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId())
        {
            case R.id.logout:
                //  **  Remove o shared preferences **
                SharedPreferences settings = this.getSharedPreferences(PREF_NOME,0);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();

                //  **  Volta tela delogin  **
                Intent intent = new Intent(EventoActivity.this, MainActivity.class);
                startActivity(intent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void LidaComRetorno(String retorno) {
        if(progress)
            progressDialog.dismiss();

        switch (tipoRetorno){
            case 1:
                this.swipe.setRefreshing(false);
                this.swipe.clearAnimation();

                EventoConverter converter = new EventoConverter();
                eventos = converter.converte(retorno);

                if(eventos != null){
                    adapter = new EventoAdapter(eventos, this);
                    listView.setAdapter(adapter);
                }else
                    Toast.makeText(this, "Não foram encontrados Eventos", Toast.LENGTH_LONG).show();
                break;

            case 2:
                //Log.e("Sucesso 2", retorno);
                if(retorno.trim().equals("SUCESSO")){

                    //  **  Remove o evento e insere uma atualizado na lista    **
                    Evento eventoAlterado = eventoSelecionado;
                    eventoAlterado.setUsuarioRegistrado(true);

                    eventos.remove(eventoSelecionado);
                    eventos.add(posicaoEventoSelecionado, eventoAlterado);
                    adapter.notifyDataSetChanged();

                    Toast.makeText( this, "Seja bem vindo Guardião!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText( this, "Lamento Guardião, não foi possível cadastra-lo.", Toast.LENGTH_LONG).show();
                break;

            case 3:
                Log.e("Sucesso 3", retorno);
                if(retorno.trim().equals("SUCESSO")) {

                    //  **  Remove o evento e insere uma atualizado na lista    **
                    Evento eventoAlterado = eventoSelecionado;
                    eventoAlterado.setUsuarioRegistrado(false);

                    eventos.remove(eventoSelecionado);
                    eventos.add(posicaoEventoSelecionado, eventoAlterado);
                    adapter.notifyDataSetChanged();

                    Toast.makeText(this, "Adeus Guardião!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText( this, "Lamento Guardião, não foi possível remove-lo.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void LidaComErro(String erro) {
        if(progress)
            progressDialog.dismiss();

        switch (tipoRetorno){
            case 1:
                this.swipe.setRefreshing(false);
                this.swipe.clearAnimation();

                Toast.makeText(this, "Erro ao buscar eventos.", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText( this, "Lamento Guardião, não foi possível cadastra-lo.", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText( this, "Lamento Guardião, não foi possível remove-lo.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onRefresh() {
        progress = false;
        carregaLista();
    }

    protected void carregaDadosShared()
    {
        //  ** Carrega do Shared Preferences    **
        SharedPreferences settings = this.getSharedPreferences(PREF_NOME,0);
        usuarioLogado = new Usuario();
        usuarioLogado.setId(Integer.parseInt(settings.getString("id","")));
        usuarioLogado.setNickpsn(settings.getString("nickpsn",""));

        setTitle(usuarioLogado.getNickpsn());
    }
}
