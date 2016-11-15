package dev42.ironlife.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dev42.ironlife.R;
import dev42.ironlife.adapters.EventoAdapter;
import dev42.ironlife.adapters.TipoEventoAdapter;
import dev42.ironlife.converters.TipoEventoConverter;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.Evento;
import dev42.ironlife.model.TipoEvento;
import dev42.ironlife.model.UsuarioLogadoBung;
import dev42.ironlife.tasks.GetDadosTask;

import static android.R.attr.format;

public class AddEventoActivity extends AppCompatActivity implements RetornoDelegate {
    private final Context context = this;
    private final Activity activity = this;
    private EditText titulo, dataInicio, dataFim, horaInicio, horaFim, tipo, idtipo;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button idconfirmar;
    private Integer tipoRetorno;
    private List<TipoEvento> listTipoEventos;
    private TipoEvento tipoEventoSelecionado;
    private ImageView imgtipo;
    private Integer idEvento = null;
    private View frameLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evento);

        //  **  Action Bar return   **
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        titulo = (EditText)findViewById(R.id.addtitulo);
        dataInicio = (EditText)findViewById(R.id.datainicio);
        dataFim = (EditText)findViewById(R.id.datafim);
        horaInicio = (EditText)findViewById(R.id.horainicio);
        horaFim = (EditText)findViewById(R.id.horafim);
        tipo = (EditText)findViewById(R.id.tipo);
        idconfirmar = (Button)findViewById(R.id.idconfirmar);
        idtipo = (EditText)findViewById(R.id.idtipo);
        imgtipo = (ImageView)findViewById(R.id.imagemtipo);

        frameLoad = (View)findViewById(R.id.frameload);


        //  **  Se for editar recebe os dados   **
        Evento eventoSelecionado;
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            eventoSelecionado = (Evento) bundle.getSerializable("eventoSelecionado");
            setDadosUpdate(eventoSelecionado);
