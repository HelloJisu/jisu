package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.renderscript.RenderScript;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reziena.user.reziena_1.utils.RSBlurProcessor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    Animation alphaback;
    RenderScript rs, rs2;
    Bitmap blurBitMap, blurBitMap2;
    private static Bitmap bitamp, bitamp2;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference wrinkle_txt, moisture_txt, skintype_txt, wrinklemain_txt, moisturemain_txt, crdata, cldata, urdata, uldata,filepathdata;
    String filepathstring;
    long crdataint, cldataint, urdataint, uldataint;
    public static Activity homeactivity;
    String wrinkle_string, moisture_string, skintype_string;
    RelativeLayout card, design_bottom_sheet, arrow;
    LinearLayout toolbar_dash,moisture,wrinkles,skin_type, toolbar,treatbtn, historyBtn, dashboard;
    LinearLayout home1,home2,home3,home4,home5,home8,home9;
    LinearLayout dash7,dash8,dash9;
    ImageView layer1, logo,backgroundimg,dashback;
    CircleImageView image, image_main;
    BottomSheetBehavior bottomSheetBehavior;
    TextView skintype_result, moisture_score, wrinkle_score, moisture_status, wrinkle_status, moisture_score_main, wrinkle_score_main, question,skintype_main;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mImageCaptureUri;
    private int id_view;
    private String absolutePath;
    String wrinklestringg;
    View screenshot, screenshotdash;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeactivity = HomeActivity.this;

        // 값 받아오기
        moisture = findViewById(R.id.moisture);
        wrinkles = findViewById(R.id.wrinkles);
        toolbar_dash = findViewById(R.id.toolbar_dash);
        skin_type = findViewById(R.id.skin_type);
        design_bottom_sheet = findViewById(R.id.design_bottom_sheet);
        dashboard = findViewById(R.id.dashboard);
        toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.image); // profile
        image_main = findViewById(R.id.image_main); // profile
        layer1 = findViewById(R.id.layer1);
        arrow = findViewById(R.id.arrow_dash);
        treatbtn = findViewById(R.id.treatBtn);
        skintype_result = findViewById(R.id.skintype_result);
        moisture_score = findViewById(R.id.moisture_score);
        wrinkle_score = findViewById(R.id.wrinkle_score);
        moisture_status = findViewById(R.id.moisture_status);
        wrinkle_status = findViewById(R.id.wrinkle_status);
        moisture_score_main = findViewById(R.id.moisture_result_main);
        wrinkle_score_main = findViewById(R.id.wrinkle_result_main);
        logo = findViewById(R.id.logo);
        question = findViewById(R.id.question);
        historyBtn = findViewById(R.id.historyBtn);
        ImageView monCheck, tueCheck, wedCheck, thuCheck, friCheck;
        monCheck = findViewById(R.id.monCheck);
        tueCheck = findViewById(R.id.tueCheck);
        wedCheck = findViewById(R.id.wedCheck);
        thuCheck = findViewById(R.id.thuCheck);
        friCheck = findViewById(R.id.friCheck);
        home1=findViewById(R.id.home1);
        home2=findViewById(R.id.home2);
        home3=findViewById(R.id.home3);
        home4=findViewById(R.id.home4);
        home5=findViewById(R.id.home5);
        home8=findViewById(R.id.home8);
        home9=findViewById(R.id.home9);
        dash8=findViewById(R.id.dash8);
        dash9=findViewById(R.id.dash9);
        backgroundimg=findViewById(R.id.backgroundimage);
        dashback=findViewById(R.id.dashback);
        skintype_main=findViewById(R.id.skintype_main);
        screenshot=findViewById(R.id.screenshot);
        screenshotdash=findViewById(R.id.screenshotdash);
        String dialogask;


