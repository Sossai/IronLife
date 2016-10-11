package dev42.ironlife.activities;

import android.content.Intent;
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

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.adapters.EventoAdapter;
import dev42.ironlife.model.Evento;

import static android.R.attr.format;

public class EventoActivity extends AppCompatActivity {

    List<Evento> eventos;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        eventos = simulaEventos();

        listView = (ListView)findViewById(R.id.listvieweventos);
        EventoAdapter adapter = new EventoAdapter(eventos, this);
        listView.setAdapter(adapter);

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

    }

    private List<Evento> simulaEventos(){
        List<Evento> listEventos = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        String hora = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        String data = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        Evento evento = new Evento();
        evento.setId(1);
        evento.setTitulo("Ecolinha 1");
        evento.setDataInicio(data);
        evento.setHoraInicio(hora);
        evento.setDataEncerramento(data);
        evento.setHoraEncerramento(hora);
        evento.setResponsavel("Sossai");
        listEventos.add(evento);

        evento = new Evento();
        evento.setId(2);
        evento.setTitulo("Ecolinha 2");
        evento.setDataInicio(data);
        evento.setHoraInicio(hora+1);
        evento.setDataEncerramento(data);
        evento.setHoraEncerramento(hora);
        evento.setResponsavel("SrMassacre");
        evento.setUsuarioRegistrado(true);

        listEventos.add(evento);

        evento = new Evento();
        evento.setId(3);
        evento.setTitulo("Raid Oficial");
        evento.setDataInicio(data);
        evento.setHoraInicio(hora+2);
        evento.setDataEncerramento(data);
        evento.setHoraEncerramento(hora);
        evento.setResponsavel("diegotx9EUA");
        listEventos.add(evento);

        evento = new Evento();
        evento.setId(4);
        evento.setTitulo("Raid Oficial 2");
        evento.setDataInicio(data);
        evento.setHoraInicio(hora+2);
        evento.setDataEncerramento(data);
        evento.setHoraEncerramento(hora);
        evento.setResponsavel("diegotx9EUA");
        listEventos.add(evento);

        return listEventos;
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
}
