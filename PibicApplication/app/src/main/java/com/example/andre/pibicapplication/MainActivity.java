package com.example.andre.pibicapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* Leva para outra activity */
                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_menu);
            }
        });
    }
}
