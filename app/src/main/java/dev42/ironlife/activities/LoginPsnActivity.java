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

import java.util.HashMap;

import dev42.ironlife.R;
import dev42.ironlife.converters.PegaDadosJson;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.UsuarioLogadoBung;
import dev42.ironlife.tasks.GetDadosTask;

public class LoginPsnActivity extends AppCompatActivity implements RetornoDelegate{

    private WebView webView;
    private final String urlLogin = "https://auth.api.sonyentertainmentnetwork.com/2.0/oauth/authorize?response_type=code&client_id=78420c74-1fdf-4575-b43f-eb94c7d770bf&redirect_uri=https%3a%2f%2fwww.bungie.net%2fen%2fUser%2fSignIn%2fPsnid&scope=psn:s2s&request_locale=en&state=8212071061023503780";
    final String apiKey = "e129b13b149b4ef3ae55a1d1709f49aa";
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

        /*
        String urlGet = "https://www.bungie.net/Platform/User/GetBungieAccount/4611686018428679528/2/";

        HashMap<String, String> postDataParams;
        postDataParams = new HashMap<String, String>();;

        postDataParams.put("X-API-Key", apiKey);
        passo = 3;

        GetDadosTask task = new GetDadosTask(delegate, urlGet, postDataParams, "GETLOGINAPI" );
        task.execute();
*/




/*
        //String urlGet = getString(R.string.url_bungie) + "Destiny/SearchDestinyPlayer/2/Sossai";
        String urlGet = "https://www.bungie.net/Platform/Destiny/SearchDestinyPlayer/2/Sossai/";
        Log.e("urlGet", urlGet);

        HashMap<String, String> postDataParams;
        postDataParams = new HashMap<String, String>();;
        //postDataParams.put("Cookie", cookies);
        //postDataParams.put("x-csrf", xcsrf);
        postDataParams.put("X-API-Key", apiKey);
        passo = 2;

        GetDadosTask task = new GetDadosTask(delegate, urlGet, postDataParams, "GETLOGINAPI" );
        task.execute();
*/




/*
        String json = "[{\"Response\":{\"user\":{\"membershipId\":\"4545054\",\"uniqueName\":\"4545054\",\"displayName\":\"Sossai\",\"profilePicture\":70546,\"profileTheme\":37,\"userTitle\":0,\"successMessageFlags\":\"0\",\"isDeleted\":false,\"about\":\"\",\"firstAccess\":\"2014-06-10T11:33:32.155Z\",\"lastUpdate\":\"2016-10-11T12:20:01.07Z\",\"context\":{\"isFollowing\":false,\"ignoreStatus\":{\"isIgnored\":false,\"ignoreFlags\":0}},\"psnDisplayName\":\"Sossai\",\"showActivity\":true,\"locale\":\"pt-br\",\"localeInheritDefault\":true,\"showGroupMessaging\":true,\"profilePicturePath\":\"/img/profile/avatars/bungieday_24.jpg\",\"profileThemeName\":\"lingling\",\"userTitleDisplay\":\"Newbie\",\"statusText\":\"\",\"statusDate\":\"0001-01-01T00:00:00Z\"},\"email\":\"ferpa@sigmanet.com.br\",\"emailStatus\":9,\"emailUsage\":\"31\",\"psnId\":\"Sossai\",\"userAcls\":[],\"showGamertagPublic\":false,\"showFacebookPublic\":false,\"showPsnPublic\":true,\"publicCredentialTypes\":[2],\"isThemeLight\":true,\"adultMode\":false,\"userResearchStatusFlags\":\"0\",\"privacy\":0,\"hideDestinyData\":false,\"pmToastsEnabled\":true},\"ErrorCode\":1,\"ThrottleSeconds\":0,\"ErrorStatus\":\"Success\",\"Message\":\"Ok\",\"MessageData\":{}}]";
        json = json.replace("\\", "");
        PegaDadosJson pegaDadosJson = new PegaDadosJson(json);
        String ret = pegaDadosJson.valor("displayName");
*/
        //Log.e("ret", ret);

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
                    postDataParams = new HashMap<String, String>();;
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

