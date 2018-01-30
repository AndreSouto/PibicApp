package com.example.andre.pibicapplication;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

        try {
            callExe();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void callExe() throws IOException {

        //String myExec = "/data/data/APPNAME/FILENAME";
        //Process process = Runtime.getRuntime().exec(myExec);
        //DataOutputStream os = new DataOutputStream(process.getOutputStream());
        //DataInputStream osRes = new DataInputStream(process.getInputStream());
    }
}
