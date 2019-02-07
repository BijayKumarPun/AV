package com.example.homay.vision;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CamPreview extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    Camera camera;

    public CamPreview(Context context) {
        super(context);

    }
    public CamPreview(Context context, Camera camera) {

        super(context);
this.camera = camera;
surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {


            Log.i("CamPreview", "surfaceCreated: "+camera);
                camera.setPreviewDisplay(holder);
                camera.startPreview();



            } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


        try {
            camera.stopPreview();
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {


    }
}
