package dev42.ironlife.webservice;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fernando on 12/10/2016.
 */

public class WebClient {
    private String urlString;
    private HashMap<String, String> postDataParams;

    public WebClient(String url){
        this.urlString = url;
    }
    public WebClient(String url, HashMap<String, String> postDataParams){
        this.urlString = url;
        this.postDataParams = postDataParams;
    }

    public String getJson(){

        String resposta = null;
        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json;charset=utf-8");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            StringBuffer response = new StringBuffer();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            resposta = response.toString();

            //Log.e("web_cliente resposta", resposta);
            return resposta;

        }catch (Exception ex){
            Log.e("Erro webclient getJson", ex.getMessage());
            return null;

        }
        //return resposta;
    }
    public String postJson(){

        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //connection.setRequestProperty("Accept", "application/json");
            //connection.setRequestProperty("Content-type", "application/json;charset=utf-8");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

//            Log.e("map_descricao",postDataParams.get("descricao"));

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            StringBuffer response = new StringBuffer();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            //Log.e("web_cliente resposta", resposta);
            return response.toString();

        }catch (Exception ex){
            Log.e("Erro webclient postJson", ex.getMessage());
            return null;

        }
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
