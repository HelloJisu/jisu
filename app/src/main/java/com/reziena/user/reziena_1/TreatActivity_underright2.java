package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RenderScript;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
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

public class TreatActivity_underright2 extends AppCompatActivity {


    String wrinkle_string;
    String underrightstring,underleftstring;
    RenderScript rs;
    Bitmap blurBitMap;
    private static Bitmap bitamp;
    public static Activity underrightactivity;
    ImageView forehead, underleft, underright, eyeleft, eyeright, cheekl, cheekr, mouth, back, backgroundimg;
    TextView component_txt,u_tright_txt1,u_tright_txt2,u_tleft_txt1,u_tleft_txt2,c_tright_txt1,c_tright_txt2,c_tleft_txt1,c_tleft_txt2;
    int undercount=0, data=0, level=0, timer_sec, count=0;
    ImageView u_tright_line1,u_tright_line2,u_tright_line3,u_tright_line4,u_tright_line5,u_tright_line6,
            u_tright_line7,u_tright_line8,u_tright_line9,u_tright_line10,u_tright_line11,u_tright_line12,u_tright_line13;
    TimerTask second;
    String part;
    ImageView content1, content2;
    AnimationDrawable utrani1,utrani2,utrani3,utrani4,utrani5,utrani6,utrani7,utrani8,utrani9,utrani10,utrani11,utrani12,utrani13;