                    Log.e("Sucesso ret passo 1", retorno);

                    urlGet = getString(R.string.url_bungie) + "Destiny/SearchDestinyPlayer/2/"+usuarioLogadoBung.getDisplayName().trim() + '/';
                    passo = 2;

                    GetDadosTask task = new GetDadosTask(delegate, urlGet, postDataParams, "GETLOGINAPI" );
                    task.execute();
                }else
                    retornaFalha();


                //Log.e("Nome", ret);
                break;
            case 2: //  **  Pegou o MembershipId
                Log.e("Sucesso ret passo 2", retorno);
                pegaDadosJson = new PegaDadosJson(retorno);


                if(pegaDadosJson.valor("membershipId","") != null){
                    usuarioLogadoBung.setMembershipId(pegaDadosJson.valor("membershipId",""));

                    Log.e("Sucesso ret passo 2-1", usuarioLogadoBung.getMembershipId());

                    urlGet = getString(R.string.url_bungie) + "User/GetBungieAccount/"+usuarioLogadoBung.getMembershipId() + "/2/";
                    passo = 3;

                    GetDadosTask task = new GetDadosTask(delegate, urlGet, postDataParams, "GETLOGINAPI" );
                    task.execute();
                }else
                    retornaFalha();

                break;
            case 3: //  **  Pegou dados dos Grupo

                Log.e("Sucesso ret passo 3", retorno);
                pegaDadosJson = new PegaDadosJson(retorno);

                if(pegaDadosJson.valor("groupId","") != null){
                    Log.e("groupid", pegaDadosJson.valor("groupId",""));
                    usuarioLogadoBung.setGroupId(pegaDadosJson.valor("groupId",""));
                    if(pegaDadosJson.valor("groupName",usuarioLogadoBung.getGroupId()) != null){
                        usuarioLogadoBung.setGroupName(pegaDadosJson.valor("groupName",usuarioLogadoBung.getGroupId()));

                        //  **  Peguei todos os dados necessários, salvo nas sharede preferences o usuario valido   **
                        if(validaCla(usuarioLogadoBung)){
                            usuarioLogadoBung.setDadosShared();
                            Intent intent = new Intent(LoginPsnActivity.this, EventoActivity.class);
                            startActivity(intent);
                            Toast.makeText(this, "Seja bem vindo Guardião.", Toast.LENGTH_LONG).show();
                            limpaCookies();
                        }else{
                            Toast.makeText(this, "Lamento Guardião, apenas membros do clã permitido IRON LIFE são permitidos.", Toast.LENGTH_LONG).show();
                            retornaFalha();
                        }
                    }else
                        retornaFalha();
                        //Log.e("x","erro2");

                }else
                    retornaFalha();
                    //Log.e("x","erro");

                break;
        }


    }

    private void retornaFalha(){
        limpaCookies();
        //usuarioLogadoBung.deleteDadosShared();
        Toast.makeText(this, "Lamento Guadião, não foi possível validar seus dados.", Toast.LENGTH_LONG).show();
        finish();
        Intent intent = new Intent(LoginPsnActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void LidaComErro(String erro) {
        Log.e("Erro", erro);
        finish();
        retornaFalha();
//        Intent intent = new Intent(LoginPsnActivity.this, MainActivity.class);
//        startActivity(intent);
    }

    private void limpaCookies(){
        if(Build.VERSION.SDK_INT >= 21){
            CookieManager.getInstance().removeAllCookies(null);
        }else{
            CookieManager.getInstance().removeAllCookie();
        }
/*        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(callback);
        cookieManager.setAcceptCookie(false);

        WebView webview = new WebView(this);
        WebSettings ws = webview.getSettings();
        ws.setSaveFormData(false);
        ws.setSavePassword(false); // Not needed for API level 18 or greater (deprecated)*/

    }

    private boolean validaCla(UsuarioLogadoBung usuarioLogadoBung){
        return usuarioLogadoBung.getGroupName().equals("IRON LIFE");
    }

}