// check
        // check
        //Calendar cal = Calendar.getInstance();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
        //cal.setTimeZone(time);
        int day = cal.get(Calendar.DAY_OF_WEEK);    // 1=일 2=월 3=화 4=수 5=목 6=금 7=토// 1=일 2=월 3=화 4=수 5=목 6=금 7=토
        switch (day) {
            case 2: // 월
                monCheck.setImageResource(R.drawable.ximg);
                tueCheck.setImageResource(R.drawable.noncheck);
                wedCheck.setImageResource(R.drawable.noncheck);
                thuCheck.setImageResource(R.drawable.noncheck);
                friCheck.setImageResource(R.drawable.noncheck);
                break;
            case 3: // 화
                monCheck.setImageResource(R.drawable.ximg);
                tueCheck.setImageResource(R.drawable.check);
                wedCheck.setImageResource(R.drawable.noncheck);
                thuCheck.setImageResource(R.drawable.noncheck);
                friCheck.setImageResource(R.drawable.noncheck);
                break;
            case 4: // 수
                monCheck.setImageResource(R.drawable.ximg);
                tueCheck.setImageResource(R.drawable.check);
                wedCheck.setImageResource(R.drawable.check);
                thuCheck.setImageResource(R.drawable.noncheck);
                friCheck.setImageResource(R.drawable.noncheck);
                break;
            case 5: // 목
                monCheck.setImageResource(R.drawable.ximg);
                tueCheck.setImageResource(R.drawable.check);
                wedCheck.setImageResource(R.drawable.check);
                thuCheck.setImageResource(R.drawable.check);
                friCheck.setImageResource(R.drawable.noncheck);
                break;
            case 6: // 금
                monCheck.setImageResource(R.drawable.ximg);
                tueCheck.setImageResource(R.drawable.check);
                wedCheck.setImageResource(R.drawable.check);
                thuCheck.setImageResource(R.drawable.check);
                friCheck.setImageResource(R.drawable.check);
                break;
        }

        Intent subintent = getIntent();

        dialogask = subintent.getExtras().getString("name");



        if(dialogask!=null){
        if(dialogask.equals("skintypedialog")){
            Intent intent = new Intent(getApplicationContext(), SkintypeAsk.class);
            overridePendingTransition(0,0);
            startActivity(intent);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    screenshot();
                }
            }, 20);
        }}

        // init the Bottom Sheet Behavior
        bottomSheetBehavior = BottomSheetBehavior.from(design_bottom_sheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // 시작할 때 DashBoard와 기계 이미지 안보이게 하기
        dashboard.setVisibility(View.INVISIBLE);
        layer1.setVisibility(View.INVISIBLE);


        // set hideable or not
        bottomSheetBehavior.setHideable(false);

        //animation
        final Animation scaletranslate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scaletranslate);
        final Animation alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_alpha);
        final Animation alpha2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_alpha2);
        final Animation scaletranslate2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scaletranslate2);
        alphaback = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_alpha_back);

        wrinkle_txt = databaseReference.child("result").child("winkle");
        moisture_txt = databaseReference.child("result").child("moisture");
        skintype_txt = databaseReference.child("result").child("skintype");
        wrinklemain_txt = databaseReference.child("result").child("winkle");
        moisturemain_txt = databaseReference.child("result").child("moisture");
        crdata = databaseReference.child("result").child("cheekright_data");
        cldata = databaseReference.child("result").child("cheekleft_data");
        urdata = databaseReference.child("result").child("underrigh_data");
        uldata = databaseReference.child("result").child("underleft_data");
        filepathdata = databaseReference.child("result").child("filepath");

        wrinklemain_txt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wrinklestringg=dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View view, int i) {
                dashboard.setVisibility(View.INVISIBLE);
                layer1.setVisibility(View.INVISIBLE);

                // Dash -> Home으로 Dragging 해도 화면 전환이 되지 않게 함
                if (i == 1) {      //STATE_DRAGGING
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

                // Dash 화면
                if (i == 3) {      //STATE_EXPANDED
                    dashboard.setVisibility(View.VISIBLE);
                    layer1.setVisibility(View.VISIBLE);
                }
            }

            // 슬라이드시 화면 보이게
            @Override
            public void onSlide(@NonNull View view, float v) {
            }

        });


        View.OnClickListener onClickListener = new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.treatBtn:
                        if(wrinklestringg.equals("-")){
                            intent = new Intent(getApplicationContext(), noActivity.class);
                            overridePendingTransition(0,0);
                            startActivity(intent);
                            new Handler().postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    screenshot();
                                }
                            }, 20);
                        }
                        else{
                            intent = new Intent(getApplicationContext(), LoadingActivity.class);
                            overridePendingTransition(0,0);
                            startActivity(intent);
                        }
                        break;
                    case R.id.moisture:
                        intent = new Intent(getApplicationContext(), MoistureActivity.class);
                        overridePendingTransition(0,0);
                        startActivity(intent);
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                screenshotdash();
                            }
                        }, 20);

                        break;
                    case R.id.wrinkles:
                        intent = new Intent(getApplicationContext(), WrinklesActivity.class);
                        overridePendingTransition(0,0);
                        startActivity(intent);
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                screenshotdash();
                            }
                        }, 20);
                        break;
                    case R.id.skin_type:
                        intent = new Intent(getApplicationContext(), SkintypeActivity.class);
                        overridePendingTransition(0,0);
                        startActivity(intent);
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                screenshotdash();
                            }
                        }, 20);
                        break;
                    case R.id.toolbar:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        dashboard.setVisibility(View.VISIBLE);
                        dashboard.startAnimation(alpha);
                        home1.setVisibility(View.INVISIBLE);
                        home2.setVisibility(View.INVISIBLE);
                        home3.setVisibility(View.INVISIBLE);
                        home4.setVisibility(View.INVISIBLE);
                        home5.setVisibility(View.INVISIBLE);
                        dash8.setVisibility(View.VISIBLE);
                        dash9.setVisibility(View.VISIBLE);
                        layer1.setVisibility(View.VISIBLE);
                        layer1.startAnimation(alpha);
                        toolbar.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.arrow_dash:
                    case R.id.toolbar_dash:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        dashboard.startAnimation(alpha2);
                        layer1.setVisibility(View.INVISIBLE);
                        home1.setVisibility(View.VISIBLE);
                        home2.setVisibility(View.VISIBLE);
                        home3.setVisibility(View.VISIBLE);
                        home4.setVisibility(View.VISIBLE);
                        home5.setVisibility(View.VISIBLE);
                        dash8.setVisibility(View.INVISIBLE);
                        dash9.setVisibility(View.INVISIBLE);
                        layer1.startAnimation(alpha2);
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.logo:
                        databaseReference.child("result").child("skintype").setValue("No data yet");
                        databaseReference.child("result").child("moisture").setValue("-");
                        databaseReference.child("result").child("winkle").setValue("-");
                        databaseReference.child("result").child("cheekright_data").setValue(0);
                        databaseReference.child("result").child("cheekleft_data").setValue(0);
                        databaseReference.child("result").child("underleft_data").setValue(0);
                        databaseReference.child("result").child("underrigh_data").setValue(0);
                        databaseReference.child("result").child("underrightstring").setValue("false");
                        databaseReference.child("result").child("underleftstring").setValue("false");
                        databaseReference.child("result").child("cheekrightstring").setValue("false");
                        databaseReference.child("result").child("cheekleftstring").setValue("false");
                        break;
                    case R.id.historyBtn:
                        intent = new Intent(getApplicationContext(), SkinhistoryActivity.class);
                        overridePendingTransition(0,0);
                        startActivity(intent);
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                screenshot();
                            }
                        }, 20);
                        break;
                    case R.id.image:
                        doTakeAlbumAction();
                }
            }
        };
        image.setOnClickListener(onClickListener);
        historyBtn.setOnClickListener(onClickListener);
        moisture.setOnClickListener(onClickListener);
        wrinkles.setOnClickListener(onClickListener);
        skin_type.setOnClickListener(onClickListener);
        toolbar.setOnClickListener(onClickListener);
        toolbar_dash.setOnClickListener(onClickListener);
        arrow.setOnClickListener(onClickListener);
        treatbtn.setOnClickListener(onClickListener);
        logo.setOnClickListener(onClickListener);
    }

