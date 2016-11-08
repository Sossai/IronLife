package dev42.ironlife.converters;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev42.ironlife.model.Evento;
import dev42.ironlife.model.TipoEvento;

/**
 * Created by Fernando on 17/10/2016.
 */

public class TipoEventoConverter {

    public List<TipoEvento> converte(String json){

        List<TipoEvento> listTipoEventos = new ArrayList<>();
        TipoEvento tipoEvento;
        try{
            JSONArray js = new JSONArray(json);
            for(int i = 0; i < js.length();i++)
            {
                JSONObject jsonEvento = js.getJSONObject(i);
                tipoEvento = new TipoEvento();
                tipoEvento.setId(Integer.parseInt(jsonEvento.getString("id")));
                tipoEvento.setDescricao(jsonEvento.getString("descricao"));
                tipoEvento.setImagem(jsonEvento.getString("imagem"));

                listTipoEventos.add(tipoEvento);
            }
            return listTipoEventos;
        }catch (Exception ex){
            Log.e("TipoEventoConeverter", ex.getMessage());
            return null;
        }
    }
}
