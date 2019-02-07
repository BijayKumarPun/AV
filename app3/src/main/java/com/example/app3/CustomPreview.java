package com.example.app3;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CustomPreview extends SurfaceView implements SurfaceHolder.Callback {
    android.hardware.Camera camera;
    SurfaceHolder surfaceHolder;


    public CustomPreview(Context context, android.hardware.Camera camera) {
        super(context);
        this.camera = camera;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

    }

    public CustomPreview(Context context) {
        super(context);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        try {
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(holder);

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
        surfaceHolder.removeCallback(this);

        camera.stopPreview();
        camera.release();
    }
}
