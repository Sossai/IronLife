package dev42.ironlife.services;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

import dev42.ironlife.R;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.UsuarioLogadoBung;
import dev42.ironlife.tasks.GetDadosTask;

/**
 * Created by Fernando on 10/11/2016.
 */

public class IronLifeInstanceIDService extends FirebaseInstanceIdService /*implements RetornoDelegate*/ {
    private static final String TAG = "MyFirebaseInsIDService";

    @Override
    public void onTokenRefresh() {

        //Get updated token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d(TAG, "New Token: " + refreshedToken);
        Log.e("RefreshToken", refreshedToken);

        if(refreshedToken !=null)
            salvaTokenLocal(refreshedToken);

        //atualizaTokenServer(refreshedToken);

        //You can save the token into third party server to do anything you want
    }

    //  **  Toquem unico por aparelho. Salvo para enviar posteriormente caso não esteja logado  **
    protected void salvaTokenLocal(String token){
        SharedPreferences settings = this.getSharedPreferences("TokenFcm",0);
        SharedPreferences.Editor editor = settings.edit();
        //**  Salva no SharedPreferences  **
        editor.putString("token", token);
        editor.commit();
    }
/*
    protected void atualizaTokenServer(String refreshedToken )
    {
        UsuarioLogadoBung usuarioLogadoBung = new UsuarioLogadoBung(this);
        usuarioLogadoBung.getDadosShared();

        if(!usuarioLogadoBung.getMembershipId().isEmpty())
        {
            String url = getString(R.string.url_add_usuario_bungie);
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("membershipid",usuarioLogadoBung.getMembershipId());
            postDataParams.put("displayname",usuarioLogadoBung.getDisplayName());
            postDataParams.put("fcmtoken",refreshedToken);

            GetDadosTask task = new GetDadosTask(this, url, postDataParams, "POST");
            task.execute();
        }
    }

    @Override
    public void LidaComErro(String erro) {
        Log.e("RefToken_Sucesso", erro);
    }

    @Override
    public void LidaComRetorno(String retorno) {
        Log.e("RefToken_Sucesso", retorno);
    }
    */
}
