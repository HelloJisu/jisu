package com.reziena.user.reziena_1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.renderscript.RenderScript;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reziena.user.reziena_1.utils.RSBlurProcessor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeActivity extends AppCompatActivity {

    static BluetoothAdapter mBtAdapter;
    String deviceName;
    private String mDeviceAddress = "";
    LoginActivity loginActivity = (LoginActivity) LoginActivity.loginactivity;

    boolean measureWrinkle=false;

    BluetoothDevice device;

    static boolean isFirst = true;

    static BluetoothConnectionService mBluetoothConnection;

    // 스마트폰끼리의 UUID
    private static final UUID MY_UUID = UUID.fromString("00000003-0000-1000-8000-00805f9b34fb");
    private final static byte[] PIN  = {1, 2, 3, 4};
    private static final int REQUEST_LOCATION = 1;
    private static final int REQUEST_ENABLE_BT = 1;

    int count; /**discovery된 device가 몇개인지*/
    int countDown=45;
    ArrayList<String> address = new ArrayList<>(); /** discovery한 address */
    ArrayList<String> bondedDevice = new ArrayList<>();


    Animation alphaback;
    RenderScript rs, rs2;
    Bitmap blurBitMap, blurBitMap2;
    private static Bitmap bitamp, bitamp2;
    LoginActivity loginactivity = (LoginActivity) LoginActivity.loginactivity;
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
    LinearLayout dash6,dash1,dash8,dash9,dash10;
    ImageView layer1, logo,backgroundimg,dashback,dashbackimg;
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

    TextView home_setName, dash_setName;

    int moisture_per=0, wrinkle_per=0;

    String btTag = "BLUETOOTH_CONNECT";

    String treatResult="";
    ImageView[] check = new ImageView[5];
    //ImageView check1, check2, check3, check4, check5;

    ImageView mois_up, mois_down, wrinkle_up, wrinkle_down, imageView2;
    int max_mois, max_wrink;

    private String userName;
    public static String IP_Address = "52.32.36.182";
    static String day_string;
    static String devName = "상아";     //Galaxy Note8, Galaxy S9

    private String DB_skintype, DB_moisture="", DB_wrinkle="";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeactivity = HomeActivity.this;
        loginActivity.finish();

        checkPermissions();

        // 값 받아오기\
        dashbackimg=findViewById(R.id.dashbackground);
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
        check[0] = findViewById(R.id.check1);
        check[1] = findViewById(R.id.check2);
        check[2] = findViewById(R.id.check3);
        check[3] = findViewById(R.id.check4);
        check[4] = findViewById(R.id.check5);
        home1=findViewById(R.id.home1);
        home2=findViewById(R.id.home2);
        home3=findViewById(R.id.home3);
        home4=findViewById(R.id.home4);
        home5=findViewById(R.id.home5);
        home8=findViewById(R.id.home8);
        home9=findViewById(R.id.home9);
        dash1=findViewById(R.id.dash1);
        dash8=findViewById(R.id.dash8);
        dash9=findViewById(R.id.dash9);
        backgroundimg=findViewById(R.id.backgroundimage);
        dashback=findViewById(R.id.dashback);
        skintype_main=findViewById(R.id.skintype_main);
        home_setName = findViewById(R.id.home_setName);
        dash_setName = findViewById(R.id.dash_setName);
        String dialogstring;

        mois_up = findViewById(R.id.mois_up);
        mois_down = findViewById(R.id.mois_down);
        wrinkle_up = findViewById(R.id.wrinkle_up);
        wrinkle_down = findViewById(R.id.wrinkle_down);

        imageView2 = findViewById(R.id.imageView2);

        // check
        //Calendar cal = Calendar.getInstance();
        //Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
        //cal.setTimeZone(time);

        // init the Bottom Sheet Behavior
        bottomSheetBehavior = BottomSheetBehavior.from(design_bottom_sheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // 시작할 때 DashBoard와 기계 이미지 안보이게 하기
        dashboard.setVisibility(View.INVISIBLE);
        layer1.setVisibility(View.INVISIBLE);


        // set hideable or not
        bottomSheetBehavior.setHideable(false);

        Intent subintent = getIntent();
        dialogstring = subintent.getExtras().getString("name");

        if(dialogstring!=null){
            if(dialogstring.equals("skintypedialog")){
                final Intent intent = new Intent(getApplicationContext(),SkintypeAsk.class);
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        startActivity(intent);
                        screenshot();
                    }
                }, 100);
            }
        }

        //animation
        final Animation alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_alpha);
        final Animation alpha2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_alpha2);
        final Animation scaletranslate2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scaletranslate2);
        final Animation animationup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationup);
        final Animation animationdown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationdown);
        final Animation translateup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translateup);
        final Animation translatedown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translatedown);
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
                        if (measureWrinkle) {
                            intent = new Intent(getApplicationContext(), LoadingActivity.class);
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                        } else {
                            // wrinkle level이 없다고 팝업 띄우기
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
                        dashbackimg.startAnimation(animationup);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        dashboard.setVisibility(View.VISIBLE);
                        dashboard.startAnimation(alpha);
                        home1.setVisibility(View.INVISIBLE);
                        home2.setVisibility(View.INVISIBLE);
                        home3.setVisibility(View.INVISIBLE);
                        home4.setVisibility(View.INVISIBLE);
                        home5.setVisibility(View.INVISIBLE);
                        dash1.setVisibility(View.VISIBLE);
                        dash8.setVisibility(View.VISIBLE);
                        dash9.setVisibility(View.VISIBLE);
                        layer1.setVisibility(View.VISIBLE);
                        layer1.startAnimation(alpha);
                        layer1.startAnimation(translateup);
                        toolbar.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.arrow_dash:
                    case R.id.toolbar_dash:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        dashbackimg.startAnimation(animationdown);
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run() {
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            }
                        }, 13);
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run() {
                                dash1.setVisibility(View.INVISIBLE);
                            }
                        }, 5);
                        dashboard.startAnimation(alpha2);
                        layer1.setVisibility(View.INVISIBLE);
                        //layer1.setVisibility(View.INVISIBLE);
                        home1.setVisibility(View.VISIBLE);
                        home2.setVisibility(View.VISIBLE);
                        home3.setVisibility(View.VISIBLE);
                        home4.setVisibility(View.VISIBLE);
                        home5.setVisibility(View.VISIBLE);
                        dash1.setVisibility(View.INVISIBLE);
                        dash8.setVisibility(View.INVISIBLE);
                        dash9.setVisibility(View.INVISIBLE);
                        layer1.startAnimation(alpha2);
                        //dash8.setVisibility(View.INVISIBLE);
                        //dash9.setVisibility(View.INVISIBLE);
                        layer1.startAnimation(translatedown);
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.logo:
                        // reset
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
                    case R.id.imageView2:
                        SharedPreferences sp_userName = getSharedPreferences("userName", MODE_PRIVATE);
                        SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sp_userName.edit();
                        SharedPreferences.Editor editor2 = sp_userID.edit();
                        editor1.remove("userName");
                        editor2.remove("userID");
                        editor1.commit();
                        editor2.commit();
                        Log.e("remove", "yeal~!"); //하기실어
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
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
        imageView2.setOnClickListener(onClickListener);

        // SharedPreferences에서 이름 받아오기
        SharedPreferences sp_userName = getSharedPreferences("userName", MODE_PRIVATE);
        userName = sp_userName.getString("userName", "");
        home_setName.setText("GOOD MORNING, "+ userName+"!");
        dash_setName.setText("GOOD MORNING, "+ userName+"!");
        Log.e("SharedPreferences", userName);

        if (isFirst) getBondedDevices();

    }

    private void checkPermissions() {

        int permissionCheck1 = ContextCompat.checkSelfPermission(this , Manifest.permission.BLUETOOTH_ADMIN);
        if(permissionCheck1 == PackageManager.PERMISSION_GRANTED) Log.e("퍼미션", "BLUETOOTH_ADMIN granted!");
        else Log.e("퍼미션", "BLUETOOTH_ADMIN not granted!");

        int permissionCheck2 = ContextCompat.checkSelfPermission(this , Manifest.permission.BLUETOOTH);
        if(permissionCheck2 == PackageManager.PERMISSION_GRANTED) Log.e("퍼미션", "BLUETOOTH granted!");
        else Log.e("퍼미션", "BLUETOOTH not granted!");

        int permissionCheck3 = ContextCompat.checkSelfPermission(this , ACCESS_FINE_LOCATION);
        if(permissionCheck3 == PackageManager.PERMISSION_GRANTED) Log.e("퍼미션", "ACCESS_FINE_LOCATION granted!");
        else Log.e("퍼미션", "ACCESS_FINE_LOCATION not granted!");

        int permissionCheck4 = ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permissionCheck4 == PackageManager.PERMISSION_GRANTED) Log.e("퍼미션", "ACCESS_COARSE_LOCATION granted!");
        else {
            Log.e("퍼미션", "ACCESS_COARSE_LOCATION not granted!");
            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }

    public static void send(String message) {
        byte[] bytes = message.getBytes(Charset.defaultCharset());
        mBluetoothConnection.write(bytes);
    }

    private void getBondedDevices() {
        isFirst = false;
        String isHave="";
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.e(btTag, "getBondedDevices() init");

        // 장치가 블루투스 지원하지 않는 경우
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Log.e("STATUS", "BLE 지원 불가능");
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("BLE 지원 안됨");
            builder.setMessage("블루투스가 지원이 안됩니다,");
            builder.setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            android.support.v7.app.AlertDialog alert = builder.create();
            alert.show();
            finish();
        }

        // 장치가 블루투스 지원하는 경우
        // 블루투스가 꺼져있으면 사용자에게 블루투스 활성화를 요청한다
        if (!mBtAdapter.isEnabled()) {
            Log.e("STATUS", "BLE 비활성상태");
            //Toast.makeText(getApplicationContext(), "BLE 비활성상태", Toast.LENGTH_SHORT).show();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);  // 뒤에것은 어떤 요청인지 알기 위해
        }

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if (pairedDevices.size()>0) {
            for (BluetoothDevice bond : pairedDevices) {
                deviceName = bond.getName();
                Log.e(btTag, "bonded name:" + deviceName);
                if (deviceName.equals(devName)) {
                    isHave += "yes";
                    Log.e(btTag, "devName: "+deviceName);
                    Log.e(btTag, "devAdd: "+ bond.getAddress());
                    connectToDevice(bond.getAddress());
                }
            }
            if (!isHave.contains("yes")) {
                discoveryStart();
            }
        }
        else { discoveryStart(); }
    }

    public void discoveryStart() {
        Intent intent = new Intent(getApplicationContext(), BTOnActivity.class);
        startActivity(intent);
    }

    public boolean connectToDevice(String address) {
        mDeviceAddress = address;
        device = mBtAdapter.getRemoteDevice(mDeviceAddress);

        /** Filtering Broadcast Receiver */
        IntentFilter filter3 = new IntentFilter();
        filter3.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter3.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter3.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter3.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        /** Start Broadcast Receiver */
        this.registerReceiver(mBroadcastReceiver3, filter3);

        if (mBtAdapter == null || address == null) {
            Log.e(btTag, "mBtAdapter==null & address==null");
            return false;
        }

        Log.e(btTag, "startBTConnection: initializing RFCOM Bluetooth Connection");

        mBluetoothConnection = new BluetoothConnectionService(getApplicationContext());
        mBluetoothConnection.startClient(device, MY_UUID);

        //discoveryStart();

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("퍼미션", "ACCESS_COARSE_LOCATION granted!");
            } else {
                Log.e("퍼미션", "ACCESS_COARSE_LOCATION 삭제하지말라고!!");
            }
        }
    }

    //database
    @Override
    public void onStart() { super.onStart(); }

    // reset
    class setData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String where = params[1];

            SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
            String userID = sp_userID.getString("userID", "");
            String postParameters = "id="+userID+"&";
            Log.e("cheekl-postParameters", "update/"+postParameters);

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);;

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                // response
                int responseStatusCode = httpURLConnection.getResponseCode();
                String responseStatusMessage = httpURLConnection.getResponseMessage();
                Log.e("response-update", "POST response Code - " + responseStatusCode);
                Log.e("response-update", "POST response Message - "+ responseStatusMessage);

            } catch (Exception e) {
                Log.e("ERROR", "updateDataError ", e);
            }
            return null;

        }
    }

    // calling Moisture
    class GetData1 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String getResult) {
            super.onPostExecute(getResult);

            if (getResult==null) {
                Log.e("getdata-moisture", "getResult==null");
                DB_moisture = "-";
                mois_up.setVisibility(View.INVISIBLE);
                mois_down.setVisibility(View.INVISIBLE);
            } else {
                Log.e("getdata-moisture", "getResult=="+getResult);
                if (getResult.contains("No_results")) {
                    DB_moisture = "-";
                    mois_up.setVisibility(View.INVISIBLE);
                    mois_down.setVisibility(View.INVISIBLE);
                } else showResult(getResult);
            }
            moisture_score_main.setText(DB_moisture);
            moisture_score.setText(DB_moisture);

            SetArrow setTask1 = new SetArrow();
            setTask1.execute("http://"+IP_Address+"/setArrow.php", "moisture");

        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
            String userID = sp_userID.getString("userID", "");
            String postParameters = "id="+userID;
            Log.e("moisture-userID", userID);

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.e("moisture-postParameters", postParameters);
                outputStream.flush();
                outputStream.close();

                InputStream inputStream;
                int responseStatusCode = httpURLConnection.getResponseCode();
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                    Log.e("moisture-response", "code - HTTP_OK - " + responseStatusCode);
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                    Log.e("moisture-response", "code - HTTP_NOT_OK - " + responseStatusCode);
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.e("moisture-error-stream", e.getMessage());
            }
            return null;
        }

        private void showResult(String result){
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("getData");

                String level="";
                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);
                    level= item.getString("level");
                    moisture_per = item.getInt("level");
                    max_mois = item.getInt("id");
                    max_mois -= 1;
                    Log.e("moisture-level ", DB_moisture+"!!!!!!!!!!");
                }
                Log.e("moisture::::", String.valueOf(moisture_per));
                switch (level) {
                    case "100":
                        DB_moisture = "A+"; break;
                    case "95":
                        DB_moisture = "A"; break;
                    case "90":
                        DB_moisture = "B+"; break;
                    case "85":
                        DB_moisture = "B"; break;
                    case "80":
                        DB_moisture = "C+"; break;
                    case "75":
                        DB_moisture = "C"; break;
                }

            } catch (JSONException e) {
                Log.d("moisture-JSON", "showResult : ", e);
            }

        }
    }

    // calling Wrinkle
    class GetData2 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String getResult) {
            super.onPostExecute(getResult);

            if (getResult==null) {
                measureWrinkle = false;
                Log.e("getdata-wrinkle", "getResult==null");
                DB_wrinkle = "-";
            } else {
                Log.e("getdata-wrinkle", "getResult=="+getResult);
                if (getResult.contains("No_results")) {
                    DB_wrinkle = "-";
                    wrinkle_up.setVisibility(View.INVISIBLE);
                    wrinkle_down.setVisibility(View.INVISIBLE);
                } else showResult(getResult);
            }
            wrinkle_score_main.setText(DB_wrinkle);
            wrinkle_score.setText(DB_wrinkle);

            SetArrow setTask2 = new SetArrow();
            setTask2.execute("http://"+IP_Address+"/setArrow.php", "wrinkle");
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
            String userID = sp_userID.getString("userID", "");
            String postParameters = "id="+userID;

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                Log.e("wrinkle-Connect", "complete");

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.e("wrinkle-postParameters", postParameters);
                outputStream.flush();
                outputStream.close();

                InputStream inputStream;
                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("wrinkle-response", "code - " + responseStatusCode);

                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.e("wrinkle-error", e.getMessage());
            }
            return null;
        }

        private void showResult(String result){
            measureWrinkle = true;
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("getData");

                String level="";
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject item = jsonArray.getJSONObject(i);

                    level= item.getString("level");
                    wrinkle_per = item.getInt("level");
                    max_wrink = item.getInt("id");
                    max_wrink -= 1;
                }
                Log.e("wrinkle::::", String.valueOf(wrinkle_per));
                switch (level) {
                    case "100":
                        DB_wrinkle = "A+"; break;
                    case "95":
                        DB_wrinkle = "A"; break;
                    case "90":
                        DB_wrinkle = "B+"; break;
                    case "85":
                        DB_wrinkle = "B"; break;
                    case "80":
                        DB_wrinkle = "C+"; break;
                    case "75":
                        DB_wrinkle = "C"; break;
                }

            } catch (JSONException e) {
                Log.d("wrinkle-JSON", "showResult : ", e);
            }
        }
    }

    // calling skintype
    class GetData3 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String getResult) {
            super.onPostExecute(getResult);

            Log.e("skintype-", "onPostExecute - " + getResult);
            if (getResult==null) {
                DB_skintype = "No data yet";
            } else {
                if (getResult.contains("No_results")) {
                    DB_skintype = "No data yet";
                } else showResult(getResult);
            }
            skintype_main.setText(DB_skintype);
            skintype_result.setText(DB_skintype);
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
            String userID = sp_userID.getString("userID", "");
            String postParameters = "id="+userID;

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                Log.e("skintype-Connect", "complete");

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.e("skintype-postParameters", postParameters);
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("skintype-response", "code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                    //Log.e("read", String.valueOf(sb.append(line)));
                }
                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.e("skintype-error", e.getMessage());
            }
            return null;
        }

        private void showResult(String result){
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("getData");

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);
                    DB_skintype = item.getString("result");
                    Log.e("skintype ", DB_skintype+"!!!!!!!!!!");
                }

            } catch (JSONException e) {
                Log.d("JSON", "showResult : ", e);
            }

        }
    }

    // calling Treat
    class GetData4 extends AsyncTask<String, Void, String> {
        List<String[]> wrinkleArray = new ArrayList<>();

        @Override
        protected void onPostExecute(String getResult) {
            super.onPostExecute(getResult);

            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
            Date currentTime = new Date();
            String date = mSimpleDateFormat.format ( currentTime );
            String dates[] = date.split("-");
            //String getDates[];

            Log.e("getdata-treat", "getResult=="+getResult);
            if (getResult.contains("No_results")) {
                check[0].setImageResource(R.drawable.noncheck);
                check[1].setImageResource(R.drawable.noncheck);
                check[2].setImageResource(R.drawable.noncheck);
                check[3].setImageResource(R.drawable.noncheck);
                check[4].setImageResource(R.drawable.noncheck);
            } else showResult(getResult);
            if (getResult==null) {
                measureWrinkle = false;
                Log.e("getdata-treat", "getResult==null");
                DB_wrinkle = "-";
                wrinkle_up.setVisibility(View.INVISIBLE);
                wrinkle_down.setVisibility(View.INVISIBLE);
                check[0].setImageResource(R.drawable.noncheck);
                check[1].setImageResource(R.drawable.noncheck);
                check[2].setImageResource(R.drawable.noncheck);
                check[3].setImageResource(R.drawable.noncheck);
                check[4].setImageResource(R.drawable.noncheck);
            } else {
                for (int i = 0; i <= (wrinkleArray.size() / 5); i++) {
                    //Log.e("WrinkleArray i " + i + "번째", wrinkleArray.get(i)[0] + "/" + wrinkleArray.get(i)[1] + "/" + wrinkleArray.get(i)[2]);
                    try {
                        for (int j = 0; j < 5; j++) {
                            Log.e("WrinkleArray j " + j + "번째", wrinkleArray.get(j)[0] + "/" + wrinkleArray.get(j)[1] + "/" + wrinkleArray.get(j)[2]);
                            if (wrinkleArray.get(i+j)[1].equals(wrinkleArray.get(i+j+1)[1])) {
                                Log.e((i+j)+"번째 & ", (i+j+1)+"번째 날짜같음");
                                if ((wrinkleArray.get(i+j)[2].contains("uneye_l") || wrinkleArray.get(i+j)[2].contains("uneye_r")) && (wrinkleArray.get(i+j+1)[2].contains("uneye_l") || wrinkleArray.get(i+j+1)[2].contains("uneye_r"))){
                                    check[j].setImageResource(R.drawable.check);
                                } else if ((wrinkleArray.get(i+j)[2].contains("cheek_l") || wrinkleArray.get(i+j)[2].contains("cheek_r")) && (wrinkleArray.get(i+j+1)[2].contains("cheek_l") || wrinkleArray.get(i+j+1)[2].contains("cheek_r"))){
                                    Log.e("cheek", "both");
                                    check[j].setImageResource(R.drawable.check);
                                } else {
                                    check[j].setImageResource(R.drawable.ximg);
                                }
                            }
                            j++;
                        }
                    } catch (Exception e) {
                        Log.e("Exception ", "e");
                        if ((wrinkleArray.size() % 5) != 0) {
                            for (int k = 4; k >= 5-(wrinkleArray.size() % 5); k--) {
                                check[k].setImageResource(R.drawable.noncheck);
                            }
                        }
                    }
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
            String userID = sp_userID.getString("userID", "");
            String postParameters = "id="+userID;

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                Log.e("wrinkle-Connect", "complete");

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.e("wrinkle-postParameters", postParameters);
                outputStream.flush();
                outputStream.close();

                InputStream inputStream;
                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("wrinkle-response", "code - " + responseStatusCode);

                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.e("wrinkle-error", e.getMessage());
            }
            return null;
        }

        private void showResult(String result){
            measureWrinkle = true;
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("getData");

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject item = jsonArray.getJSONObject(i);
                    wrinkleArray.add(new String[]{item.getString("id"),item.getString("date"),item.getString("value")});
                }
            } catch (JSONException e) {
                Log.d("treat-JSON", "showResult : "+e.getMessage());
            }
        }
    }

    class SetArrow extends AsyncTask<String, Void, String> {

        String mois="", wrink="";
        @Override
        protected void onPostExecute(String getResult) {
            super.onPostExecute(getResult);

            //Log.e("setArrow-Result", getResult);
            Log.e("현재디비", DB_moisture+DB_wrinkle);

            Log.e("setArrow", getResult);

            showResult(getResult);

            if (getResult.contains("No_results")) {
                if (getResult.contains("moisture")) {
                    mois_up.setVisibility(View.INVISIBLE);
                    mois_down.setVisibility(View.INVISIBLE);
                }
                else if (getResult.contains("wrinkle")) {
                    wrinkle_up.setVisibility(View.INVISIBLE);
                    wrinkle_down.setVisibility(View.INVISIBLE);
                }
            }
            else if (getResult.contains("moisture")) {
                if(mois.equals("up")) {
                    Log.e("setting-moisture", "up");
                    mois_up.setVisibility(View.VISIBLE);
                    mois_down.setVisibility(View.INVISIBLE);
                } else if(mois.equals("down")) {
                    Log.e("setting-moisture", "down");
                    mois_up.setVisibility(View.INVISIBLE);
                    mois_down.setVisibility(View.VISIBLE);
                } else {
                    Log.e("setting-moisture", "else");
                    mois_up.setVisibility(View.INVISIBLE);
                    mois_down.setVisibility(View.INVISIBLE);
                }
                moisture_status.setText("Great!");
            }
            else if (getResult.contains("wrinkle")) {
                if(wrink.equals("up")) {
                    Log.e("setting-wrinkle", "up");
                    wrinkle_up.setVisibility(View.VISIBLE);
                    wrinkle_down.setVisibility(View.INVISIBLE);
                }
                else if(wrink.equals("down")) {
                    Log.e("setting-wrinkle", "down");
                    wrinkle_up.setVisibility(View.INVISIBLE);
                    wrinkle_down.setVisibility(View.VISIBLE);
                }
                else {
                    Log.e("setting-wrinkle", "else");
                    wrinkle_up.setVisibility(View.INVISIBLE);
                    wrinkle_down.setVisibility(View.INVISIBLE);
                }
                wrinkle_status.setText("Good Balance");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String dbName = params[1];

            SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
            String userID = sp_userID.getString("userID", "");
            String postParameters;
            if (dbName.equals("moisture")) { postParameters = "id="+userID+"&max="+max_mois+"&dbName="+dbName; }
            else {postParameters = "id="+userID+"&max="+max_wrink+"&dbName="+dbName;}


            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                Log.e("skintype-Connect", "complete");

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.e("setArrow-postParameters", postParameters);
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("skintype-response", "code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                    //Log.e("read", String.valueOf(sb.append(line)));
                }
                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.e("setArrow-error", e.getMessage());
            }
            return null;
        }

        private void showResult(String result){
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("getData");

                int before = 0, now = 0;
                String dbName="";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    dbName = item.getString("dbName");

                    if (dbName.equals("moisture")) {
                        int before_mois = item.getInt("level");
                        if (before_mois < moisture_per) { mois = "up"; }
                        else if (before_mois == moisture_per) { mois = "else"; }
                        else { mois = "down"; }
                        Log.e("setArrow", dbName + "/현재값:" + DB_moisture  + "/전에값" + before_mois);
                    }
                    if (dbName.equals("wrinkle")) {
                        int before_wrink = item.getInt("level");
                        if (before_wrink < wrinkle_per) { wrink = "up"; }
                        else if (before_wrink == wrinkle_per) { wrink = "else"; }
                        else { wrink = "down"; }
                        Log.e("setArrow", dbName + "/현재값:" + DB_wrinkle  + "/전에값" + before_wrink);
                    }
                }
                Log.e("setArrow:::", "/숫자(현,전)" +now+before);

            } catch (JSONException e) {
                Log.d("JSON", "showResult : ", e);
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        GetData1 task1 = new GetData1();
        task1.execute("http://"+IP_Address+"/callingMoisture.php", "");

        GetData2 task2 = new GetData2();
        task2.execute("http://"+IP_Address+"/callingWrinkle.php", "");

        GetData3 task3 = new GetData3();
        task3.execute("http://"+IP_Address+"/callingSkintype.php", "");

        GetData4 task4 = new GetData4();
        task4.execute("http://"+IP_Address+"/callingTreat.php", "");
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

        super.onActivityResult(requestCode, resultCode, data);

        // BT 활성화 아니오 눌렀을 경우 끝
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }

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

    /** Broadcast Receiver for listing devices that are not yet paired */
    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();
            Log.e("broadcasereceiver", "onReceive: ACTION____________come in Receiver3");
            //Log.e(TAG, "Now Action?::" + action);

            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Log.e(btTag,"Now Action?:: " + action);
                Toast toast = Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT);
                toast.show();
                unregisterReceiver(mBroadcastReceiver3);
            }
            else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction())) {
                Log.e(btTag,"Now Action?:: " + action);
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(intent.getAction())) {
                Log.e(btTag,"Now Action?:: " + action);
            }
            else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(intent.getAction())) {
                Log.e(btTag,"Now Action?:: " + action);
            }
        }
    };


}