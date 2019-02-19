package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signin2Activity extends AppCompatActivity {
    TextView okay;
    private EditText name;
    private EditText email;
    RadioGroup gender;
    RadioButton genderresult;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    Button signin, signinfinish;
    private Uri mImageCaptureUri;
    private int id_view;
    private String absolutePath;
    CircleImageView profile;
    public static Activity skinhistoryactivity;
    HomeActivity homeactivity = (HomeActivity)HomeActivity.homeactivity;
    String namestring, emailstring, profileurl;
    private static final String DEFAULT_LOCAL = "Portugal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin2);
        skinhistoryactivity = Signin2Activity.this;

        Intent subintent = getIntent();

        namestring = subintent.getExtras().getString("name");
        emailstring = subintent.getExtras().getString("email");
        profileurl = subintent.getExtras().getString("profile");

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        profile = findViewById(R.id.signinprofile);
        signin = findViewById(R.id.signin);
        signinfinish = findViewById(R.id.signinfinish);
        gender = findViewById(R.id.radioGroup1);


        Spinner s = (Spinner)findViewById(R.id.spinner1);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        if(namestring!=null){
            name.setText(namestring);
        }
        if(emailstring!=null){
            email.setText(emailstring);
        }
        if(profile!=null){
            Glide.with(this).load(profileurl).into(profile);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.signinfinish:
                        Intent intent1 = new Intent(getApplicationContext(),LoginmainActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.signin:
                        genderresult=findViewById(gender.getCheckedRadioButtonId());
                        if(genderresult==null){
                            Toast toast = Toast.makeText(getApplicationContext(),"돌아가라",Toast.LENGTH_LONG);
                            toast.show();
                        }else{
                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                            intent.putExtra("name","skintypedialog");
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case R.id.signinprofile:
                        doTakeAlbumAction();
                        break;
                }
            }
        };
        signin.setOnClickListener(onClickListener);
        signinfinish.setOnClickListener(onClickListener);
        profile.setOnClickListener(onClickListener);
    }

    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode!=RESULT_OK)
            return;

        switch(requestCode)
        {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA:{
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/");

                intent.putExtra("outputX",200);
                intent.putExtra("outputY",200);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent,CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE:{
                if(resultCode!=RESULT_OK){
                    return;
                }
                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel"+System.currentTimeMillis()+".jpg";

                if(extras!=null){
                    Bitmap photo = extras.getParcelable("data");
                    //Glide.with(this).load(filePath).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(image);

                    profile.setImageBitmap(photo);

                    storeCropImage(photo,filePath);
                    absolutePath = filePath;
                    break;
                }
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists()){
                    f.delete();
                }
            }
        }
    }
    private void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_smartWheel = new File(dirPath);

        if(!directory_smartWheel.exists()){
            directory_smartWheel.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}