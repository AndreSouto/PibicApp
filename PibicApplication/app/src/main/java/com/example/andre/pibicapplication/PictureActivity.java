package com.example.andre.pibicapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class PictureActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        /* ImageView onde foto tirada aparece */
        pic = (ImageView) findViewById(R.id.imageView2);

        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pic.setImageBitmap(imageBitmap);
        }
    }

}
