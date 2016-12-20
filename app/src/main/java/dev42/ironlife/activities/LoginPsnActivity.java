package dev42.ironlife.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.util.HashMap;

import dev42.ironlife.R;
import dev42.ironlife.converters.PegaDadosJson;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.UsuarioLogadoBung;
import dev42.ironlife.tasks.GetDadosTask;

public class LoginPsnActivity extends AppCompatActivity implements RetornoDelegate{

    private WebView webView;
    private final String urlLogin = "https://auth.api.sonyentertainmentnetwork.com/2.0/oauth/authorize?response_type=code&client_id=78420c74-1fdf-4575-b43f-eb94c7d770bf&redirect_uri=https%3a%2f%2fwww.bungie.net%2fen%2fUser%2fSignIn%2fPsnid&scope=psn:s2s&request_locale=en&state=8212071061023503780";
    private final String apiKey = "e129b13b149b4ef3ae55a1d1709f49aa";
    //final String apiKey = getString(R.string.APIKey);
    private String cookies = null, xcsrf = null;
    private boolean sucesso = false;
    private RetornoDelegate delegate = this;
    private Activity activity = this;
    private Integer passo;

    private HashMap<String, String> postDataParams;
    private UsuarioLogadoBung usuarioLogadoBung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_psn);

        usuarioLogadoBung = new UsuarioLogadoBung(this);
        carregaWebView();
    }

    private void carregaWebView()
    {
        CookieManager.getInstance().setAcceptCookie(true);
        webView = (WebView) findViewById(R.id.loginwebview);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                //  **  Pego os cookies de retorno  **
                cookies = CookieManager.getInstance().getCookie(url);
                sucesso = false;

                //  **  Separo as strings dos cookies cook1=a ; cook2=b   **
                String[] keyValuePairs = cookies.split(";");
                String chave, valor;

                //  **  Se não encontrou o atk nao consegui logar ou retornar   **
                for (String cook : keyValuePairs){
                    chave =  cook.split("=")[0].trim();
                    valor =  cook.split("=")[1].trim();
                    //Log.e("chave/valor", chave + '=' + valor);

                    switch (chave){
                        case "bungled":
                            xcsrf = valor;
                            break;
                        case "bungleatk":
                            sucesso = true;
                    }
                }

                //Log.e("Cook",cookies);
                if(sucesso){

                    //final HashMap<String, String> postDataParams;   //  **  Estes parametros não vão mudar  **
                    //https://www.bungie.net/Platform/User/GetBungieNetUser/
                    String urlGet = getString(R.string.url_bungie) + "User/GetBungieNetUser/";
                    postDataParams = new HashMap<String, String>();
                    postDataParams.put("Cookie", cookies);
                    postDataParams.put("x-csrf", xcsrf);
                    postDataParams.put("X-API-Key", apiKey);

                    passo = 1;
                    GetDadosTask task = new GetDadosTask(delegate, urlGet, postDataParams, "GETLOGINAPI" );
                    task.execute();
                }else {
                    //  ** Nao faço nada até que seja possiverl pegar algum retorno valido no callback do assynctask    **
                    ///Toast.makeText(activity, "Lamento Guardião.", Toast.LENGTH_LONG).show();
                    //finish();
                    Log.e("Falha cookie", "Falha cookie");
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urlLogin);
    }

    @Override
    public void LidaComRetorno(String retorno) {
        String urlGet;
        PegaDadosJson pegaDadosJson;

        //HashMap<String, String> postDataParams;
        switch (passo){
            case 1: //  **  Pegou o nome do usuario


                pegaDadosJson = new PegaDadosJson(retorno);

                if(pegaDadosJson.valor("displayName","") != null)
                {
                    usuarioLogadoBung.setDisplayName(pegaDadosJson.valor("displayName",""));

//                    Log.e("Sucesso ret passo 1", retorno);

                    urlGet = getString(R.string.url_bungie) + "Destiny/SearchDestinyPlayer/2/"+usuarioLogadoBung.getDisplayName().trim() + '/';
                    passo = 2;

                    GetDadosTask task = new GetDadosTask(delegate, urlGet, postDataParams, "GETLOGINAPI" );
                    task.execute();
                }else
                    retornaFalha("Captura dos dados do usuário na Bungie.");


                //Log.e("Nome", ret);
                break;
            case 2: //  **  Pegou o MembershipId
                //Log.e("Sucesso ret passo 2", retorno);
                pegaDadosJson = new PegaDadosJson(retorno);

                if(pegaDadosJson.valor("membershipId","") != null){
                    usuarioLogadoBung.setMembershipId(pegaDadosJson.valor("membershipId",""));

                    Log.e("Sucesso ret passo 2-1", usuarioLogadoBung.getMembershipId());

                    urlGet = getString(R.string.url_bungie) + "User/GetBungieAccount/"+usuarioLogadoBung.getMembershipId() + "/2/";
                    passo = 3;

                    GetDadosTask task = new GetDadosTask(delegate, urlGet, postDataParams, "GETLOGINAPI" );
                    task.execute();
                }else
                    retornaFalha("Captura do Id do usuário na Bungie:"+ usuarioLogadoBung.getDisplayName().trim());

                break;
            case 3: //  **  Pegou dados dos Grupo

//                Log.e("Sucesso ret passo 3", retorno);
                pegaDadosJson = new PegaDadosJson(retorno);

                if(pegaDadosJson.valor("groupId","") != null){
                    //Log.e("groupid", pegaDadosJson.valor("groupId",""));
                    usuarioLogadoBung.setGroupId(pegaDadosJson.valor("groupId",""));
                    if(pegaDadosJson.valor("groupName",usuarioLogadoBung.getGroupId()) != null){
                        usuarioLogadoBung.setGroupName(pegaDadosJson.valor("groupName",usuarioLogadoBung.getGroupId()));

                        //  **  Peguei todos os dados necessários, salvo nas sharede preferences o usuario valido   **
                        if(validaCla(usuarioLogadoBung)){
                            cadastraUsuarioBungie();

                            limpaCookies();
                            finish();


/*                            usuarioLogadoBung.setDadosShared();

                            Intent intent = new Intent(LoginPsnActivity.this, EventoActivity.class);
                            startActivity(intent);
                            Toast.makeText(this, "Seja bem vindo Guardião.", Toast.LENGTH_LONG).show();
                            limpaCookies();*/
                        }else{
                            Toast.makeText(this, "Lamento Guardião, apenas membros do clã IRON LIFE são permitidos.", Toast.LENGTH_LONG).show();
                            retornaFalha("");
                        }
                    }else
                        retornaFalha("Captura nome do Clã:" + usuarioLogadoBung.getMembershipId());
                        //Log.e("x","erro2");
                }else
                    retornaFalha("Captura Id do Clã: " + usuarioLogadoBung.getMembershipId());
                    //Log.e("x","erro");

                break;
            case 4:
//                Log.e("Retorno 4", retorno);
                if(retorno.trim().equals("SUCESSO")){
                    usuarioLogadoBung.setDadosShared();

                    Intent intent = new Intent(LoginPsnActivity.this, EventoActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Seja bem vindo Guardião.", Toast.LENGTH_LONG).show();
//                    limpaCookies();
                }else
                    retornaFalha("Salvar dados no servidor :" + usuarioLogadoBung.getMembershipId());

                break;
        }
    }

    @Override
    public void LidaComErro(String erro) {
        Log.e("Erro" + passo, erro);
        finish();
        retornaFalha("Falha retorno.");
//        Intent intent = new Intent(LoginPsnActivity.this, MainActivity.class);
//        startActivity(intent);
    }

    private void cadastraUsuarioBungie(){
        passo = 4;
        String url = getString(R.string.url_add_usuario_bungie);
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("membershipid",usuarioLogadoBung.getMembershipId());
        postDataParams.put("displayname",usuarioLogadoBung.getDisplayName());

        Log.e("Passo 4", usuarioLogadoBung.getMembershipId() + "|" + usuarioLogadoBung.getDisplayName());
        GetDadosTask task = new GetDadosTask(this, url, postDataParams, "POST");
        task.execute();
    }

    private void retornaFalha(String falha){
        limpaCookies();
        //usuarioLogadoBung.deleteDadosShared();
        Toast.makeText(this, "Lamento Guadião, não foi possível validar seus dados.", Toast.LENGTH_LONG).show();
        FirebaseCrash.report(new Exception(falha));
        finish();
        Intent intent = new Intent(LoginPsnActivity.this, MainActivity.class);
        startActivity(intent);
    }



    private void limpaCookies(){
        if(Build.VERSION.SDK_INT >= 21){
            CookieManager.getInstance().removeAllCookies(null);
        }else{
            CookieManager.getInstance().removeAllCookie();
        }
    }

    private boolean validaCla(UsuarioLogadoBung usuarioLogadoBung){
        return usuarioLogadoBung.getGroupName().equals("IRON LIFE");
    }

}
