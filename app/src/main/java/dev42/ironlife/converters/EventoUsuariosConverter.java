package dev42.ironlife.converters;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev42.ironlife.model.Evento;
import dev42.ironlife.model.EventoUsuarios;
import dev42.ironlife.model.Usuario;

/**
 * Created by Fernando on 12/10/2016.
 */

public class EventoUsuariosConverter {

    public EventoUsuarios converte(String json){

        List<Evento> eventos = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();
        EventoUsuarios eventoUsuarios = null;
        Usuario usuario = null;

        try{
            JSONArray js = new JSONArray(json);

            for(int i = 0; i < js.length();i++)
            {
                JSONObject jsonEvento = js.getJSONObject(i);

                //  **  1º registro é o evento  **
                //  **  2º próximos são usuarios    **
                eventoUsuarios = new EventoUsuarios();
                if(i == 0){
                    eventoUsuarios.setId(Integer.parseInt(jsonEvento.getString("id")));
                    eventoUsuarios.setTitulo(jsonEvento.getString("descricao"));
                    eventoUsuarios.setDataInicio(jsonEvento.getString("datainicio"));
                    eventoUsuarios.setHoraInicio(jsonEvento.getString("horainicio"));
                    eventoUsuarios.setDataEncerramento(jsonEvento.getString("dataencerramento"));
                    eventoUsuarios.setHoraEncerramento(jsonEvento.getString("horaencerramento"));
                    eventoUsuarios.setResponsavel(jsonEvento.getString("nomeresponsavel"));
                    eventoUsuarios.setIdResponsavel(Integer.parseInt(jsonEvento.getString("idusuarioresponsavel")));
                    eventoUsuarios.setUsuarioRegistrado(Boolean.valueOf(jsonEvento.getString("usuarioRegistrado")));
                    eventoUsuarios.setImagem(jsonEvento.getString("tipoeventoimagem"));
                }else{
                    usuario = new Usuario();
                    usuario.setId(Integer.parseInt(jsonEvento.getString("id")));
                    usuario.setNickpsn(jsonEvento.getString("nickpsn"));
                    usuario.setNome(jsonEvento.getString("nome"));
                    usuario.setEmail(jsonEvento.getString("email"));
                    usuario.setIdtipousuario(Integer.parseInt(jsonEvento.getString("idtipousuario")));

                    usuarios.add(usuario);
                }
            }
            eventoUsuarios.setUsuarios(usuarios);

            return eventoUsuarios;
        }catch (Exception ex){
            Log.e("EventoUsuariosConvt", ex.getMessage());
            return null;
        }
    }
}
