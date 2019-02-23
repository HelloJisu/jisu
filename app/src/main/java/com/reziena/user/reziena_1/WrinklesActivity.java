package com.reziena.user.reziena_1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class WrinklesActivity extends AppCompatActivity {

    String permissionstring;
    ImageButton imageButton;
    HomeActivity homeactivity = (HomeActivity)HomeActivity.homeactivity;
    TextView ready;
    static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrinkles);


        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.0f;
        getWindow().setAttributes(lpWindow);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 100%
        int height = (int) (display.getHeight() * 0.9);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        imageButton = findViewById(R.id.imageButton);
        ready = findViewById(R.id.ready);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ready:

                        int permissionCamera = ContextCompat.checkSelfPermission(WrinklesActivity.this, Manifest.permission.CAMERA);
                        if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions((Activity) WrinklesActivity.this, new String[]{Manifest.permission.CAMERA}, MainActivity.REQUEST_CAMERA);
                            ActivityCompat.requestPermissions((Activity) WrinklesActivity.this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, MainActivity.REQUEST_CAMERA);
                            Log.e("야야야!", "퍼미션 허용~");
                            permissionstring = "true";
                        } else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                    case R.id.imageButton:
                        homeactivity.dashback.setImageResource(0);
                        finish();
                        break;
                }
            }
        };
        imageButton.setOnClickListener(onClickListener);
        ready.setOnClickListener(onClickListener);
    }

    public boolean dispatchTouchEvent(MotionEvent ev){
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);
        if(!dialogBounds.contains((int)ev.getX(),(int) ev.getY())){
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        homeactivity.backgroundimg.setImageResource(0);
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
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Log.e("허용","퍼미션허용했긔");
                        } else {
                            Toast.makeText(this,"Should have camera permission to run", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
        }
    }
}