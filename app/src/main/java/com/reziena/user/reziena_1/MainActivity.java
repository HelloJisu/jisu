package com.reziena.user.reziena_1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TextureView mCameraTextureView;
    private Preview mPreview;
    public static Activity mainnactivity;
    public Context CONTEXT;

    View capure;
    private ProgressBar mProgressBar;
    Activity mainActivity = this;
    public native void Detect();

    private static final String TAG = "MAINACTIVITY";

    static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        capure=(View)findViewById(R.id.capture);
        mProgressBar = findViewById(R.id.progress1);
        mainnactivity=MainActivity.this;
        mProgressBar.setVisibility(View.GONE);
        mainnactivity=MainActivity.this;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.capture:
                        intent = new Intent(getApplicationContext(), WrinkleResultActivity.class);
                        overridePendingTransition(0,0);
                        startActivity(intent);

                }
            }
        };

        capure.setOnClickListener(onClickListener);
        mCameraTextureView = (TextureView) findViewById(R.id.textureView);
        mPreview = new Preview(this, mCameraTextureView);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            mCameraTextureView = findViewById(R.id.textureView);
                            mPreview = new Preview(mainActivity, mCameraTextureView);
                            Log.e(TAG,"mPreview set");
                        } else {
                            Toast.makeText(this,"Should have camera permission to run", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreview.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.onPause();
    }
}