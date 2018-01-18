package com.example.andre.pibicapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;

public class PictureActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView pic;


    SurfaceView  cameraView,transparentView;

    SurfaceHolder holder,holderTransparent;

    Camera camera;

    private float RectLeft, RectTop,RectRight,RectBottom ;

    int  deviceHeight,deviceWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        /* ImageView onde foto tirada aparece */
        pic = (ImageView) findViewById(R.id.imageView2);

       // dispatchTakePictureIntent();
    //}

    cameraView = (SurfaceView)findViewById(R.id.CameraView);





    holder = cameraView.getHolder();

    holder.addCallback((SurfaceHolder.Callback) this);

    //holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    cameraView.setSecure(true);



    // Create second surface with another holder (holderTransparent)

    transparentView = (SurfaceView)findViewById(R.id.TransparentView);

    holderTransparent = transparentView.getHolder();

    holderTransparent.addCallback((SurfaceHolder.Callback) this);

    holderTransparent.setFormat(PixelFormat.TRANSLUCENT);

    transparentView.setZOrderMediaOverlay(true);

    //getting the device heigth and width

    deviceWidth=getScreenWidth();

    deviceHeight=getScreenHeight();
        //dispatchTakePictureIntent();

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

        RectLeft = 150;

        RectTop = 300 ;

        RectRight = 550;

        RectBottom =RectTop+ 500;

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



        refreshCamera(); //call method for refress camera

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

        camera.release(); //for release a camera

    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String path = "sdcard/camera_app/cam_image.jpg";
        pic.setImageDrawable(Drawable.createFromPath(path));
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
