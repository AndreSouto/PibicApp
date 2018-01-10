package com.example.andre.pibicapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InfoActivity extends AppCompatActivity {

    ImageButton larrow,rarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        larrow = (ImageButton) findViewById(R.id.imageButton2);
        rarrow = (ImageButton) findViewById(R.id.imageButton3);

        /* Menu */
        larrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* Leva para outra activity */
                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(intent);
            }
        });

        /* Figura ilustrativa */
        rarrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* Leva para outra activity */
                Intent intent = new Intent(getApplicationContext(),PopUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