    public void animation() {
        second = new TimerTask() {
            @Override
            public void run() {
                Log.e("undercount",String.valueOf(count));
                count++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            if (count == 1) {
                                String str = "THIS COLUMN HAS 6 LINES.\nPLACE THE DEVICE TO THE COLORED LINE AS\nSHOWN. AND PRESS THE CENTER BUTTON TO\nSTART TREATING ONE LINE";
                                SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                                ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 72, 126, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                component_txt.setText(ssb);
                                u_tright_line8.setBackgroundResource(R.drawable.underrightanim1);
                                utrani8 = (AnimationDrawable) u_tright_line8.getBackground();
                                utrani8.start();
                            }
                            if (count == 2) {
                                utrani8.stop();
                                u_tright_line8.setBackgroundResource(R.drawable.line8finish);
                                u_tright_line9.setBackgroundResource(R.drawable.underrightanim1);
                                utrani9 = (AnimationDrawable) u_tright_line9.getBackground();
                                utrani9.start();
                                u_tright_txt1.setText("5 left");
                            }
                            if (count == 3) {
                                utrani9.stop();
                                u_tright_line9.setBackgroundResource(R.drawable.line8finish);
                                u_tright_line10.setBackgroundResource(R.drawable.underrightanim1);
                                utrani10 = (AnimationDrawable) u_tright_line10.getBackground();
                                utrani10.start();
                                u_tright_txt1.setText("4 left");
                            }
                            if (count == 4) {
                                utrani10.stop();
                                u_tright_line10.setBackgroundResource(R.drawable.line8finish);
                                u_tright_line11.setBackgroundResource(R.drawable.underrightanim1);
                                utrani11 = (AnimationDrawable) u_tright_line11.getBackground();
                                utrani11.start();
                                u_tright_txt1.setText("3 left");
                            }
                            if (count == 5) {
                                utrani11.stop();
                                u_tright_line11.setBackgroundResource(R.drawable.line8finish);
                                u_tright_line12.setBackgroundResource(R.drawable.underrightanim1);
                                utrani12 = (AnimationDrawable) u_tright_line12.getBackground();
                                utrani12.start();
                                u_tright_txt1.setText("2 left");
                            }
                            if (count == 6) {
                                utrani12.stop();
                                u_tright_line12.setBackgroundResource(R.drawable.line8finish);
                                u_tright_line13.setBackgroundResource(R.drawable.underrightanim1);
                                utrani13 = (AnimationDrawable) u_tright_line13.getBackground();
                                utrani13.start();
                                u_tright_txt1.setText("1 left");
                            }
                            if (count == 7) {
                                String str = "THIS COLUMN HAS 7 LINES.\nPLACE THE DEVICE TO THE COLORED LINE AS\nSHOWN. AND PRESS THE CENTER BUTTON TO\nSTART TREATING ONE LINE";
                                SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                                ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 72, 126, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                component_txt.setText(ssb);
                                utrani13.stop();
                                u_tright_line13.setBackgroundResource(R.drawable.line8finish);
                                u_tright_line1.setBackgroundResource(R.drawable.underrightanim2);
                                utrani1 = (AnimationDrawable) u_tright_line1.getBackground();
                                u_tright_txt1.setText("DONE");
                                u_tright_txt1.setTextColor(Color.parseColor("#9E0958"));
                            }
                            if (count == 8) {
                                utrani1.stop();
                                u_tright_line1.setBackgroundResource(R.drawable.line1finish);
                                u_tright_line2.setBackgroundResource(R.drawable.underrightanim2);
                                utrani2 = (AnimationDrawable) u_tright_line2.getBackground();
                                utrani2.start();
                                u_tright_txt2.setText("6 left");
                            }
                            if (count == 9) {
                                utrani2.stop();
                                u_tright_line2.setBackgroundResource(R.drawable.line1finish);
                                u_tright_line3.setBackgroundResource(R.drawable.underrightanim2);
                                utrani3 = (AnimationDrawable) u_tright_line3.getBackground();
                                utrani3.start();
                                u_tright_txt2.setText("5 left");
                            }
                            if (count == 10) {
                                utrani3.stop();
                                u_tright_line3.setBackgroundResource(R.drawable.line1finish);
                                u_tright_line4.setBackgroundResource(R.drawable.underrightanim2);
                                utrani4 = (AnimationDrawable) u_tright_line4.getBackground();
                                utrani4.start();
                                u_tright_txt2.setText("4 left");
                            }
                            if (count == 11) {
                                utrani4.stop();
                                u_tright_line4.setBackgroundResource(R.drawable.line1finish);
                                u_tright_line5.setBackgroundResource(R.drawable.underrightanim2);
                                utrani5 = (AnimationDrawable) u_tright_line5.getBackground();
                                utrani5.start();
                                u_tright_txt2.setText("3 left");
                            }
                            if (count == 12) {
                                utrani5.stop();
                                u_tright_line5.setBackgroundResource(R.drawable.line1finish);
                                u_tright_line6.setBackgroundResource(R.drawable.underrightanim2);
                                utrani6 = (AnimationDrawable) u_tright_line6.getBackground();
                                utrani6.start();
                                u_tright_txt2.setText("2 left");
                            }
                            if (count == 13) {
                                utrani6.stop();
                                u_tright_line6.setBackgroundResource(R.drawable.line1finish);
                                u_tright_line7.setBackgroundResource(R.drawable.underrightanim2);
                                utrani7 = (AnimationDrawable) u_tright_line7.getBackground();
                                utrani7.start();
                                u_tright_txt2.setText("1 left");
                            } if (count >= 14) {
                                u_tright_line7.setBackgroundResource(R.drawable.line1finish);
                                component_txt.setText("GOOD JOB");
                                u_tright_txt2.setText("DONE");
                                u_tright_txt2.setTextColor(Color.parseColor("#9E0958"));
                                data=25;
                                underrightstring="true";
                        }
                        if(count==15){
                            setData task = new setData();
                            task.execute("http://"+HomeActivity.IP_Address+"/saveTreat.php", "uneye_r");
                            if (! TreatActivity_underright2.this.isFinishing()) {
                                Intent intent = new Intent(getApplicationContext(),DoneActivity.class);
                                intent.putExtra("stringlist","underrright");
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

    public void onStart() {
        super.onStart();
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        underrightactivity=TreatActivity_underright2.this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treat_underright2);

        //값 받아오기

        content1 = findViewById(R.id.treatup_ur2);
        content2 = findViewById(R.id.treatdown_ur2);
        back=(ImageView)findViewById(R.id.backw_ur);
        u_tright_line1=(ImageView)findViewById(R.id.u_tright_line1);
        u_tright_line2=(ImageView)findViewById(R.id.u_tright_line2);
        u_tright_line3=(ImageView)findViewById(R.id.u_tright_line3);
        u_tright_line4=(ImageView)findViewById(R.id.u_tright_line4);
        u_tright_line5=(ImageView)findViewById(R.id.u_tright_line5);
        u_tright_line6=(ImageView)findViewById(R.id.u_tright_line6);
        u_tright_line7=(ImageView)findViewById(R.id.u_tright_line7);
        u_tright_line8=(ImageView)findViewById(R.id.u_tright_line8);
        u_tright_line9=(ImageView)findViewById(R.id.u_tright_line9);
        u_tright_line10=(ImageView)findViewById(R.id.u_tright_line10);
        u_tright_line11=(ImageView)findViewById(R.id.u_tright_line11);
        u_tright_line12=(ImageView)findViewById(R.id.u_tright_line12);
        u_tright_line13=(ImageView)findViewById(R.id.u_tright_line13);
        u_tright_txt1=(TextView)findViewById(R.id.u_tright_txt1);
        u_tright_txt2=(TextView)findViewById(R.id.u_tright_txt2);
        u_tleft_txt1=(TextView)findViewById(R.id.u_tleft_txt1);
        u_tleft_txt2=(TextView)findViewById(R.id.u_tleft_txt2);
        c_tright_txt1=(TextView)findViewById(R.id.c_tright_txt1);
        c_tright_txt2=(TextView)findViewById(R.id.c_tright_txt2);
        c_tleft_txt1=(TextView)findViewById(R.id.c_tleft_txt1);
        c_tleft_txt2=(TextView)findViewById(R.id.c_tleft_txt2);
        component_txt=findViewById(R.id.componenttxt_ur);
        backgroundimg = findViewById(R.id.background);
        animation();

    }

    public void onPause() {
        super.onPause();
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
