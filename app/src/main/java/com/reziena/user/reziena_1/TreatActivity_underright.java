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
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TreatActivity_underright extends AppCompatActivity {

  private String IP_Address = "52.32.36.182";
  String treatResult="";

  ImageView forehead, underleft, underright, eyeleft, eyeright, cheekl, cheekr, mouth, back;
  LinearLayout component;
  String underrightstring,underleftstring,cheekrightstring,cheekleftstring;
  TextView component_txt,u_tright_txt1,u_tright_txt2,u_tleft_txt1,u_tleft_txt2,c_tright_txt1,c_tright_txt2,c_tleft_txt1,c_tleft_txt2;
  RelativeLayout treatback, underright_layout, underleft_layout,treat_default,cheekright_layout,cheekleft_layout;
  int cheekcount=0, undercount=0, foreheadcount=0, level=0, timer_sec, count=0;
  ImageView u_tright_line1,u_tright_line2,u_tright_line3,u_tright_line4,u_tright_line5,u_tright_line6,
          u_tright_line7,u_tright_line8,u_tright_line9,u_tright_line10,u_tright_line11,u_tright_line12,u_tright_line13,
          u_tleft_line1,u_tleft_line2,u_tleft_line3,u_tleft_line4,u_tleft_line5,u_tleft_line6,
          u_tleft_line7,u_tleft_line8,u_tleft_line9,u_tleft_line10,u_tleft_line11,u_tleft_line12,u_tleft_line13
          ,c_tright_line1,c_tright_line2,c_tright_line3,c_tright_line4,c_tright_line5,c_tright_line6,c_tright_line7,c_tright_line8
          ,c_tright_line9,c_tright_line10,c_tright_line11,c_tright_line12,c_tright_line13,c_tright_line14,c_tright_line15,c_tright_line16,c_tright_line17
          ,c_tright_line18,c_tright_line19,c_tright_line20,c_tright_line21,c_tright_line22,c_tleft_line1,c_tleft_line2,c_tleft_line3,c_tleft_line4,c_tleft_line5,c_tleft_line6,c_tleft_line7,c_tleft_line8
          ,c_tleft_line9,c_tleft_line10,c_tleft_line11,c_tleft_line12,c_tleft_line13,c_tleft_line14,c_tleft_line15,c_tleft_line16,c_tleft_line17
          ,c_tleft_line18,c_tleft_line19,c_tleft_line20,c_tleft_line21,c_tleft_line22;
  TimerTask second;
  String part,wrinkle_string;
  public static Activity treatactivity;
  ImageView content1, content2;
  public static Activity treatunderright;

  private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference databaseReference = firebaseDatabase.getReference();
  private DatabaseReference underrightdata,underleftdata,cheekleftdata,cheekrightdata,wrinkle_txt;


  @SuppressLint("WrongViewCast")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_treat_underright);
    treatunderright = TreatActivity_underright.this;
    underrightdata = databaseReference.child("result").child("underrightstring");
    underleftdata = databaseReference.child("result").child("underleftstring");
    cheekleftdata = databaseReference.child("result").child("cheekleftstring");
    cheekrightdata = databaseReference.child("result").child("cheekrightstring");
    wrinkle_txt = databaseReference.child("result").child("winkle");
    Resources res = getResources();
    final Drawable  cheekrightimg= res.getDrawable(R.drawable.cheekrightimg);
    final Drawable  cheekleftimg= res.getDrawable(R.drawable.cheekleftimg);
    final Drawable  cheekunderrightimg= res.getDrawable(R.drawable.cheekunderimg);
    final Drawable  cheekunderleftimg= res.getDrawable(R.drawable.cheekunderleft);
    //값 받아오기
    content1 = findViewById(R.id.treatup_ur);
    content2 = findViewById(R.id.treatdown_ur);
    forehead =  (ImageView)findViewById(R.id.forehead_ur);
    underleft =  (ImageView)findViewById(R.id.underleft_ur);
    underright =  (ImageView)findViewById(R.id.underright_ur);
    cheekl =  (ImageView)findViewById(R.id.cheek_left_ur);
    cheekr =  (ImageView)findViewById(R.id.cheek_right_ur);
    mouth =  (ImageView)findViewById(R.id.mouth_ur);
    eyeleft = (ImageView)findViewById(R.id.eyeleft_ur);
    eyeright=(ImageView)findViewById(R.id.eyeright_ur);
    component_txt=(TextView)findViewById(R.id.componenttxt_ur);
    back=(ImageView)findViewById(R.id.backw_ur);
    View.OnClickListener onClickListener = new View.OnClickListener() {
      Intent intent;

      @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
      public void onClick(View v) {
        level=1;
        switch (v.getId()) {
          case R.id.backw_ur:
            intent = new Intent(getBaseContext(), TreatActivity.class);
            startActivity(intent);
            finish();
            break;

          case R.id.underright_ur:
            HomeActivity.send("uneye_r->start");
            intent = new Intent(getBaseContext(), TreatActivity_underright2.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
            break;
        }
      }
    };
    back.setOnClickListener(onClickListener);
    underright.setOnClickListener(onClickListener);
  }

  public void onStart() {
    super.onStart(); }

  public void onResume() {
    super.onResume();

    GetData task = new GetData();
    task.execute("http://"+IP_Address+"/callingTreat.php", "");

    GetData2 task2 = new GetData2();
    task2.execute("http://"+IP_Address+"/callingWrinkle.php", "");

  }

  class GetData extends AsyncTask<String, Void, String> {

    @Override
    protected void onPostExecute(String getResult) {
      super.onPostExecute(getResult);

      Log.e("treat3-", "onPostExecute - " + getResult);

      if (!getResult.contains("No_results")) {
        showResult(getResult);

        // cheekl
        if (treatResult.contains("uneye_l")) {
          underleft.setEnabled(false);
          underleft.setImageResource(R.drawable.underleftdone);
        }

        // cheekr
        if (treatResult.contains("uneye_r")) {
          underright.setEnabled(false);
          underright.setImageResource(R.drawable.underrightdone);
        }
      }
      else {
        Log.e("treat3-response:", "no result");
      }
    }

    @Override
    protected String doInBackground(String... params) {
      String serverURL = params[0];

      SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
      Date currentTime = new Date();
      String date = mSimpleDateFormat.format ( currentTime );

      SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
      String userID = sp_userID.getString("userID", "");
      String postParameters = "date="+date+"&id="+userID;
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
        Log.e("treat2-postParameters", postParameters);
        outputStream.flush();
        outputStream.close();

        InputStream inputStream;
        int responseStatusCode = httpURLConnection.getResponseCode();
        if(responseStatusCode == HttpURLConnection.HTTP_OK) {
          inputStream = httpURLConnection.getInputStream();
          Log.e("treat3-response", "code - HTTP_OK - " + responseStatusCode);
        }
        else{
          inputStream = httpURLConnection.getErrorStream();
          Log.e("treat3-response", "code - HTTP_NOT_OK - " + responseStatusCode);
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
        Log.e("treat3-error-stream", e.getMessage());
      }
      return null;
    }

    private void showResult(String result){
      try {
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("getData");

        for(int i=0;i<jsonArray.length();i++){

          JSONObject item = jsonArray.getJSONObject(i);
          treatResult+=item.getString("value");

          Log.e("treatResult: ", treatResult+"");
        }

      } catch (JSONException e) {
        Log.d("treatResult-JSON", "showResult : ", e);
      }
    }
  }

  class GetData2 extends AsyncTask<String, Void, String> {

    @Override
    protected void onPostExecute(String getResult) {
      super.onPostExecute(getResult);

      Log.e("wrinkle-", "onPostExecute - " + getResult);

      showResult(getResult);

      if (wrinkle_string.equals("A")) {
        level = 1;
      }
      if (wrinkle_string.equals("B")) {
        level = 2;
      }
      if (wrinkle_string.equals("C")) {
        level = 3;
      }
      if (level == 1) {
        underleft.setImageResource(R.drawable.underleftlevel1);
        underright.setImageResource(R.drawable.underrightlevel1);
        component_txt.setText("PLEASE SET THE DEVICE\nON LEVEL 1,\nAND SELECT STARTIG AREA");
      }
      if (level == 2) {
        underleft.setImageResource(R.drawable.underleftlevel2);
        underright.setImageResource(R.drawable.underrightlevel2);
        component_txt.setText("PLEASE SET THE DEVICE\nON LEVEL 2,\nAND SELECT STARTIG AREA");
      }
      if (level == 3) {
        underleft.setImageResource(R.drawable.underleftlevel3);
        underright.setImageResource(R.drawable.underrightlevel3);
        component_txt.setText("PLEASE SET THE DEVICE\nON LEVEL 3,\nAND SELECT STARTIG AREA");
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
      try {
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("getData");

        for(int i=0;i<jsonArray.length();i++){

          JSONObject item = jsonArray.getJSONObject(i);

          wrinkle_string = item.getString("level");
        }

      } catch (JSONException e) {
        Log.d("wrinkle-JSON", "showResult : ", e);
      }

    }
  }

}
