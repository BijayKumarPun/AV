package com.example.app3;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private String TAG = "MainActivity";
    FrameLayout frameLayout;
    Camera camera;
    CustomPreview customPreview;
    Button buttonSnap, buttonRecord;
    ImageView imageView;
    boolean RECORD_MODE = true;

    //vide recording
    MediaRecorder mediaRecorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.viewContainer);
        buttonSnap = findViewById(R.id.button_snap_);

        buttonRecord = findViewById(R.id.button_record_);
        mediaRecorder = new MediaRecorder();

        buttonRecord.setOnClickListener(this);
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

            case R.id.button_record_:


                recordVideo(RECORD_MODE);

                break;
        }
    }

    private void recordVideo(Boolean state) {

        if (state) {
            RECORD_MODE = false;

            camera.unlock();
            mediaRecorder.setCamera(camera);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

            mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
            File outPutFile = getFileName("VIDEO");

            if (outPutFile == null) return;

            mediaRecorder.setOutputFile(outPutFile.toString());
            mediaRecorder.setPreviewDisplay(customPreview.getHolder().getSurface());
        //    mediaRecorder.setVideoSize(500, 50);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            RECORD_MODE = true;
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();


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

        //save image to the directory

        File file = getFileName("IMAGE");
        if (file == null) return;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            fileOutputStream.close();

            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, new String[]{"image/jpeg"}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private File getFileName(String TYPE) {

        Log.i(TAG, "getFileName: ");

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.i(TAG, "getFileName: inside mounted");

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MeroApp");
            if (!file.exists()) {

                file.mkdirs();
            }
            if (file.exists()) {
                Log.i(TAG, "getFileName: File exists");
                if (TYPE.equals("IMAGE")) {
                    Log.i(TAG, "getFileName: inside IMAGE type");
                    return new File(file.getPath() + File.separator + "randomized.jpg");
                } else {
                    Log.i(TAG, "getFileName: inside VIDEO type");
                    return new File(file.getPath() + File.separator + "myvideo.mp4");
                }
            }
            Log.i(TAG, "getFileName: file still doesn't exist");

        }
        return null;
    }


}
