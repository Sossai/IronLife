package dev42.ironlife.converters;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev42.ironlife.model.Evento;
import dev42.ironlife.model.TutorialYoutube;

/**
 * Created by Fernando on 12/10/2016.
 */

public class TutorialYoutubeConverter {

    public List<TutorialYoutube> converte(String json){

        List<TutorialYoutube> listTutorialYoutube= new ArrayList<>();
        TutorialYoutube tutorialYoutube;

        try{
            JSONArray js = new JSONArray(json);
            for(int i = 0; i < js.length();i++)
            {
                JSONObject jsonEvento = js.getJSONObject(i);

                tutorialYoutube = new TutorialYoutube();
                tutorialYoutube.setId(Integer.parseInt(jsonEvento.getString("id")));
                tutorialYoutube.setTitulo(jsonEvento.getString("titulo"));
                tutorialYoutube.setLink(jsonEvento.getString("link"));
                tutorialYoutube.setImagem(jsonEvento.getString("imagem"));

                listTutorialYoutube.add(tutorialYoutube);
            }
            return listTutorialYoutube;
        }catch (Exception ex){
            Log.e("TutorialYoutubeConv", ex.getMessage());
            return null;
        }
    }
}
