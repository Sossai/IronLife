package dev42.ironlife.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dev42.ironlife.R;

public class AddEventoActivity extends AppCompatActivity{
    final Context context = this;
    EditText dataInicio, dataFim, horaInicio, horaFim, tipo;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evento);

        dataInicio = (EditText)findViewById(R.id.datainicio);
        dataFim = (EditText)findViewById(R.id.datafim);
        horaInicio = (EditText)findViewById(R.id.horainicio);
        horaFim = (EditText)findViewById(R.id.horafim);
        tipo = (EditText)findViewById(R.id.tipo);

//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
//        adapter.add("Raid");
//        adapter.add("Escolinha");

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
                String[] stringArray = new String[] { "Escolinha", "Raid" };
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                modeList.setAdapter(modeAdapter);

                builder.setView(modeList);
                final Dialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tipo.setText(modeList.getItemAtPosition(position).toString());
                        dialog.dismiss();
                    }
                });
            }
        });

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
                        dataInicio.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        break;
                    case "FIM":
                        dataFim.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
                                horaInicio.setText(hourOfDay + ":" + minute);
                                break;
                            case "FIM":
                                horaFim.setText(hourOfDay + ":" + minute);
                                break;
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.setCancelable(true);
        timePickerDialog.setCanceledOnTouchOutside(true);
        timePickerDialog.show();
    }
}
