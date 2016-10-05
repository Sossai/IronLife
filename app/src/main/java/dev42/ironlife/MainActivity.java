package dev42.ironlife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText login = (EditText)findViewById(R.id.login);
        EditText senha = (EditText)findViewById(R.id.senha);

//        login.getBackground().setColorFilter();
    }
}
