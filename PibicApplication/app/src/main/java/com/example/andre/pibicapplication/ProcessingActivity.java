package com.example.andre.pibicapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProcessingActivity extends AppCompatActivity {

    String path = "sdcard/camera_app/cam_image.jpg";
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

         /* ImageView onde foto tirada aparece */
        pic = (ImageView) findViewById(R.id.imageView2);
        pic.setImageDrawable(Drawable.createFromPath(path));

        sendToServer();

        try {
            callExe();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendToServer(){

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        String json_response = "";

        try {
            URL url = new URL("https://example.com/api_endpoint");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(encoded);
            writer.flush();
            writer.close();
            os.close();

            Log.d("Auth", conn.getResponseCode() + "");

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String text = "";

            while ((text = br.readLine()) != null) {
                json_response += text;
            }
            conn.disconnect();
        } catch (IOException e) {
            Log.d(getClass().getName(), "" + e.getMessage());
        }

        System.out.println(json_response);
    }

    private void callExe() throws IOException {}
}
