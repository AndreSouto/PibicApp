package com.example.andre.pibicapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    Button info,picture,tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        info = (Button) findViewById(R.id.button2);
        picture = (Button) findViewById(R.id.button3);
        tutorial = (Button) findViewById(R.id.button4);

        /* Botao de informacoes */
        info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* Leva para outra activity */
                Intent intent = new Intent(getApplicationContext(),InfoActivity.class);
                startActivity(intent);
            }
        });

        /* Botao para tirar foto */
        picture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* Leva para outra activity */
                Intent intent = new Intent(getApplicationContext(),PictureActivity.class);
                startActivity(intent);
            }
        });

        /* Botao para tutorial de como usar */
        tutorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* Leva para outra activity */
                Intent intent = new Intent(getApplicationContext(),TutorialActivity.class);
                startActivity(intent);
            }
        });
    }
}
