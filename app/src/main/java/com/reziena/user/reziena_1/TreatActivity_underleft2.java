package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RenderScript;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reziena.user.reziena_1.utils.RSBlurProcessor;

import org.opencv.core.Point;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TreatActivity_underleft2 extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference wrinkle_txt;
    private DatabaseReference underrightdata,underleftdata,cheekleftdata,cheekrightdata;
    String wrinkle_string;
    String underleftstring,underrightstring;
    public static Activity underleftativity;
    RenderScript rs;
    Bitmap blurBitMap;
    private static Bitmap bitamp;
    ImageView content1, content2;
    ImageView forehead, underleft, underright, eyeleft, eyeright, cheekl, cheekr, mouth, back, backgroundimg;
    LinearLayout component;
    TextView component_txt,u_tright_txt1,u_tright_txt2,u_tleft_txt1,u_tleft_txt2,c_tright_txt1,c_tright_txt2,c_tleft_txt1,c_tleft_txt2;
    RelativeLayout treatback, underleft_layout,treat_default;
    int undercount=0, data=0, level=0, timer_sec, count=0;
    ImageView u_tleft_line1,u_tleft_line2,u_tleft_line3,u_tleft_line4,u_tleft_line5,u_tleft_line6,
            u_tleft_line7,u_tleft_line8,u_tleft_line9,u_tleft_line10,u_tleft_line11,u_tleft_line12,u_tleft_line13;
    TimerTask second;
    String part;
    AnimationDrawable utlani1,utlani2,utlani3,utlani4,utlani5,utlani6,utlani7,utlani8,utlani9,utlani10,utlani11,utlani12,utlani13;
    static String finish;

    public static void intentpage(String string) {
        finish=string;
    }

    public void animation() {
        second = new TimerTask() {
            @Override
            public void run() {
                Log.e("카운터",String.valueOf(count));
                count++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            if (count == 1) {
                                String str = "THIS COLUMN HAS 6 LINES.\nPLACE THE DEVICE TO THE COLORED LINE AS\nSHOWN. AND PRESS THE CENTER BUTTON TO\nSTART TREATING ONE LINE";
                                SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                                ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 72, 126, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                component_txt.setText(ssb);
                                u_tleft_line8.setBackgroundResource(R.drawable.underleftanim1);
                                utlani8 = (AnimationDrawable) u_tleft_line8.getBackground();
                                utlani8.start();
                            }
                            if (count == 2) {
                                utlani8.stop();
                                u_tleft_line8.setBackgroundResource(R.drawable.line9finish);
                                u_tleft_line9.setBackgroundResource(R.drawable.underleftanim1);
                                utlani9 = (AnimationDrawable) u_tleft_line9.getBackground();
                                utlani9.start();
                                u_tleft_txt1.setText("5 left");
                            }
                            if (count == 3) {
                                utlani9.stop();
                                u_tleft_line9.setBackgroundResource(R.drawable.line9finish);
                                u_tleft_line10.setBackgroundResource(R.drawable.underleftanim1);
                                utlani10 = (AnimationDrawable) u_tleft_line10.getBackground();
                                utlani10.start();
                                u_tleft_txt1.setText("4 left");
                            }
                            if (count == 4) {
                                utlani10.stop();
                                u_tleft_line10.setBackgroundResource(R.drawable.line9finish);
                                u_tleft_line11.setBackgroundResource(R.drawable.underleftanim1);
                                utlani11 = (AnimationDrawable) u_tleft_line11.getBackground();
                                utlani11.start();
                                u_tleft_txt1.setText("3 left");
                            }
                            if (count == 5) {
                                utlani11.stop();
                                u_tleft_line11.setBackgroundResource(R.drawable.line9finish);
                                u_tleft_line12.setBackgroundResource(R.drawable.underleftanim1);
                                utlani12 = (AnimationDrawable) u_tleft_line12.getBackground();
                                utlani12.start();
                                u_tleft_txt1.setText("2 left");
                            }
                            if (count == 6) {
                                utlani12.stop();
                                u_tleft_line12.setBackgroundResource(R.drawable.line9finish);
                                u_tleft_line13.setBackgroundResource(R.drawable.underleftanim1);
                                utlani13 = (AnimationDrawable) u_tleft_line13.getBackground();
                                utlani13.start();
                                u_tleft_txt1.setText("1 left");
                            }
                            if (count == 7) {
                                String str = "THIS COLUMN HAS 7 LINES.\nPLACE THE DEVICE TO THE COLORED LINE AS\nSHOWN. AND PRESS THE CENTER BUTTON TO\nSTART TREATING ONE LINE";
                                SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                                ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 72, 126, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                component_txt.setText(ssb);
                                utlani13.stop();
                                u_tleft_line13.setBackgroundResource(R.drawable.line9finish);
                                u_tleft_line1.setBackgroundResource(R.drawable.underleftanim2);
                                utlani1 = (AnimationDrawable) u_tleft_line1.getBackground();
                                utlani1.start();
                                u_tleft_txt1.setText("DONE");
                                u_tleft_txt1.setTextColor(Color.parseColor("#9E0958"));
                            }
                            if (count == 8) {
                                utlani1.stop();
                                u_tleft_line1.setBackgroundResource(R.drawable.line5finish);
                                u_tleft_line2.setBackgroundResource(R.drawable.underleftanim2);
                                utlani2 = (AnimationDrawable) u_tleft_line2.getBackground();
                                utlani2.start();
                                u_tleft_txt2.setText("6 left");
                            }
                            if (count == 9) {
                                utlani2.stop();
                                u_tleft_line2.setBackgroundResource(R.drawable.line5finish);
                                u_tleft_line3.setBackgroundResource(R.drawable.underleftanim2);
                                utlani3 = (AnimationDrawable) u_tleft_line3.getBackground();
                                utlani3.start();
                                u_tleft_txt2.setText("5 left");
                            }
                            if (count == 10) {
                                utlani3.stop();
                                u_tleft_line3.setBackgroundResource(R.drawable.line5finish);
                                u_tleft_line4.setBackgroundResource(R.drawable.underleftanim2);
                                utlani4 = (AnimationDrawable) u_tleft_line4.getBackground();
                                utlani4.start();
                                u_tleft_txt2.setText("4 left");
                            }
                            if (count == 11) {
                                utlani4.stop();
                                u_tleft_line4.setBackgroundResource(R.drawable.line5finish);
                                u_tleft_line5.setBackgroundResource(R.drawable.underleftanim2);
                                utlani5 = (AnimationDrawable) u_tleft_line5.getBackground();
                                utlani5.start();
                                u_tleft_txt2.setText("3 left");
                            }
                            if (count == 12) {
                                utlani5.stop();
                                u_tleft_line5.setBackgroundResource(R.drawable.line5finish);
                                u_tleft_line6.setBackgroundResource(R.drawable.underleftanim2);
                                utlani6 = (AnimationDrawable) u_tleft_line6.getBackground();
                                utlani6.start();
                                u_tleft_txt2.setText("2 left");
                            }
                            if (count == 13) {
                                utlani6.stop();
                                u_tleft_line6.setBackgroundResource(R.drawable.line5finish);
                                u_tleft_line7.setBackgroundResource(R.drawable.underleftanim2);
                                utlani7 = (AnimationDrawable) u_tleft_line7.getBackground();
                                utlani7.start();
                                u_tleft_txt2.setText("1 left");
                            } if (count >= 14) {
                                u_tleft_line7.setBackgroundResource(R.drawable.line5finish);
                                component_txt.setText("GOOD JOB");
                                u_tleft_txt2.setText("DONE");
                                u_tleft_txt2.setTextColor(Color.parseColor("#9E0958"));
                                data=25;
                                underleftstring="true";
                            }
                            if(count==15){
                                setData task = new setData();
                                task.execute("http://"+R.string.IP_Address+"/saveTreat.php", "uneye_l");

                                if (! TreatActivity_underleft2.this.isFinishing()) {
                                    Intent intent = new Intent(getApplicationContext(),DoneActivity.class);
                                    intent.putExtra("stringlist","underleft");
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
                            }

                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 1000);

    }

    public void onStart() {
        super.onStart();
    }

    class setData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String where = params[1];

            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
            Date currentTime = new Date();
            String date = mSimpleDateFormat.format ( currentTime );

            SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
            String userID = sp_userID.getString("userID", "");
            String postParameters = "date="+date+"&id="+userID+"&where="+where;
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

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treat_underleft2);
        underleftativity=TreatActivity_underleft2.this;

        underrightdata = databaseReference.child("result").child("underrightstring");
        underleftdata = databaseReference.child("result").child("underleftstring");

        Resources res = getResources();
        final Drawable  cheekrightimg= res.getDrawable(R.drawable.cheekrightimg);
        final Drawable  cheekleftimg= res.getDrawable(R.drawable.cheekleftimg);
        final Drawable  cheekunderrightimg= res.getDrawable(R.drawable.cheekunderimg);
        final Drawable  cheekunderleftimg= res.getDrawable(R.drawable.cheekunderleft);
        //값 받아오기
        content1 = findViewById(R.id.treatup_ul2);
        content2 = findViewById(R.id.treatdown_ul2);
        u_tleft_line1=(ImageView)findViewById(R.id.u_tleft_line1);
        u_tleft_line2=(ImageView)findViewById(R.id.u_tleft_line2);
        u_tleft_line3=(ImageView)findViewById(R.id.u_tleft_line3);
        u_tleft_line4=(ImageView)findViewById(R.id.u_tleft_line4);
        u_tleft_line5=(ImageView)findViewById(R.id.u_tleft_line5);
        u_tleft_line6=(ImageView)findViewById(R.id.u_tleft_line6);
        u_tleft_line7=(ImageView)findViewById(R.id.u_tleft_line7);
        u_tleft_line8=(ImageView)findViewById(R.id.u_tleft_line8);
        u_tleft_line9=(ImageView)findViewById(R.id.u_tleft_line9);
        u_tleft_line10=(ImageView)findViewById(R.id.u_tleft_line10);
        u_tleft_line11=(ImageView)findViewById(R.id.u_tleft_line11);
        u_tleft_line12=(ImageView)findViewById(R.id.u_tleft_line12);
        u_tleft_line13=(ImageView)findViewById(R.id.u_tleft_line13);
        u_tright_txt1=(TextView)findViewById(R.id.u_tright_txt1);
        u_tright_txt2=(TextView)findViewById(R.id.u_tright_txt2);
        u_tleft_txt1=(TextView)findViewById(R.id.u_tleft_txt1);
        u_tleft_txt2=(TextView)findViewById(R.id.u_tleft_txt2);
        c_tright_txt1=(TextView)findViewById(R.id.c_tright_txt1);
        c_tright_txt2=(TextView)findViewById(R.id.c_tright_txt2);
        c_tleft_txt1=(TextView)findViewById(R.id.c_tleft_txt1);
        c_tleft_txt2=(TextView)findViewById(R.id.c_tleft_txt2);
        component_txt=findViewById(R.id.componenttxt_ul);
        backgroundimg=findViewById(R.id.background);
        wrinkle_txt = databaseReference.child("result").child("winkle");

        animation();
    }

    public void onStop() {
        super.onStop();
        count = 0;
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
    }
}
