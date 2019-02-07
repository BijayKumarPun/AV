package com.example.app3;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.login.LoginException;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private String TAG = "MainActivity";
    FrameLayout frameLayout;
    Camera camera;
    CustomPreview customPreview;
    Button buttonSnap;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.viewContainer);
        buttonSnap = findViewById(R.id.button_snap_);
        buttonSnap.setOnClickListener(this);
        imageView = findViewById(R.id.imageContainer);
        imageView.setOnClickListener(this);
        initCamera();



    }

    private void initCamera() {

        camera = getCamera();
        customPreview = new CustomPreview(this, camera);
        frameLayout.addView(customPreview);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (camera == null) {

            initCamera();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        camera = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Camera getCamera() {
        camera = null;
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            camera = Camera.open();
            return camera;
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_snap_:
                takeSnapshot();
                break;
            case R.id.imageContainer:
                imageView.setRotation(imageView.getRotation() + 90);
                break;
        }
    }

    private void takeSnapshot() {
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                saveImageToStorage(data);
            }
        });

    }

    private void saveImageToStorage(byte[] data) {
     //save
    }



}