//            Log.e("Evento", eventoSelecionado.getTitulo());
        }


        recuperarListaTipoEvento();

        dataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegaData("INICIO");
            }
        });

        dataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegaData("FIM");
            }
        });

        horaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegaHora("INICIO");
            }
        });

        horaFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegaHora("FIM");
            }
        });

        tipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Tipo");
                final ListView modeList = new ListView(context);
                TipoEventoAdapter adapter = new TipoEventoAdapter(listTipoEventos, activity);
                modeList.setAdapter(adapter);

                builder.setView(modeList);
                final Dialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //tipo.setText(modeList.getItemAtPosition(position).toString());
                        tipoEventoSelecionado = (TipoEvento) modeList.getItemAtPosition(position);
                        tipo.setText(tipoEventoSelecionado.getDescricao());

                        Picasso.with(activity)
                                .load(tipoEventoSelecionado.getImagem())
                                .into(imgtipo);
                        dialog.dismiss();
                    }
                });
            }
        });

        idconfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviaDados();
            }
        });
    }

    protected void recuperarListaTipoEvento(){
        tipoRetorno = 2;
        String url = getString(R.string.url_lista_tipo_evento);
        frameLoad.setVisibility(View.VISIBLE);
        GetDadosTask task = new GetDadosTask(this, url,null, "GET");
        task.execute();
    }

    protected void setDadosUpdate(Evento evento){
        titulo.setText(evento.getTitulo());
        dataInicio.setText(evento.getDataInicio());
        dataFim.setText(evento.getDataEncerramento());
        horaInicio.setText(evento.getHoraInicio());
        horaFim.setText(evento.getHoraEncerramento());
        tipo.setText(evento.getDescricaoTipoEvento());
        idtipo.setText(evento.getIdTipoEvento().toString());
        tipoEventoSelecionado = new TipoEvento();
        tipoEventoSelecionado.setId(evento.getIdTipoEvento());
        Picasso.with(activity)
                .load(evento.getImagem())
                .into(imgtipo);
        idEvento = evento.getId();
    }

    protected void enviaDados(){
        tipoRetorno = 1;

        if(dadosValidos()){

            UsuarioLogadoBung usuarioLogadoBung = new UsuarioLogadoBung(this);
            usuarioLogadoBung.getDadosShared();

            String url = getString(R.string.url_cria_evento);
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("descricao",titulo.getText().toString());
            postDataParams.put("datainicio",dataInicio.getText().toString());
            postDataParams.put("horainicio",horaInicio.getText().toString());
            postDataParams.put("dataencerramento",dataFim.getText().toString());
            postDataParams.put("horaencerramento",horaFim.getText().toString());
            postDataParams.put("idtipoevento",tipoEventoSelecionado.getId().toString());
            postDataParams.put("membershipid",usuarioLogadoBung.getMembershipId());
            postDataParams.put("displayname",usuarioLogadoBung.getDisplayName());

            //  *** Update  **
            if(idEvento != null)
                postDataParams.put("id",idEvento.toString());

            frameLoad.setVisibility(View.VISIBLE);
            GetDadosTask task = new GetDadosTask(this, url, postDataParams, "POST");
            task.execute();

        }
    }

    protected boolean dadosValidos(){
        Boolean dadosValidos = true;

        Integer diasMaxInicio = 5, diasMaxFim = 2;
        Date dtHoje = null;
        Date dtInicio = null;
        Date dtFim = null;
        Date dtMaxInicio = null;
        Date dtMaxFim = null;

        if(titulo.getText().toString().length() == 0 ){
            Toast.makeText(this, "Entre com um Título.", Toast.LENGTH_LONG).show();
            dadosValidos = false;
        }
        else if(dataInicio.getText().toString().length() == 0) {
            Toast.makeText(this, "Selecione uma data de início.", Toast.LENGTH_LONG).show();
            dadosValidos = false;
        }
        else if(horaInicio.getText().toString().length() == 0) {
            Toast.makeText(this, "Selecione uma hora de início.", Toast.LENGTH_LONG).show();
            dadosValidos = false;
        }
        else if(dataFim.getText().toString().length() == 0) {
            Toast.makeText(this, "Selecione uma data de encerramento.", Toast.LENGTH_LONG).show();
            dadosValidos = false;
        }
        else if(horaFim.getText().toString().length() == 0) {
            Toast.makeText(this, "Selecione uma hora de encerramento.", Toast.LENGTH_LONG).show();
            dadosValidos = false;
        }
        else if(horaFim.getText().toString().length() == 0) {
            Toast.makeText(this, "Selecione uma hora de encerramento.", Toast.LENGTH_LONG).show();
            dadosValidos = false;
        }
        else if(tipoEventoSelecionado == null) {
            Toast.makeText(this, "Selecione um tipo de evento.", Toast.LENGTH_LONG).show();
            dadosValidos = false;
        }else{
        //  ** Validando intervalo de dadtas    **
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, diasMaxInicio); // Adding 5 days
            String dtMaxInicioStr = sdf.format(c.getTime());

            String dtHojeStr = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            String strDtInicio = dataInicio.getText().toString();
            String strDtFim = dataFim.getText().toString();

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try {
                dtInicio = format.parse(strDtInicio);
                dtHoje = format.parse(dtHojeStr);
                dtFim = format.parse(strDtFim);
                dtMaxInicio = format.parse(dtMaxInicioStr);

                c.setTime(dtInicio);
                c.add(Calendar.DATE, diasMaxFim); // Adding 2 days
                String dtMaxFimStr = sdf.format(c.getTime());
                dtMaxFim = format.parse(dtMaxFimStr);

            }catch (Exception ex){}

        /*
            = 0 -> datas iguais
            < 0 -> data data menor
            > 0 -> data maior
        */
            // 1 **  Data Inicio maior que hoje
            if(dtInicio.compareTo(dtHoje) >= 0){
                // 2 **  Data Inicio menor que hoje + 5 dias
                if(dtInicio.compareTo(dtMaxInicio) > 0){
                    Toast.makeText(this, "O evento deve iniciar no máximo daqui a " + diasMaxInicio.toString().trim() + " dias.", Toast.LENGTH_LONG).show();
                    dadosValidos = false;
                }
                // 3 **  Data Final >= data inicio
                else if(dtFim.compareTo(dtInicio) >= 0 ){
                    // 4 **  Data Final <= data inicio + 2 dias
                    if(dtFim.compareTo(dtMaxFim) > 0 ){
                        Toast.makeText(this, "O evento deve encerrar no máximo " + diasMaxFim.toString().trim() + " dias após o inicio.", Toast.LENGTH_LONG).show();
                        dadosValidos = false;
                    }
                }else{
                    Toast.makeText(this, "Data final deve ser maior ou igual a data de inicio.", Toast.LENGTH_LONG).show();
                    dadosValidos = false;
                }
            }else{
                Toast.makeText(this, "Data inicial deve ser maior ou igual a hoje.", Toast.LENGTH_LONG).show();
                dadosValidos = false;
            }
        }


        return dadosValidos;


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
    public void LidaComErro(String erro) {
        frameLoad.setVisibility(View.GONE);
        Log.e("Erro Criar Evento", erro);

        switch (tipoRetorno){
            case 1:
                if(idEvento == null)
                    Toast.makeText( this, "Falha ao criar Evento.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText( this, "Falha ao editar Evento.", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText( this, "Falha ao recuperar Tipo de Evento.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void LidaComRetorno(String retorno) {
//        Log.e("Sucesso tipo",retorno);
        frameLoad.setVisibility(View.GONE);

        switch (tipoRetorno){
            case 1:
                if(retorno.trim().equals("SUCESSO")){
                    if(idEvento == null)
                        Toast.makeText( this, "Evento criado com sucesso!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText( this, "Evento editado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }else
                    if(idEvento == null)
                        Toast.makeText( this, "Falha ao criar Evento.", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText( this, "Falha ao editar Evento.", Toast.LENGTH_LONG).show();
                break;
            case 2:
                TipoEventoConverter converter = new TipoEventoConverter();
                listTipoEventos = converter.converte(retorno);
                break;
        }
    }

    private void pegaData(final String campo){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                switch (campo){
                    case "INICIO":
                        dataInicio.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        break;
                    case "FIM":
                        dataFim.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        break;
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setCancelable(true);
        datePickerDialog.setCanceledOnTouchOutside(true);
        datePickerDialog.show();
    }

    private void pegaHora(final String campo){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                switch (campo){
                    case "INICIO":
                        //horaInicio.setText(hourOfDay + ":" + minute);
                        horaInicio.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));

                        break;
                    case "FIM":
                        horaFim.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                        break;
                }
            }
        }, mHour, mMinute, false);
        timePickerDialog.setCancelable(true);
        timePickerDialog.setCanceledOnTouchOutside(true);
        timePickerDialog.show();
    }
}
