package dev42.ironlife.converters;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev42.ironlife.model.Evento;
import dev42.ironlife.model.EventoUsuariosView;
import dev42.ironlife.model.Usuario;
import dev42.ironlife.model.UsuarioLogadoBung;

/**
 * Created by Fernando on 12/10/2016.
 */

public class EventoUsuariosViewConverter {

//    private Context context = null;
    public List<UsuarioLogadoBung> converte(String json){

        List<UsuarioLogadoBung> listaUsuarioLogadoBung = new ArrayList<>();
        EventoUsuariosView eventoUsuariosView = new EventoUsuariosView();
//        UsuarioLogadoBung usuarioLogadoBung = new UsuarioLogadoBung(context);
//        Usuario usuario;
        Context context = null;
        boolean flagAdd = false;
        try{
            JSONArray js = new JSONArray(json);
            for(int i = 0; i < js.length();i++)
            {

                JSONObject jsonEvento = js.getJSONObject(i);
                UsuarioLogadoBung usuarioLogadoBung = new UsuarioLogadoBung(context);
                usuarioLogadoBung.setMembershipId(jsonEvento.getString("membershipid"));
                usuarioLogadoBung.setDisplayName(jsonEvento.getString("displayname"));

                listaUsuarioLogadoBung.add(usuarioLogadoBung);

//                usuario = new Usuario();
//                usuario.setId(Integer.parseInt(jsonEvento.getString("id")));
//                usuario.setNickpsn(jsonEvento.getString("nickpsn"));
/*
                if(i %2 == 0){
                    eventoUsuariosView = new EventoUsuariosView();
                    eventoUsuariosView.setUsuarioPar(usuarioLogadoBung);
                    eventoUsuariosView.setUsuarioImpar(null);

//                    Log.e("Par",jsonEvento.getString("nickpsn") );
                }
                else {
                    eventoUsuariosView.setUsuarioImpar(usuarioLogadoBung);
//                    Log.e("IMPAR",jsonEvento.getString("nickpsn") );
                    flagAdd = true;
                }
                //  **  Adiciona na lista sempre que montar o par ou acabar a lista **
                if(flagAdd || (i +1) == js.length())
                    usuarioLogadoBungs.add(eventoUsuariosView);*/

            }
            return listaUsuarioLogadoBung;
        }catch (Exception ex){
            Log.e("EventoUsuariosViewC", ex.getMessage());
            return null;
        }
    }
}

/*
public class EventoUsuariosViewConverter {

    public List<EventoUsuariosView> converte(String json){

        List<EventoUsuariosView> eventoUsuariosViews = new ArrayList<>();
        EventoUsuariosView eventoUsuariosView = new EventoUsuariosView();
//        Usuario usuario;
        Context context = null;
        boolean flagAdd = false;
        try{
            JSONArray js = new JSONArray(json);
            for(int i = 0; i < js.length();i++)
            {

                JSONObject jsonEvento = js.getJSONObject(i);
                UsuarioLogadoBung usuarioLogadoBung = new UsuarioLogadoBung(context);
                usuarioLogadoBung.setMembershipId(jsonEvento.getString("membershipid"));
                usuarioLogadoBung.setDisplayName(jsonEvento.getString("displayname"));

//                usuario = new Usuario();
//                usuario.setId(Integer.parseInt(jsonEvento.getString("id")));
//                usuario.setNickpsn(jsonEvento.getString("nickpsn"));

                if(i %2 == 0){
                    eventoUsuariosView = new EventoUsuariosView();
                    eventoUsuariosView.setUsuarioPar(usuarioLogadoBung);
                    eventoUsuariosView.setUsuarioImpar(null);

//                    Log.e("Par",jsonEvento.getString("nickpsn") );
                }
                else {
                    eventoUsuariosView.setUsuarioImpar(usuarioLogadoBung);
//                    Log.e("IMPAR",jsonEvento.getString("nickpsn") );
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
*/