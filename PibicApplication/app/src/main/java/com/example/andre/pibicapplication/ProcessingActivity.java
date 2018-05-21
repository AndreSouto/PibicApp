package com.example.andre.pibicapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProcessingActivity extends AppCompatActivity {

    String path = "sdcard/camera_app/cam_image.jpg";
    ImageView pic;
    TextView tx3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);


         /* ImageView onde foto tirada aparece */
        pic = (ImageView) findViewById(R.id.imageView2);
        pic.setImageDrawable(Drawable.createFromPath(path));

        tx3 = (TextView) findViewById(R.id.textView3);

        try {
            sendToServer();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            callExe();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendToServer() throws JSONException, IOException {

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        String json_response = "";


        JSONObject postData = new JSONObject();
        postData.put("foto", "data:image/JPEG;base64," + encoded);

        new SendDeviceDetails().execute("http://172.20.10.5:8080/imagem", postData.toString());

    }

    private void callExe() throws IOException {}

    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";
            StringBuffer buffer = new StringBuffer();

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setChunkedStreamingMode(0);
                httpURLConnection.connect();

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(params[1]);
                wr.flush();
                wr.close();


                if (httpURLConnection.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + httpURLConnection.getErrorStream());
                }


                InputStream stream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));



                while((data = reader.readLine()) != null)
                {
                    buffer.append(data);
                }


            } catch (Exception e) {
               e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return String.valueOf(buffer);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            JSONObject mainObject = null;
            String name = "";

            try {
                mainObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                 name = mainObject.getString("resposta");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            tx3.setText(name);
            Toast.makeText(getApplicationContext(), name,
                    Toast.LENGTH_LONG).show();
        }
    }
}
