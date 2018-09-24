package com.example.andre.pibicapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InfoActivity extends AppCompatActivity {

    Button seeExampleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        seeExampleButton = (Button) findViewById(R.id.example_button);

        /* Figura ilustrativa */
        seeExampleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* Leva para outra activity */
                Intent intent = new Intent(getApplicationContext(),PopUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
