package dev42.ironlife.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.adapters.EventoAdapter;
import dev42.ironlife.converters.EventoConverter;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.Evento;
import dev42.ironlife.tasks.GetDadosTask;

import static android.R.attr.format;

public class EventoActivity extends AppCompatActivity implements RetornoDelegate, SwipeRefreshLayout.OnRefreshListener {

    private List<Evento> eventos;
    private ListView listView;
    private ProgressDialog progressDialog;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        listView = (ListView)findViewById(R.id.listvieweventos);
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
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        Evento eventoSelecionado = (Evento)listView.getItemAtPosition(info.position);

        MenuItem Inscrever = menu.add("Inscrever");
        MenuItem Cancelar = menu.add("Cancelar Inscrição");
        MenuItem Sobre = menu.add("Sobre");

    }

    public void carregaLista(){
        progressDialog = ProgressDialog.show(this, "", "Contactando o servidor, por favor, aguarde alguns instantes.", true, false);
        String url = getString(R.string.url_lista_eventos);

        url += 1;   //  **  Id do usuario logado    **
        GetDadosTask task = new GetDadosTask(this, url, null, "GET");
        task.execute();
    }

    @Override
    public void LidaComRetorno(String retorno) {
        progressDialog.dismiss();
        EventoConverter converter = new EventoConverter();
        eventos = converter.converte(retorno);

        if(eventos != null){
            EventoAdapter adapter = new EventoAdapter(eventos, this);
            listView.setAdapter(adapter);
        }else
            Toast.makeText(this, "Não foram encontrados Eventos", Toast.LENGTH_LONG).show();
    }

    @Override
    public void LidaComErro(String erro) {
        progressDialog.dismiss();
        Toast.makeText(this, "Erro ao buscar eventos.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        carregaLista();
        this.swipe.setRefreshing(false);
        this.swipe.clearAnimation();

    }
}
