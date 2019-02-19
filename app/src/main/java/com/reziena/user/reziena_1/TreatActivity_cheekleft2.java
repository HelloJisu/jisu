package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.reziena.user.reziena_1.utils.RSBlurProcessor;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TreatActivity_cheekleft2 extends AppCompatActivity {

    RenderScript rs;
    Bitmap blurBitMap;
    private static Bitmap bitamp;
    public static Activity cheekleftactivity;
    String wrinkle_string;
    ImageView backgroundimg;
    String cheekleftstring,cheekrightstring;
    ImageView forehead, underleft, underright, eyeleft, eyeright, cheekl, cheekr, mouth, back;
    LinearLayout component;
    TextView component_txt,u_tright_txt1,u_tright_txt2,u_tleft_txt1,u_tleft_txt2,c_tright_txt1,c_tright_txt2,c_tleft_txt1,c_tleft_txt2;
    int cheekcount=0, data=0, level=0, timer_sec, count=0;
    ImageView c_tleft_line1,c_tleft_line2,c_tleft_line3,c_tleft_line4,c_tleft_line5,c_tleft_line6,c_tleft_line7,c_tleft_line8
            ,c_tleft_line9,c_tleft_line10,c_tleft_line11,c_tleft_line12,c_tleft_line13,c_tleft_line14,c_tleft_line15,c_tleft_line16,c_tleft_line17
            ,c_tleft_line18,c_tleft_line19,c_tleft_line20,c_tleft_line21,c_tleft_line22;
    TimerTask second;
    String part;
    AnimationDrawable ctlani1,ctlani2,ctlani3,ctlani4,ctlani5,ctlani6,ctlani7,ctlani8,ctlani9,ctlani10,ctlani11,ctlani12,ctlani13,ctlani14,ctlani15
            ,ctlani16,ctlani17,ctlani18,ctlani19,ctlani20,ctlani21,ctlani22;
    static String finish;
    ImageView content1, content2;

    private String IP_Address = "52.32.36.182";

    public void animation() {
        second = new TimerTask() {
            @Override
            public void run() {
                //Log.e("카운터_anitimer",String.valueOf(count));
                count++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            if (count == 1) {
                                String str = "THIS COLUMN HAS 8 LINES.\nPLACE THE DEVICE TO THE COLORED LINE AS\nSHOWN. AND PRESS THE CENTER BUTTON TO\nSTART TREATING ONE LINE";
                                SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                                ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 72, 126, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                component_txt.setText(ssb);
                                c_tleft_line15.setBackgroundResource(R.drawable.cheekleftanim1);
                                ctlani15 = (AnimationDrawable) c_tleft_line15.getBackground();
                                ctlani15.start();
                            }
                            if (count == 2) {
                                ctlani15.stop();
                                c_tleft_line15.setBackgroundResource(R.drawable.line321finish);
                                c_tleft_line16.setBackgroundResource(R.drawable.cheekleftanim1);
                                ctlani16 = (AnimationDrawable) c_tleft_line16.getBackground();
                                ctlani16.start();
                                c_tleft_txt1.setText("7 left");
                            }
                            if (count == 3) {
                                ctlani16.stop();
                                c_tleft_line16.setBackgroundResource(R.drawable.line321finish);
                                c_tleft_line17.setBackgroundResource(R.drawable.cheekleftanim1);
                                ctlani17 = (AnimationDrawable) c_tleft_line17.getBackground();
                                ctlani17.start();
                                c_tleft_txt1.setText("6 left");
                            }
                            if (count == 4) {
                                ctlani17.stop();
                                c_tleft_line17.setBackgroundResource(R.drawable.line321finish);
                                c_tleft_line18.setBackgroundResource(R.drawable.cheekleftanim1);
                                ctlani18 = (AnimationDrawable) c_tleft_line18.getBackground();
                                ctlani18.start();
                                c_tleft_txt1.setText("5 left");
                            }
                            if (count == 5) {
                                ctlani18.stop();
                                c_tleft_line18.setBackgroundResource(R.drawable.line321finish);
                                c_tleft_line19.setBackgroundResource(R.drawable.cheekleftanim1);
                                ctlani19 = (AnimationDrawable) c_tleft_line19.getBackground();
                                ctlani19.start();
                                c_tleft_txt1.setText("4 left");
                            }
                            if (count == 6) {
                                ctlani19.stop();
                                c_tleft_line19.setBackgroundResource(R.drawable.line321finish);
                                c_tleft_line20.setBackgroundResource(R.drawable.cheekleftanim1);
                                ctlani20 = (AnimationDrawable) c_tleft_line20.getBackground();
                                ctlani20.start();
                                c_tleft_txt1.setText("3 left");
                            }
                            if (count == 7) {
                                ctlani20.stop();
                                c_tleft_line20.setBackgroundResource(R.drawable.line321finish);
                                c_tleft_line21.setBackgroundResource(R.drawable.cheekleftanim1);
                                ctlani21 = (AnimationDrawable) c_tleft_line21.getBackground();
                                ctlani21.start();
                                c_tleft_txt1.setText("2 left");
                            }
                            if (count == 8) {
                                ctlani21.stop();
                                c_tleft_line21.setBackgroundResource(R.drawable.line321finish);
                                c_tleft_line22.setBackgroundResource(R.drawable.cheekleftanim1);
                                ctlani22 = (AnimationDrawable) c_tleft_line22.getBackground();
                                ctlani22.start();
                                c_tleft_txt1.setText("1 left");
                            }
                            if (count == 9) {
                                String str = "THIS COLUMN HAS 14 LINES.\nPLACE THE DEVICE TO THE COLORED LINE AS\nSHOWN. AND PRESS THE CENTER BUTTON TO\nSTART TREATING ONE LINE";
                                SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                                ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 73, 127, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                component_txt.setText(ssb);
                                ctlani22.stop();
                                c_tleft_line22.setBackgroundResource(R.drawable.line321finish);
                                c_tleft_line1.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani1 = (AnimationDrawable) c_tleft_line1.getBackground();
                                ctlani1.start();
                                c_tleft_txt1.setText("DONE");
                                c_tleft_txt1.setTextColor(Color.parseColor("#9E0958"));
                            }
                            if (count == 10) {
                                ctlani1.stop();
                                c_tleft_line1.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line2.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani2 = (AnimationDrawable) c_tleft_line2.getBackground();
                                ctlani2.start();
                                c_tleft_txt2.setText("13 left");
                            }
                            if (count == 11) {
                                ctlani2.stop();
                                c_tleft_line2.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line3.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani3 = (AnimationDrawable) c_tleft_line3.getBackground();
                                ctlani3.start();
                                c_tleft_txt2.setText("12 left");
                            }
                            if (count == 12) {
                                ctlani3.stop();
                                c_tleft_line3.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line4.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani4 = (AnimationDrawable) c_tleft_line4.getBackground();
                                ctlani4.start();
                                c_tleft_txt2.setText("11 left");
                            }
                            if (count == 13) {
                                ctlani4.stop();
                                c_tleft_line4.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line5.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani5 = (AnimationDrawable) c_tleft_line5.getBackground();
                                ctlani5.start();
                                c_tleft_txt2.setText("10 left");
                            }
                            if (count == 14) {
                                ctlani5.stop();
                                c_tleft_line5.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line6.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani6 = (AnimationDrawable) c_tleft_line6.getBackground();
                                ctlani6.start();
                                c_tleft_txt2.setText("9 left");
                            }
                            if (count == 15) {
                                ctlani6.stop();
                                c_tleft_line6.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line7.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani7 = (AnimationDrawable) c_tleft_line7.getBackground();
                                ctlani7.start();
                                c_tleft_txt2.setText("8 left");
                            }
                            if (count == 16) {
                                ctlani7.stop();
                                c_tleft_line7.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line8.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani8 = (AnimationDrawable) c_tleft_line8.getBackground();
                                ctlani8.start();
                                c_tleft_txt2.setText("7 left");
                            }
                            if (count == 17) {
                                ctlani8.stop();
                                c_tleft_line8.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line9.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani9 = (AnimationDrawable) c_tleft_line9.getBackground();
                                ctlani9.start();
                                c_tleft_txt2.setText("6 left");
                            }
                            if (count == 18) {
                                ctlani9.stop();
                                c_tleft_line9.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line10.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani10 = (AnimationDrawable) c_tleft_line10.getBackground();
                                ctlani10.start();
                                c_tleft_txt2.setText("5 left");
                            }
                            if (count == 19) {
                                ctlani10.stop();
                                c_tleft_line10.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line11.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani11 = (AnimationDrawable) c_tleft_line11.getBackground();
                                ctlani11.start();
                                c_tleft_txt2.setText("4 left");
                            }
                            if (count == 20) {
                                ctlani11.stop();
                                c_tleft_line11.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line12.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani12 = (AnimationDrawable) c_tleft_line12.getBackground();
                                ctlani12.start();
                                c_tleft_txt2.setText("3 left");
                            }
                            if (count == 21) {
                                ctlani12.stop();
                                c_tleft_line12.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line13.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani13 = (AnimationDrawable) c_tleft_line13.getBackground();
                                ctlani13.start();
                                c_tleft_txt2.setText("2 left");
                            }
                            if (count == 22) {
                                ctlani13.stop();
                                c_tleft_line13.setBackgroundResource(R.drawable.line5finish);
                                c_tleft_line14.setBackgroundResource(R.drawable.cheekleftanim2);
                                ctlani14 = (AnimationDrawable) c_tleft_line14.getBackground();
                                ctlani14.start();
                                c_tleft_txt2.setText("1 left");
                            }if (count > 22) {
                            c_tleft_line14.setBackgroundResource(R.drawable.line5finish);
                            component_txt.setText("GOOD JOB");
                            c_tleft_txt2.setText("DONE");
                            c_tleft_txt2.setTextColor(Color.parseColor("#9E0958"));
                            data = 25;
                            cheekleftstring="true";
                        }
                            if(count==23){

                                setData task = new setData();
                                task.execute("http://"+IP_Address+"/saveTreat.php", "cheek_l");

                                //checkTreat task = new checkTreat();
                                //task.execute("http://"+IP_Address+"/checkTreat.php", "cheek_l");

                                if (! TreatActivity_cheekleft2.this.isFinishing()) {
                                    Intent intent = new Intent(getApplicationContext(),DoneActivity.class);
                                    intent.putExtra("stringlist","cheekleft");
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treat_cheekleft2);

        cheekleftactivity = TreatActivity_cheekleft2.this;

        //값 받아오기

        content1 = findViewById(R.id.treatup_cl2);
        content2 = findViewById(R.id.treatdown_cl2);
        back=(ImageView)findViewById(R.id.backw_cl);
        c_tleft_line1=(ImageView)findViewById(R.id.c_tleft_line1);
        c_tleft_line2=(ImageView)findViewById(R.id.c_tleft_line2);
        c_tleft_line3=(ImageView)findViewById(R.id.c_tleft_line3);
        c_tleft_line4=(ImageView)findViewById(R.id.c_tleft_line4);
        c_tleft_line5=(ImageView)findViewById(R.id.c_tleft_line5);
        c_tleft_line6=(ImageView)findViewById(R.id.c_tleft_line6);
        c_tleft_line7=(ImageView)findViewById(R.id.c_tleft_line7);
        c_tleft_line8=(ImageView)findViewById(R.id.c_tleft_line8);
        c_tleft_line9=(ImageView)findViewById(R.id.c_tleft_line9);
        c_tleft_line10=(ImageView)findViewById(R.id.c_tleft_line10);
        c_tleft_line11=(ImageView)findViewById(R.id.c_tleft_line11);
        c_tleft_line12=(ImageView)findViewById(R.id.c_tleft_line12);
        c_tleft_line13=(ImageView)findViewById(R.id.c_tleft_line13);
        c_tleft_line14=(ImageView)findViewById(R.id.c_tleft_line14);
        c_tleft_line15=(ImageView)findViewById(R.id.c_tleft_line15);
        c_tleft_line16=(ImageView)findViewById(R.id.c_tleft_line16);
        c_tleft_line17=(ImageView)findViewById(R.id.c_tleft_line17);
        c_tleft_line18=(ImageView)findViewById(R.id.c_tleft_line18);
        c_tleft_line19=(ImageView)findViewById(R.id.c_tleft_line19);
        c_tleft_line20=(ImageView)findViewById(R.id.c_tleft_line20);
        c_tleft_line21=(ImageView)findViewById(R.id.c_tleft_line21);
        c_tleft_line22=(ImageView)findViewById(R.id.c_tleft_line22);
        u_tleft_txt1=(TextView)findViewById(R.id.u_tleft_txt1);
        u_tleft_txt2=(TextView)findViewById(R.id.u_tleft_txt2);
        c_tright_txt1=(TextView)findViewById(R.id.c_tright_txt1);
        c_tright_txt2=(TextView)findViewById(R.id.c_tright_txt2);
        c_tleft_txt1=(TextView)findViewById(R.id.c_tleft_txt1);
        c_tleft_txt2=(TextView)findViewById(R.id.c_tleft_txt2);
        component_txt=findViewById(R.id.componenttxt_cl);
        backgroundimg=findViewById(R.id.background);

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