//database
public void onStart() {
    super.onStart();
    wrinkle_txt.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            wrinkle_string=dataSnapshot.getValue(String.class);
            wrinkle_score.setText(wrinkle_string);

        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
    filepathdata.addValueEventListener(new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            filepathstring=dataSnapshot.getValue(String.class);
            if(filepathstring.equals(null)){
                image.setImageResource(R.drawable.ellipseprofile);
            }
            else{
                File imgFile = new  File(filepathstring);

                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    image.setImageBitmap(myBitmap);
                    image_main.setImageBitmap(myBitmap);
                }
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
    wrinklemain_txt.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            wrinkle_string=dataSnapshot.getValue(String.class);
            wrinkle_score_main.setText(wrinkle_string);
            if(wrinkle_string.equals("-") || wrinkle_string.equals(null)){
                wrinkle_status.setText("");
            }
            else{
                wrinkle_status.setText("Good Balance!");
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
    moisture_txt.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            moisture_string =dataSnapshot.getValue(String.class);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    moisture_score.setText(moisture_string);
                }
            }, 500);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
    moisturemain_txt.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            moisture_string =dataSnapshot.getValue(String.class);
            moisture_score_main.setText(moisture_string);

            if(moisture_string.equals("-") || moisture_string.equals(null)){
                moisture_status.setText("");
            }
            else{
                moisture_status.setText("Great!");
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
    crdata.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            crdataint=dataSnapshot.getValue(long.class);
            Log.e("야", String.valueOf(crdataint));
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
    cldata.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            cldataint=dataSnapshot.getValue(long.class);
            Log.e("야", String.valueOf(cldataint));

        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
    urdata.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            urdataint=dataSnapshot.getValue(long.class);
            Log.e("야", String.valueOf(urdataint));
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
    uldata.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            uldataint=dataSnapshot.getValue(long.class);
            Log.e("야", String.valueOf(uldataint));
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
    skintype_txt.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            skintype_string =dataSnapshot.getValue(String.class);
            skintype_main.setText(skintype_string);
            skintype_result.setText(skintype_string);
            if(skintype_string.equals("No data yet") || skintype_string.equals(null)){
                question.setText("Do you want to find out your skin type?");
            }
            else{
                if(skintype_string.equals("D R N T")) {
                    question.setText("Dry - Resensitant - Non-pigmented - Tight");
                }
                if(skintype_string.equals("O R N T")) {
                    question.setText("Oily - Resensitant - Non-pigmented - Tight");
                }
                if(skintype_string.equals("D S N T")) {
                    question.setText("Dry - Sensitive - Non-pigmented - Tight");
                }
                if(skintype_string.equals("O S N T")) {
                    question.setText("Oily - Sensitive - Non-pigmented - Tight");
                }
                if(skintype_string.equals("D R P T")) {
                    question.setText("Dry - Resensitant - Pigmented - Tight");
                }
                if(skintype_string.equals("O R P T")) {
                    question.setText("Oily - Resensitant - Pigmented - Tight");
                }
                if(skintype_string.equals("D R P W")) {
                    question.setText("Dry - Resensitant - Pigmented - Wrinkle");
                }
                if(skintype_string.equals("O R P W")) {
                    question.setText("Oily - Resensitant - Pigmented - Wrinkle");
                }
                if(skintype_string.equals("O S P W")) {
                    question.setText("Oily - Sensitive - Pigmented - Wrinkle");
                }
                if(skintype_string.equals("O S N W")) {
                    question.setText("Oily - Sensitive - Non-pigmented - Wrinkle");
                }
                if(skintype_string.equals("O S P T")) {
                    question.setText("Oily - Sensitive - Pigmented - Tight");
                }

            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });
}

    @Override
    public void onPause() {
        super.onPause();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void doTakePhotoAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String url = "tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
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

                        image.setImageBitmap(photo);
                        image_main.setImageBitmap(photo);

                        databaseReference.child("result").child("filepath").setValue(filePath);

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

    public class CustomBitmapPool implements BitmapPool {
        @Override
        public int getMaxSize() {
            return 0;
        }

        @Override
        public void setSizeMultiplier(float sizeMultiplier) {

        }

        @Override
        public boolean put(Bitmap bitmap) {
            return false;
        }

        @Override
        public Bitmap get(int width, int height, Bitmap.Config config) {
            return null;
        }

        @Override
        public Bitmap getDirty(int width, int height, Bitmap.Config config) {
            return null;
        }

        @Override
        public void clearMemory() {
        }

        @Override
        public void trimMemory(int level) {
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

    public void screenshot(){
        rs = RenderScript.create(this);
        View view=getWindow().getDecorView();
        view.setDrawingCacheEnabled(false);
        view.setDrawingCacheEnabled(true);
        bitamp = view.getDrawingCache();
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        blurBitMap = rsBlurProcessor.blur(bitamp, 20f, 3);
        backgroundimg.setImageBitmap(blurBitMap);
        backgroundimg.startAnimation(alphaback);
    }


    public void screenshotdash(){
        rs2 = RenderScript.create(this);
        View view=getWindow().getDecorView();
        view.setDrawingCacheEnabled(false);
        view.setDrawingCacheEnabled(true);
        bitamp2 = view.getDrawingCache();
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs2);
        blurBitMap2 = rsBlurProcessor.blur(bitamp2, 20f, 3);
        dashback.setImageBitmap(blurBitMap2);
        dashback.startAnimation(alphaback);
    }
}