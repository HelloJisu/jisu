package com.reziena.user.reziena_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
import java.util.Locale;

public class SkintypeActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    HomeActivity homeactivity = (HomeActivity)HomeActivity.homeactivity;
    TextView next, pre, done, again, okay;
    SeekBar s1, s2, s3, s4;
    TextView q1, q2, q3, q4;
    TextView oily, dry, sensitive, resensitant, pigmented, nonpigmented, wrinkled, tight;
    View progress1, progress2, progress3, progress4;
    ArrayList<Integer> list = new ArrayList<>();
    LinearLayout imageButton;
    ImageView result1, result2, result3, result4;
    int page=1;
    double od=0.0, sr=0.0, pn=0.0, wt=0.0;
    String skin_type="";
    String detail="";
    LinearLayout content1, content2;
    int width;

    public SkintypeActivity() {
    }

    private void setPage() {
        if(page==5) {
            content1.setVisibility(View.INVISIBLE);
            content2.setVisibility(View.VISIBLE);
            pre.setVisibility(View.INVISIBLE); done.setVisibility(View.INVISIBLE); next.setVisibility(View.INVISIBLE);
            again.setVisibility(View.VISIBLE); okay.setVisibility(View.VISIBLE);
            setResult_detail();
        }
        else {
            content1.setVisibility(View.VISIBLE);
            content2.setVisibility(View.INVISIBLE);
            switch (page) {
                case 1:
                    q1.setText(R.string.q1);q2.setText(R.string.q2);q3.setText(R.string.q3);q4.setText(R.string.q4);
                    pre.setVisibility(View.INVISIBLE); done.setVisibility(View.INVISIBLE); next.setVisibility(View.VISIBLE); again.setVisibility(View.INVISIBLE); okay.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    q1.setText(R.string.q5);q2.setText(R.string.q6);q3.setText(R.string.q7);q4.setText(R.string.q8);
                    pre.setVisibility(View.VISIBLE); next.setVisibility(View.VISIBLE); done.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    q1.setText(R.string.q9);q2.setText(R.string.q10);q3.setText(R.string.q11);q4.setText(R.string.q12);
                    pre.setVisibility(View.VISIBLE); next.setVisibility(View.VISIBLE); done.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    q1.setText(R.string.q13);q2.setText(R.string.q14);q3.setText(R.string.q15);q4.setText(R.string.q16);
                    pre.setVisibility(View.VISIBLE); done.setVisibility(View.VISIBLE); next.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }

    private void reset() {
        oily.setTextColor(getResources().getColor(R.color.none)); dry.setTextColor(getResources().getColor(R.color.none));
        sensitive.setTextColor(getResources().getColor(R.color.none)); resensitant.setTextColor(getResources().getColor(R.color.none));
        pigmented.setTextColor(getResources().getColor(R.color.none)); nonpigmented.setTextColor(getResources().getColor(R.color.none));
        wrinkled.setTextColor(getResources().getColor(R.color.none)); tight.setTextColor(getResources().getColor(R.color.none));
        od=0; sr=0; pn=0; wt=0;
        for(int i=0; i<16; i++) {
            list.set(i, 0);
        }
        page=1; setPage(); setSeekbar(); skin_type=""; detail="";

    }

    private void setResult_detail() {
        if(skin_type.contains("O")) {
            oily.setTextColor(getResources().getColor(R.color.color1));
            result1.setImageResource(R.drawable.o);
        }
        if (skin_type.contains("D")) {
            dry.setTextColor(getResources().getColor(R.color.color1));
            result1.setImageResource(R.drawable.d);
        }
        if (skin_type.contains("S")) {
            sensitive.setTextColor(getResources().getColor(R.color.color2));
            result2.setImageResource(R.drawable.s);
        }
        if (skin_type.contains("R")) {
            resensitant.setTextColor(getResources().getColor(R.color.color2));
            result2.setImageResource(R.drawable.r);
        }
        if (skin_type.contains("P")) {
            pigmented.setTextColor(getResources().getColor(R.color.color3));
            result3.setImageResource(R.drawable.p);
        }
        if (skin_type.contains("N")) {
            nonpigmented.setTextColor(getResources().getColor(R.color.color3));
            result3.setImageResource(R.drawable.n);
        }
        if (skin_type.contains("W")) {
            wrinkled.setTextColor(getResources().getColor(R.color.color4));
            result4.setImageResource(R.drawable.w);
        }
        if (skin_type.contains("T")) {
            tight.setTextColor(getResources().getColor(R.color.color4));
            result4.setImageResource(R.drawable.t);
        }
        // 사이즈
        //int width = progress1.getWidth();

        int wod = (int)(width/4*od); int wsr = (int)(width/4*sr);
        int wpn = (int)(width/4*pn); int wwt = (int)(width/4*wt);

        if(wod==810) { progress1.getLayoutParams().width =  width; }
        else if(wod==0) { progress1.getLayoutParams().width =  50; }
        else { progress1.getLayoutParams().width =  wod; }

        if(wsr==810) { progress2.getLayoutParams().width =  width; }
        else if(wsr==0) { progress2.getLayoutParams().width =  50; }
        else { progress2.getLayoutParams().width =  wsr; }

        if(wpn==810) { progress3.getLayoutParams().width =  width; }
        else if(wpn==0) { progress3.getLayoutParams().width =  50; }
        else { progress3.getLayoutParams().width =  wpn; }

        if(wwt==810) { progress4.getLayoutParams().width =  width; }
        else if(wwt==0) { progress4.getLayoutParams().width =  50; }
        else { progress4.getLayoutParams().width =  wwt; }

        progress1.requestLayout();
        progress2.requestLayout();
        progress3.requestLayout();
        progress4.requestLayout();

        Log.i("______________________", String.valueOf(width));
        Log.i("od: ", String.valueOf(wod));
        Log.i("sr: ", String.valueOf(wsr));
        Log.i("pn: ", String.valueOf(wpn));
        Log.i("wt: ", String.valueOf(wwt));

        setData task = new setData();
        task.execute("http://"+HomeActivity.IP_Address+"/saveSkintype.php", skin_type);

    }

    class setData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String result = params[1];
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
            Date currentTime = new Date();
            String date = mSimpleDateFormat.format ( currentTime );

            SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
            String userID = sp_userID.getString("userID", "");
            String postParameters = "date="+date+"&id="+userID+"&result="+result;
            Log.e("skintype-postParameters", postParameters);

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);;

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.e("postParameters", postParameters);
                outputStream.flush();
                outputStream.close();

                // response
                int responseStatusCode = httpURLConnection.getResponseCode();
                String responseStatusMessage = httpURLConnection.getResponseMessage();
                Log.e("response-skintype", "POST response Code - " + responseStatusCode);
                Log.e("response-skintype", "POST response Message - "+ responseStatusMessage);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    // 정상적인 응답 데이터
                    inputStream = httpURLConnection.getInputStream();
                    Log.e("skintype-inputstream: ", "정상적");
                } else {
                    // error
                    inputStream = httpURLConnection.getErrorStream();
                    Log.e("skintype-inputstream: ", "비정상적: " + httpURLConnection.getErrorStream());
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line=bufferedReader.readLine()) != null) {
                    sb.append(line);
                    Log.e("read", String.valueOf(sb.append(line)));
                }

            } catch (Exception e) {
                Log.e("skintype-ERROR", "InsertDataError "+e.getMessage());
            }
            return null;
        }
    }

    private void setSeekbar() {
        switch (page) {
            case 1:
                s1.setProgress(list.get(0)); s2.setProgress(list.get(1)); s3.setProgress(list.get(2)); s4.setProgress(list.get(3));
                break;
            case 2:
                s1.setProgress(list.get(4)); s2.setProgress(list.get(5)); s3.setProgress(list.get(6)); s4.setProgress(list.get(7));
                break;
            case 3:
                s1.setProgress(list.get(8)); s2.setProgress(list.get(9)); s3.setProgress(list.get(10)); s4.setProgress(list.get(11));
                break;
            case 4:
                s1.setProgress(list.get(12)); s2.setProgress(list.get(13)); s3.setProgress(list.get(14)); s4.setProgress(list.get(15));
                break;
        }
    }

    private void saveSeekbar() {
        switch (page) {
            case 1:
                list.set(0, s1.getProgress()); list.set(1, s2.getProgress()); list.set(2, s3.getProgress()); list.set(3, s4.getProgress());
                break;
            case 2:
                list.set(4, s1.getProgress()); list.set(5, s2.getProgress()); list.set(6, s3.getProgress()); list.set(7, s4.getProgress());
                break;
            case 3:
                list.set(8, s1.getProgress()); list.set(9, s2.getProgress()); list.set(10, s3.getProgress()); list.set(11, s4.getProgress());
                break;
            case 4:
                list.set(12, s1.getProgress()); list.set(13, s2.getProgress()); list.set(14, s3.getProgress()); list.set(15, s4.getProgress());
                break;
        }
    }

    private void calculator() {

        // 값 더하기
        for (int i = 0; i < 16; i++) {
            switch (i) {
                case 0:
                    od += list.get(i);
                    od += 1;
                    break;
                case 1:
                    od += list.get(i);
                    od += 1;
                    break;
                case 2:
                    if (list.get(i) == 0) {
                        od += 4;
                    } else if (list.get(i) == 1) {
                        od += 3;
                    } else if (list.get(i) == 2) {
                        od += 2;
                    } else if (list.get(i) == 3) {
                        od += 1;
                    }
                    break;
                case 3:
                    od += list.get(i);
                    od += 1;
                    break;

                case 4:
                    sr += list.get(i);
                    sr += 1;
                    break;
                case 5:
                    sr += list.get(i);
                    sr += 1;
                    break;
                case 6:
                    sr += list.get(i);
                    sr += 1;
                    break;
                case 7:
                    sr += list.get(i);
                    sr += 1;
                    break;

                case 8:
                    pn += list.get(i);
                    pn += 1;
                    break;
                case 9:
                    pn += list.get(i);
                    pn += 1;
                    break;
                case 10:
                    pn += list.get(i);
                    pn += 1;
                    break;
                case 11:
                    pn += list.get(i);
                    pn += 1;
                    break;

                case 12:
                    wt += list.get(i);
                    wt += 1;
                    break;
                case 13:
                    wt += list.get(i);
                    wt += 1;
                    break;
                case 14:
                    wt += list.get(i);
                    wt += 1;
                    break;
                case 15:
                    if (list.get(i) == 0) {
                        wt += 4;
                    } else if (list.get(i) == 1) {
                        wt += 3;
                    } else if (list.get(i) == 2) {
                        wt += 2;
                    } else if (list.get(i) == 3) {
                        wt += 1;
                    }
                    break;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Log.i(i + "___ ", String.valueOf(list.get(i)));
        }

        Log.i("______________________", String.valueOf(list.size()));
        Log.i("od: ", String.valueOf(od));
        Log.i("sr: ", String.valueOf(sr));
        Log.i("pn: ", String.valueOf(pn));
        Log.i("wt: ", String.valueOf(wt));

        od = od / 4;
        sr = sr / 4;
        pn = pn / 4;
        wt = wt / 4;

        Log.i("______________________", String.valueOf(list.size()));
        Log.i("od: ", String.valueOf(od));
        Log.i("sr: ", String.valueOf(sr));
        Log.i("pn: ", String.valueOf(pn));
        Log.i("wt: ", String.valueOf(wt));

        if (od>2.5) skin_type+="O ";
        else if (od<2.5) skin_type+="D ";
        else {
            if (list.get(1)>2.5) skin_type+="O ";
            else skin_type+="D ";
        }

        if (sr>2.5) skin_type+="S ";
        else if (sr<2.5) skin_type+="R ";
        else {
            if (list.get(5)>2.5) skin_type+="S ";
            else skin_type+="R ";
        }

        if (pn>2.5) skin_type+="P ";
        else if (pn<2.5) skin_type+="N " ;
        else {
            if (list.get(8)>2.5) skin_type+="P ";
            else skin_type+="N ";
        }

        if (wt>2.5) skin_type+="W";
        else if (wt<2.5) skin_type+="T";
        else {
            if (list.get(12)>2.5) skin_type+="W";
            else skin_type+="T";
        }

        od = 4 - od;
        sr = 4 - sr;
        pn = 4 - pn;
        wt = 4 - wt;

        Log.i("skin_type:    ", skin_type);
        databaseReference.child("result").child("skintype").setValue(skin_type);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skintype);

        for(int i=0; i<16; i++) {
            list.add(0);
        }

        // popupt창 사이즈 지정
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.0f;
        getWindow().setAttributes(lpWindow);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 100%
        int height = (int) (display.getHeight() * 0.9);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        next = findViewById(R.id.next); pre = findViewById(R.id.pre); done = findViewById(R.id.done); again = findViewById(R.id.again); okay = findViewById(R.id.okay);
        s1 = findViewById(R.id.s1); s2 = findViewById(R.id.s2); s3 = findViewById(R.id.s3); s4 = findViewById(R.id.s4);
        q1 = findViewById(R.id.q1);  q2 = findViewById(R.id.q2); q3 = findViewById(R.id.q3);  q4 = findViewById(R.id.q4);
        imageButton = findViewById(R.id.imageButton); content1 = findViewById(R.id.content1); content2 = findViewById(R.id.content2);
        progress1 = findViewById(R.id.progress1); progress2 = findViewById(R.id.progress2); progress3 = findViewById(R.id.progress3); progress4 = findViewById(R.id.progress4);
        oily = findViewById(R.id.oily); dry = findViewById(R.id.dry); sensitive = findViewById(R.id.sensitive); resensitant = findViewById(R.id.resensitant);
        pigmented = findViewById(R.id.pigmented); nonpigmented = findViewById(R.id.nonpigmented); wrinkled = findViewById(R.id.wrinkled); tight = findViewById(R.id.tight);
        result1 = findViewById(R.id.result1); result2 = findViewById(R.id.result2); result3 = findViewById(R.id.result3); result4 = findViewById(R.id.result4);

        setPage();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.next:
                        saveSeekbar(); page++; setPage(); setSeekbar();
                        break;
                    case R.id.pre:
                        saveSeekbar(); page--; setSeekbar(); setPage();
                        break;
                    case R.id.done:
                        //Intent intent = new Intent(getApplicationContext(), SkintypeResultActivity.class);
                        //intent.putExtra("result", list);
                        //startActivity(intent);
                        saveSeekbar(); calculator(); page++; setPage();
                        break;
                    case R.id.again:
                        reset();
                        break;
                    case R.id.imageButton: case R.id.okay:
                        homeactivity.dashback.setImageResource(0);
                        homeactivity.backgroundimg.setImageResource(0);
                        finish();
                        break;
                }
            }
        };
        next.setOnClickListener(onClickListener);
        pre.setOnClickListener(onClickListener);
        done.setOnClickListener(onClickListener);
        imageButton.setOnClickListener(onClickListener);
        again.setOnClickListener(onClickListener);
        okay.setOnClickListener(onClickListener);
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
        homeactivity.dashback.setImageResource(0);
    }
}