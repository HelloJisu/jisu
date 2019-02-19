package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
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
    TextView okay, finish;
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
    TextView finishtxt;



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

        okay = findViewById(R.id.yes_ur);
        finish = findViewById(R.id.cancle_ur);

        okay.setOnClickListener(this);
        finish.setOnClickListener(this);

        underrightdata = databaseReference.child("result").child("underrightstring");
        underleftdata = databaseReference.child("result").child("underleftstring");
        cheekleftdata = databaseReference.child("result").child("cheekleftstring");
        cheekrightdata = databaseReference.child("result").child("cheekrightstring");

        finishtxt = findViewById(R.id.finishtxt);


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
                        finishtxt.setText("finish");
                        finish.setText("Main");
                        okay.setText("Treat");
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
                        finishtxt.setText("finish");
                        finish.setText("Main");
                        okay.setText("Treat");
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
                if (stringlist.equals("underrright")) {
                    if (underleftstring.equals("true")) {
                        finishtxt.setText("finish");
                        finish.setText("Main");
                        okay.setText("Treat");
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
                        finishtxt.setText("finish");
                        finish.setText("Main");
                        okay.setText("Treat");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_ur:
                if (stringlist.equals("underrright")) {
                    if(finishtxt.getText().equals("finish")){
                        intent = new Intent(v.getContext(), TreatActivity.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                    else {
                        intent = new Intent(v.getContext(), TreatActivity_underleft2.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                }
                if (stringlist.equals("underleft")) {
                    if(finishtxt.getText().equals("finish")){
                        intent = new Intent(v.getContext(), TreatActivity.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                    else {
                        intent = new Intent(v.getContext(), TreatActivity_underright2.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                }
                if (stringlist.equals("cheekright")) {
                    if(finishtxt.getText().equals("finish")){
                        intent = new Intent(v.getContext(), TreatActivity.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                    else {
                        intent = new Intent(v.getContext(), TreatActivity_cheekleft2.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                }
                if (stringlist.equals("cheekleft")) {
                    if(finishtxt.getText().equals("finish")){
                        intent = new Intent(v.getContext(), TreatActivity.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                    else {
                        intent = new Intent(v.getContext(), TreatActivity_cheekright2.class);
                        v.getContext().startActivity(intent);
                        finish();
                    }
                }
                finish();
                break;
            case R.id.cancle_ur:
                if (stringlist.equals("underrright")) {
                    intent = new Intent(v.getContext(), HomeActivity.class);
                    v.getContext().startActivity(intent);
                    homeActivity.finish();
                    finish();
                }
                if (stringlist.equals("underleft")) {
                    intent = new Intent(v.getContext(), HomeActivity.class);
                    v.getContext().startActivity(intent);
                    homeActivity.finish();
                    finish();
                }
                if (stringlist.equals("cheekright")) {
                    intent = new Intent(v.getContext(), HomeActivity.class);
                    v.getContext().startActivity(intent);
                    homeActivity.finish();
                    finish();
                }
                if (stringlist.equals("cheekleft")) {
                    intent = new Intent(v.getContext(), HomeActivity.class);
                    v.getContext().startActivity(intent);
                    homeActivity.finish();
                    finish();
                }
                break;
        }
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