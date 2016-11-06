package dev42.ironlife.converters;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import dev42.ironlife.model.Usuario;

/**
 * Created by Fernando on 04/11/2016.
 */

public class PegaDadosJson {
    private String json;
    /*
        tipo = 0 -> Array
        tipo = 1 -> Objeto
    */

    public PegaDadosJson(String json){
        //this.json = '[' + json.trim() + ']';
        this.json = json.trim();
        //Log.e("this.json", this.json);
    }
    public String valor(String chave, String param){
        String valor = null;
        JSONArray ja;
        try {
            JSONObject jsonEvento = new JSONObject(this.json);
            if(jsonEvento.getString("ErrorStatus").equals("Success"))
            {
                JSONObject itemDetails = null;
                switch (chave){
                    case "displayName":
                        itemDetails = jsonEvento.getJSONObject("Response");
                        itemDetails = itemDetails.getJSONObject("user");
                        valor = itemDetails.getString("displayName");
                        break;

                    case "membershipId":
                        valor = jsonEvento.getString("Response");
                        ja = new JSONArray(valor);
                        for(int i = 0; i < ja.length();i++)
                        {
                            itemDetails = ja.getJSONObject(i);
                            valor = itemDetails.getString("membershipId");
                        }
                        break;

                    case "groupId":
                        itemDetails = jsonEvento.getJSONObject("Response");
                        valor = itemDetails.getString("clans");

                        ja = new JSONArray(valor);
                        for(int i = 0; i < ja.length();i++)
                        {
                            itemDetails = ja.getJSONObject(i);
                            valor = itemDetails.getString("groupId");
                        }
                        break;

                    case "groupName":
                        itemDetails = jsonEvento.getJSONObject("Response");
                        itemDetails = itemDetails.getJSONObject("relatedGroups");
                        itemDetails = itemDetails.getJSONObject(param);
                        valor = itemDetails.getString("name");

                }
                return valor;
            }else {
                Log.e("Erro Json", "Falha");
                return null;
            }

        }catch (Exception ex){
            return null;
        }
/*
        String valor = "";
        try{
            JSONArray js = new JSONArray(json);
            JSONObject jsonEvento;
            JSONObject itemDetails = null;
            for(int i = 0; i < js.length();i++)
            {
                jsonEvento = js.getJSONObject(i);

                switch (chave){
                    case "displayName":
                        itemDetails = jsonEvento.getJSONObject("Response");
                        itemDetails = itemDetails.getJSONObject("user");
                        valor = itemDetails.getString("displayName");
                        break;

                    case "membershipId":
                        itemDetails = jsonEvento.getJSONObject("Response");

                        Log.e("itemDetails",itemDetails.toString());
                        //valor = itemDetails.getString("membershipId");
                        break;
                }
            }

            Log.e("valor", valor);
            return valor;
        }catch (Exception ex){
            Log.e("tagxxx"," Erroxxx ", ex);
            return null;
        }

*/


/*
        HashMap<String, String> retorno = new HashMap<>();

        try
        {
            JSONArray js = new JSONArray(json);
            for(String dado : lDados)
            {
                for(int i = 0; i < js.length();i++)
                {
                    JSONObject jsonEvento = js.getJSONObject(i);
                    retorno.put(dado, jsonEvento.getString(dado) );
                }
            }
            return retorno;

        }catch (Exception ex)
        {
            return null;
        }
*/
/*
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
            return null;
        }catch (Exception ex){
            Log.e("EventoConeverter", ex.getMessage());
            return null;
        }
        */

    }



}
