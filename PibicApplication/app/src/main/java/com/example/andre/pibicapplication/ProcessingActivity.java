package com.example.andre.pibicapplication;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ProcessingActivity extends AppCompatActivity {

    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

         /* ImageView onde foto tirada aparece */
        pic = (ImageView) findViewById(R.id.imageView2);

        String path = "sdcard/camera_app/cam_image.jpg";
        pic.setImageDrawable(Drawable.createFromPath(path));

    }
}
