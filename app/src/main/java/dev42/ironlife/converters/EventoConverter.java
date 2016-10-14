package dev42.ironlife.converters;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev42.ironlife.model.Evento;

/**
 * Created by Fernando on 12/10/2016.
 */

public class EventoConverter {

    public List<Evento> converte(String json){

        List<Evento> eventos = new ArrayList<>();
        Evento evento;
        try{
            JSONArray js = new JSONArray(json);
            for(int i = 0; i < js.length();i++)
            {
                JSONObject jsonEvento = js.getJSONObject(i);
                evento = new Evento();
                evento.setId(Integer.parseInt(jsonEvento.getString("id")));
                evento.setTitulo(jsonEvento.getString("descricao"));
                evento.setDataInicio(jsonEvento.getString("datainicio"));
                evento.setHoraInicio(jsonEvento.getString("horainicio"));
                evento.setDataEncerramento(jsonEvento.getString("dataencerramento"));
                evento.setHoraEncerramento(jsonEvento.getString("horaencerramento"));
                evento.setResponsavel(jsonEvento.getString("nomeresponsavel"));
                evento.setIdResponsavel(Integer.parseInt(jsonEvento.getString("idusuarioresponsavel")));
                evento.setUsuarioRegistrado(Boolean.valueOf(jsonEvento.getString("usuarioRegistrado")));
                evento.setImagem(jsonEvento.getString("tipoeventoimagem"));

                eventos.add(evento);
            }
            return eventos;
        }catch (Exception ex){
            Log.e("EventoConeverter", ex.getMessage());
            return null;
        }
    }
}
