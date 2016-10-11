package dev42.ironlife.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dev42.ironlife.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText login = (EditText)findViewById(R.id.login);
        EditText senha = (EditText)findViewById(R.id.senha);
        Button loginBtn = (Button)findViewById(R.id.btnlogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                Intent intent = new Intent(MainActivity.this, EventoActivity.class);
                startActivity(intent);
            }
        });
    }
}
