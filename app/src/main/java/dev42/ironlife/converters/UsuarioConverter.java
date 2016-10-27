package dev42.ironlife.converters;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev42.ironlife.model.Evento;
import dev42.ironlife.model.Usuario;

/**
 * Created by Fernando on 12/10/2016.
 */

public class UsuarioConverter {

    public Usuario converte(String json){

        Usuario usuario = null;
        try{
            JSONArray js = new JSONArray(json);
            for(int i = 0; i < js.length();i++)
            {
                JSONObject jsonEvento = js.getJSONObject(i);
                usuario = new Usuario();
                usuario.setId(Integer.parseInt(jsonEvento.getString("id")));
                usuario.setNickpsn(jsonEvento.getString("nickpsn"));
                usuario.setNome(jsonEvento.getString("nome"));
                usuario.setEmail(jsonEvento.getString("email"));
                usuario.setIdtipousuario(Integer.parseInt(jsonEvento.getString("idtipousuario")));
            }
            return usuario;
        }catch (Exception ex){
            Log.e("EventoConeverter", ex.getMessage());
            return null;
        }
    }
}
