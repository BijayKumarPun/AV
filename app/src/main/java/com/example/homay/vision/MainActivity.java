package com.example.homay.vision;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "MainActivity";
    ImageView previewClick;
    FrameLayout frameLayoutContainer;
    Camera camera;
    CamPreview camPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");

        //instantiation
        init();


        previewClick.setOnClickListener(this);
        openCamera();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");


        if (camera == null) {

            openCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

       // camera.release();
        //camPreview.getHolder().removeCallback(camPreview);  //remove callback to the holder

        //camera = null;
        Log.i(TAG, "onPause: ");


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: ");
    }
    
    

    private void init() {
        previewClick = findViewById(R.id.preview_click_);
        frameLayoutContainer = findViewById(R.id.preview_container_);
    }

    private void openCamera() {
        camera = null;
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

            camera = Camera.open();
            getCameraParameters(camera);


            camera.setDisplayOrientation(90);

        }

        camPreview = new CamPreview(this, camera);
        frameLayoutContainer.addView(camPreview);

    }

    private void getCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        Log.i(TAG, "getCameraParameters: Supported picture format" + parameters.getSupportedPictureFormats());
        int n = Camera.getNumberOfCameras();
        Log.i(TAG, "openCamera: No. of Camera" + n);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.preview_click_:
                takePicture();
                break;
        }
    }

    private void takePicture() {

    }
}
