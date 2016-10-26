package dev42.ironlife.converters;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev42.ironlife.model.Evento;
import dev42.ironlife.model.EventoUsuariosView;
import dev42.ironlife.model.Usuario;

/**
 * Created by Fernando on 12/10/2016.
 */

public class EventoUsuariosViewConverter {

    public List<EventoUsuariosView> converte(String json){

        List<EventoUsuariosView> eventoUsuariosViews = new ArrayList<>();
        EventoUsuariosView eventoUsuariosView = new EventoUsuariosView();
        Usuario usuario;
        boolean flagAdd = false;
        try{
            JSONArray js = new JSONArray(json);
            for(int i = 0; i < js.length();i++)
            {
                JSONObject jsonEvento = js.getJSONObject(i);
                usuario = new Usuario();
                usuario.setId(Integer.parseInt(jsonEvento.getString("id")));
                usuario.setNickpsn(jsonEvento.getString("nickpsn"));

                Log.e("usuvconve", jsonEvento.getString("nickpsn") );

                if(i %2 == 0){
                    eventoUsuariosView = new EventoUsuariosView();
                    eventoUsuariosView.setUsuarioPar(usuario);
                    eventoUsuariosView.setUsuarioImpar(null);
                }
                else {
                    eventoUsuariosView.setUsuarioImpar(usuario);
                    flagAdd = true;
                }
                //  **  Adiciona na lista sempre que montar o par ou acabar a lista **
                if(flagAdd || (i +1) == js.length())
                    eventoUsuariosViews.add(eventoUsuariosView);

            }
            return eventoUsuariosViews;
        }catch (Exception ex){
            Log.e("EventoUsuariosViewC", ex.getMessage());
            return null;
        }
    }
}
