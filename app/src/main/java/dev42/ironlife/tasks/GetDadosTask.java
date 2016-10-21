package dev42.ironlife.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.util.HashMap;

import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.webservice.WebClient;

/**
 * Created by Fernando on 12/10/2016.
 */

public class GetDadosTask extends AsyncTask<Object, Object, String> {
    RetornoDelegate delegate;
    String url, tipo;
    HashMap<String, String> postDataParams;

    public GetDadosTask(RetornoDelegate delegate, String url, HashMap<String, String> postDataParams, String tipo ){
        this.delegate = delegate;
        this.url = url;
        this.tipo = tipo;
        this.postDataParams = postDataParams;
    }

    @Override
    protected String doInBackground(Object... params) {
        //SystemClock.sleep(7000);

        String resposta = null;
        WebClient webclient;
        try{
            switch (tipo){
                case "GET":
                    webclient = new WebClient(url);
                    resposta = webclient.getJson();
                    break;

                case "POST":
                    webclient = new WebClient(url, postDataParams);
                    resposta = webclient.postJson();
                    break;
            }

        }catch (Exception ex){
            return null;
        }

        return resposta;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String retorno) {
        if(retorno == null){
            delegate.LidaComErro("Erro AssyncTask");
        }else
        {
            delegate.LidaComRetorno(retorno);
        }
    }
}
