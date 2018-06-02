package com.example.andre.pibicapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    String PATHNAME = "sdcard/camera_app/cam_image.jpg";
    private float RectLeft, RectTop,RectRight,RectBottom;
    int  deviceHeight,deviceWidth;
    ImageButton pictureButton;
    SurfaceView  cameraView,transparentView;
    SurfaceHolder holder,holderTransparent;
    Camera camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        /* Surface View onde consta o retangulo desenhado */
        transparentView = (SurfaceView)findViewById(R.id.TransparentView);
        /* Surface View onde consta a camera */
        cameraView = (SurfaceView)findViewById(R.id.CameraView);
        /* Button para retirar foto */
        pictureButton = (ImageButton) findViewById(R.id.pictureButton);


        /* JPEG callback para criar pasta camera_app e salvar a foto tirada */
        final Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {

                File folder = new File("sdcard/camera_app");

                if(!folder.exists()){

                    folder.mkdir();
                }

                FileOutputStream outStream = null;

                try {
                    /* Write to SD Card */
                    outStream = new FileOutputStream(String.format(PATHNAME));
                    outStream.write(data);
                    outStream.close();

                } catch (FileNotFoundException e) {

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                } finally {}
            }
        };

         /* Botao para fotografar */
        pictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                File file = getFile();
                camera.takePicture(null, null, jpegCallback);

                /* Tempo para jpegCallback salvar a imagem */
                for(int j = 0; j < 100000000; j++){}

                /* Leva para outra activity */
                Intent intent = new Intent(getApplicationContext(),ProcessingActivity.class);
                startActivity(intent);
            }
        });


        holder = cameraView.getHolder();
        holder.addCallback((SurfaceHolder.Callback) this);

        cameraView.setSecure(true);

        /* Create second surface with another holder (holderTransparent) */
        holderTransparent = transparentView.getHolder();
        holderTransparent.addCallback((SurfaceHolder.Callback) this);
        holderTransparent.setFormat(PixelFormat.TRANSLUCENT);
        transparentView.setZOrderMediaOverlay(true);

        /* getting the device heigth and width */
        deviceWidth=getScreenWidth();
        deviceHeight=getScreenHeight();

    }

    public static int getScreenWidth() {

        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {

        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    private void Draw()
    {
        Canvas canvas = holderTransparent.lockCanvas(null);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);

        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();

        double RectLeft = screenWidth/4;
        double RectTop = screenHeight/4;
        double RectRight = screenWidth*0.75;
        //double RectBottom = RectTop + 500;
        double RectBottom = screenHeight*0.75;

        Rect rec=new Rect((int) RectLeft,(int) RectTop,(int) RectRight,(int) RectBottom);
        canvas.drawRect(rec,paint);

        holderTransparent.unlockCanvasAndPost(canvas);
    }

    public void surfaceCreated(SurfaceHolder holder) {

        try {
            synchronized (holder)

            {Draw();}   //call a draw method

            camera = Camera.open(); //open a camera
        }

        catch (Exception e) {
            Log.i("Exception", e.toString());

            return;
        }

        Camera.Parameters param;
        param = camera.getParameters();

        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        if(display.getRotation() == Surface.ROTATION_0)
        {
            camera.setDisplayOrientation(90);
        }
        if (param.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }

        camera.setParameters(param);

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }

        catch (Exception e) {

            return;
        }
    }

    @Override

    protected void onDestroy() {

        super.onDestroy();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        refreshCamera();
    }

    public void refreshCamera() {

        if (holder.getSurface() == null) {

            return;
        }

        try {

            camera.stopPreview();
        }



        catch (Exception e) {

        }

        try {

            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }

        catch (Exception e) {

        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        camera.release();
    }

    private File getFile(){

        File folder = new File("sdcard/camera_app");

        if(!folder.exists()){

            folder.mkdir();
        }

        File image_file = new File(folder,"cam_image.jpg");
        return image_file;
    }
}
