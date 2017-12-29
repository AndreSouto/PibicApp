package com.example.andre.pibicapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    ImageButton info,picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        info = (ImageButton) findViewById(R.id.imageButton2);
        picture = (ImageButton) findViewById(R.id.imageButton);

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
    }
}
