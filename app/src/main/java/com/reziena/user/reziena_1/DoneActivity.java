package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextClock;
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
import java.util.Date;
import java.util.Locale;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

import static java.security.AccessController.getContext;

@SuppressLint("ValidFragment")
public class DoneActivity extends AppCompatActivity implements View.OnClickListener {
    MyDialogListener dialogListener;
    private static final String BUNDLE_KEY_DOWN_SCALE_FACTOR = "bundle_key_down_scale_factor";
    private static final String BUNDLE_KEY_BLUR_RADIUS = "bundle_key_blur_radius";
    private static final String BUNDLE_KEY_DIMMING = "bundle_key_dimming_effect";
    private static final String BUNDLE_KEY_DEBUG = "bundle_key_debug_effect";
    private static final String BUNDLE_KEy_STRING = "bundle_key_string_effect";
    private int mRadius;
    private float mDownScaleFactor;
    private boolean mDimming;
    private boolean mDebug;
    //하하하 이 시발
    //상아야 눈무난다
    private Context context;
    String donestring;
    Intent intent;
    String stringlist;
    TextView positive, non_positive;
    TreatActivity_cheekleft2 cheekleft = (TreatActivity_cheekleft2) TreatActivity_cheekleft2.cheekleftactivity;
    TreatActivity_cheekright2 cheekright = (TreatActivity_cheekright2) TreatActivity_cheekright2.cheekrightactivity;
    TreatActivity_underleft2 underleft = (TreatActivity_underleft2) TreatActivity_underleft2.underleftativity;
    TreatActivity_underright2 underright = (TreatActivity_underright2) TreatActivity_underright2.underrightactivity;
    HomeActivity homeActivity = (HomeActivity) HomeActivity.homeactivity;
    TreatActivity treatactivity = (TreatActivity) TreatActivity.treatactivity;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference underrightdata, underleftdata, cheekleftdata, cheekrightdata;
    public String underrightstring, underleftstring, cheekrightstring, cheekleftstring;
    TextView oppositTxT;

