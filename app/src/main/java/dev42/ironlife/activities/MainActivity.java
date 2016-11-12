package dev42.ironlife.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import dev42.ironlife.R;
import dev42.ironlife.converters.PegaDadosJson;
import dev42.ironlife.converters.UsuarioConverter;
import dev42.ironlife.interfaces.RetornoDelegate;
import dev42.ironlife.model.Usuario;
import dev42.ironlife.model.UsuarioLogadoBung;
import dev42.ironlife.tasks.GetDadosTask;

public class MainActivity extends AppCompatActivity implements RetornoDelegate {
//public class MainActivity extends AppCompatActivity {

    private EditText login,senha;
    private Usuario usuarioLogado;
    private final String PREF_NOME = "UsuarioShared";
    private UsuarioLogadoBung usuarioLogadoBung;
    private final String apiKey = "e129b13b149b4ef3ae55a1d1709f49aa";

    private Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = (Button)findViewById(R.id.btnlogin);

        usuarioLogadoBung = new UsuarioLogadoBung(this);
        usuarioLogadoBung.getDadosShared();

        //  **  Já tenho o membershipid da bungie   **
        if(!usuarioLogadoBung.getMembershipId().isEmpty()){

            //  **  Revalida se pertence ao cla **
            revalidaCla();

//            Intent intent = new Intent(MainActivity.this, EventoActivity.class);
//            startActivity(intent);
        }

/*        login = (EditText)findViewById(R.id.login);
        senha = (EditText)findViewById(R.id.senha);
        Button loginBtn = (Button)findViewById(R.id.btnlogin);

        carregaDadosShared();
*/
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginPsnActivity.class);
                startActivity(intent);
            }
        });

        //  **  Token parar envio do fcm    **
        FirebaseInstanceId.getInstance().getToken();
        //Log.d("EventoActivity", "Token: " + token);

    }

    protected void revalidaCla(){

        String urlGet = getString(R.string.url_bungie) + "User/GetBungieAccount/"+usuarioLogadoBung.getMembershipId() + "/2/";
        HashMap<String, String> postDataParams = new HashMap<String, String>();
        postDataParams.put("X-API-Key", apiKey);
        GetDadosTask task = new GetDadosTask(this, urlGet, postDataParams, "GETLOGINAPI" );
        task.execute();
    }

    @Override
    public void LidaComRetorno(String retorno) {
        PegaDadosJson pegaDadosJson = new PegaDadosJson(retorno);

        //  **  Se não é mesmo id do grupo que conseguiu logar da 1º vez saiu do cla    **
        if(pegaDadosJson.valor("groupId","") != null){

            if(pegaDadosJson.valor("groupId","").equals(usuarioLogadoBung.getGroupId())){
                Intent intent = new Intent(MainActivity.this, EventoActivity.class);
                startActivity(intent);
            }else
            {
                Toast.makeText(this, "Lamento Guardião, apenas membros do clã IRON LIFE são permitidos.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void LidaComErro(String erro) {
        Log.e("Erro Main", erro);
        Toast.makeText(this, "Lamento Guardião, não foi possível validar seu clã.", Toast.LENGTH_LONG).show();
    }



/*   protected void validaUsuario(){
        String url = getString(R.string.url_validar_usuario);
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("email",login.getText().toString());
        postDataParams.put("senha",senha.getText().toString());
        postDataParams.put("tipo","1"); //  ** Validação com retorno de usuario **

        GetDadosTask task = new GetDadosTask(this, url, postDataParams, "POST");
        task.execute();
    }

    @Override
    public void LidaComRetorno(String retorno) {
        UsuarioConverter converter = new UsuarioConverter();
        usuarioLogado = converter.converte(retorno);
        if(usuarioLogado != null){

            salvaDadosShared();

            Intent intent = new Intent(MainActivity.this, EventoActivity.class);
            startActivity(intent);
        }else
            Toast.makeText(this, "Lamento Guardião, dados inválidos!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void LidaComErro(String erro) {
        Log.e("Login", erro);
        Toast.makeText(this, "Lamento Guardião, não foi possível conectar ao servidor.", Toast.LENGTH_LONG).show();
    }

    protected void salvaDadosShared()
    {
        SharedPreferences settings = this.getSharedPreferences(PREF_NOME,0);
        SharedPreferences.Editor editor = settings.edit();
        /*//**  Salva no SharedPreferences  **
        editor.putString("id", usuarioLogado.getId().toString());
        editor.putString("nickpsn", usuarioLogado.getNickpsn());
        editor.putString("nome", usuarioLogado.getNome());
        editor.putString("email", usuarioLogado.getEmail());
        editor.putString("idtipousuario", usuarioLogado.getIdtipousuario().toString());
        editor.putString("senha", senha.getText().toString());
        editor.commit();
    }

    protected void carregaDadosShared()
    {
        //  ** Carrega do Shared Preferences    **
        SharedPreferences settings = this.getSharedPreferences(PREF_NOME,0);
        login.setText(settings.getString("email",""));
        senha.setText(settings.getString("senha",""));
    }*/
}