    boolean uneye_l=false, uneye_r=false, cheek_l=false, cheek_r=false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatfinish_ur);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.0f;
        getWindow().setAttributes(lpWindow);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 100%
        int height = (int) (display.getHeight() * 0.4);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        Intent subintent = getIntent();

        stringlist = subintent.getExtras().getString("stringlist");

        positive = findViewById(R.id.positive);
        non_positive = findViewById(R.id.non_positive);

        positive.setOnClickListener(this);
        non_positive.setOnClickListener(this);

        underrightdata = databaseReference.child("result").child("underrightstring");
        underleftdata = databaseReference.child("result").child("underleftstring");
        cheekleftdata = databaseReference.child("result").child("cheekleftstring");
        cheekrightdata = databaseReference.child("result").child("cheekrightstring");

        oppositTxT = findViewById(R.id.oppositTxT);

        Log.e("underright", String.valueOf(underrightstring));
        Log.e("underleft", String.valueOf(underleftstring));
        Log.e("cheekrightt", String.valueOf(cheekrightstring));
        Log.e("cheekleft", String.valueOf(cheekleftstring));

        cheekrightdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string = dataSnapshot.getValue(String.class);
                cheekrightstring = string;
                if (stringlist.equals("cheekleft")) {
                    if (cheekrightstring.equals("true")) {
                        oppositTxT.setText("finish");
                        non_positive.setText("Main");
                        positive.setText("Treat");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        cheekleftdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string = dataSnapshot.getValue(String.class);
                cheekleftstring = string;
                if (stringlist.equals("cheekright")) {
                    if (cheekleftstring.equals("true")) {
                        oppositTxT.setText("finish");
                        non_positive.setText("Main");
                        positive.setText("Treat");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        underleftdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string = dataSnapshot.getValue(String.class);
                underleftstring = string;
                if (stringlist.equals("underright")) {
                    if (underleftstring.equals("true")) {
                        oppositTxT.setText("finish");
                        non_positive.setText("Main");
                        positive.setText("Treat");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        underrightdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string = dataSnapshot.getValue(String.class);
                underrightstring = string;
                if (stringlist.equals("underleft")) {
                    if (underrightstring.equals("true")) {
                        oppositTxT.setText("finish");
                        non_positive.setText("Main");
                        positive.setText("Treat");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onResume() {
        super.onResume();
        GetData task = new GetData();
        task.execute("http://"+HomeActivity.IP_Address+"/callingTreat.php", "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.positive:
                Log.e("onclick", "positive");
                if (stringlist.equals("underright")) {
                    if(oppositTxT.getText().equals("finish")){
                        Log.e("선택한거", "finish");
                        intent = new Intent(v.getContext(), TreatActivity.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                    else {
                        Log.e("선택한거", "finish  X");
                        //HomeActivity.send("uneye_l->start");
                        intent = new Intent(v.getContext(), TreatActivity_underleft2.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                }
                if (stringlist.equals("underleft")) {
                    if(oppositTxT.getText().equals("finish")){
                        intent = new Intent(v.getContext(), TreatActivity.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                    else {
                        //HomeActivity.send("uneye_r->start");
                        intent = new Intent(v.getContext(), TreatActivity_underright2.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                }
                if (stringlist.equals("cheekright")) {
                    if(oppositTxT.getText().equals("finish")){
                        intent = new Intent(v.getContext(), TreatActivity.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                    else {
                       // HomeActivity.send("cheek_l->start");
                        intent = new Intent(v.getContext(), TreatActivity_cheekleft2.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                }
                if (stringlist.equals("cheekleft")) {
                    if(oppositTxT.getText().equals("finish")){
                        intent = new Intent(v.getContext(), TreatActivity.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                    else {
                        //HomeActivity.send("cheek_r->start");
                        intent = new Intent(v.getContext(), TreatActivity_cheekright2.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                }
                finish();
                break;
            case R.id.non_positive:
                Log.e("onclick", "non_positive");
                if (stringlist.equals("underright")) {
                    intent = new Intent(v.getContext(), TreatActivity.class);
                    v.getContext().startActivity(intent);
                    homeActivity.finish();
                    finish();
                }
                if (stringlist.equals("underleft")) {
                    intent = new Intent(v.getContext(), TreatActivity.class);
                    v.getContext().startActivity(intent);
                    homeActivity.finish();
                    finish();
                }
                if (stringlist.equals("cheekright")) {
                    intent = new Intent(v.getContext(), TreatActivity.class);
                    v.getContext().startActivity(intent);
                    homeActivity.finish();
                    finish();
                }
                if (stringlist.equals("cheekleft")) {
                    intent = new Intent(v.getContext(), TreatActivity.class);
                    v.getContext().startActivity(intent);
                    homeActivity.finish();
                    finish();
                }
                break;
        }
    }

    class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String getResult) {
            super.onPostExecute(getResult);

            Log.e("treat1-", "onPostExecute - " + getResult);

            if (getResult==null) {}
            else if (getResult.contains("No_results")) {}
            else {
                setResult(getResult);
                // underleft
                if (stringlist.equals("underleft")) {
                    if (!uneye_r) {
                        // uneye_r을 안했으면
                        Log.e("현재: under_left", "근데 너 uneye_r안햇어"+String.valueOf(uneye_r));
                        oppositTxT.setText("opposit");
                        non_positive.setText("no");
                        positive.setText("Treat_right");
                    } else {
                        // 했으면
                        Log.e("현재: under_left", "근데 너 uneye_r햇어"+String.valueOf(uneye_r));
                        oppositTxT.setText("finish");
                        non_positive.setText("------");
                        positive.setText("go home");
                    }
                }
                // underright
                if (stringlist.equals("underright")) {
                    if (!uneye_l) {
                        // uneye_l을 안했으면
                        Log.e("현재: under_right", "근데 너 uneye_l안햇어"+String.valueOf(uneye_l));
                        oppositTxT.setText("opposit");
                        non_positive.setText("no");
                        positive.setText("Treat_left");
                    } else {
                        // 했으면
                        Log.e("현재: under_right", "근데 너 uneye_l햇어"+String.valueOf(uneye_l));
                        oppositTxT.setText("finish");
                        non_positive.setText("------");
                        positive.setText("go home");
                    }
                }
                // cheekl
                if (stringlist.equals("cheekleft")) {
                    if (!cheek_r) {
                        // cheek_r을 안했으면
                        oppositTxT.setText("opposit");
                        non_positive.setText("no");
                        positive.setText("Treat_left");
                    } else {
                        // 했으면
                        oppositTxT.setText("finish");
                        non_positive.setText("------");
                        positive.setText("go home");
                    }
                }
                // cheekr
                if (stringlist.equals("cheekright")) {
                    if (!cheek_l) {
                        // cheek_l을 안했으면
                        oppositTxT.setText("opposit");
                    } else {
                        // 했으면
                        oppositTxT.setText("finish");
                        non_positive.setText("------");
                        positive.setText("go home");
                    }
                }
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

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.e("treat1-postParameters", postParameters);
                outputStream.flush();
                outputStream.close();

                InputStream inputStream;
                int responseStatusCode = httpURLConnection.getResponseCode();
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                    Log.e("treat1-response", "code - HTTP_OK - " + responseStatusCode);
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                    Log.e("treat1-response", "code - HTTP_NOT_OK - " + responseStatusCode);
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
                Log.e("treat1-error-stream", e.getMessage());
            }
            return null;
        }

        private void setResult(String result) {
            if (result.contains("uneye_l")) uneye_l=true;
            if (result.contains("uneye_r")) uneye_r=true;
            if (result.contains("cheek_l")) cheek_l=true;
            if (result.contains("cheek_r")) cheek_r=true;
            Log.e("un_l, un_r, ch_l, ch_r", String.valueOf(uneye_l)+String.valueOf(uneye_r)+String.valueOf(cheek_l)+String.valueOf(cheek_r));
        }

        /*private void showResult(String result){
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("getData");

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);
                    treatResult+=item.getString("value");
                }
                Log.e("treatResult", treatResult);

            } catch (JSONException e) {
                Log.d("treat1-JSON", "showResult : "+e.getMessage());
            }

        }*/
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
        //super.onBackPressed();
    }
}