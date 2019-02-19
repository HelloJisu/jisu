package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
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

import java.util.Timer;
import java.util.TimerTask;

public class TreatActivity extends AppCompatActivity {

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
    String part;
    public static Activity treatactivity;



    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference underrightdata,underleftdata,cheekleftdata,cheekrightdata;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treat);
        treatactivity=TreatActivity.this;

        underrightdata = databaseReference.child("result").child("underrightstring");
        underleftdata = databaseReference.child("result").child("underleftstring");
        cheekleftdata = databaseReference.child("result").child("cheekleftstring");
        cheekrightdata = databaseReference.child("result").child("cheekrightstring");

        Resources res = getResources();
        final Drawable  cheekrightimg= res.getDrawable(R.drawable.cheekrightimg);
        final Drawable  cheekleftimg= res.getDrawable(R.drawable.cheekleftimg);
        final Drawable  cheekunderrightimg= res.getDrawable(R.drawable.cheekunderimg);
        final Drawable  cheekunderleftimg= res.getDrawable(R.drawable.cheekunderleft);
        //값 받아오기

        forehead =  (ImageView)findViewById(R.id.forehead); underleft =  (ImageView)findViewById(R.id.underleft); underright =  (ImageView)findViewById(R.id.underright);
        cheekl =  (ImageView)findViewById(R.id.cheek_left); cheekr =  (ImageView)findViewById(R.id.cheek_right); mouth =  (ImageView)findViewById(R.id.mouth);
        eyeleft = (ImageView)findViewById(R.id.eyeleft); eyeright=(ImageView)findViewById(R.id.eyeright); component_txt=(TextView)findViewById(R.id.componenttxt);
        treatback = (RelativeLayout)findViewById(R.id.treatayout);
        back=(ImageView)findViewById(R.id.backw); u_tright_line1=(ImageView)findViewById(R.id.u_tright_line1); u_tright_line2=(ImageView)findViewById(R.id.u_tright_line2);
        u_tright_line3=(ImageView)findViewById(R.id.u_tright_line3); u_tright_line4=(ImageView)findViewById(R.id.u_tright_line4); u_tright_line5=(ImageView)findViewById(R.id.u_tright_line5);
        u_tright_line6=(ImageView)findViewById(R.id.u_tright_line6); u_tright_line7=(ImageView)findViewById(R.id.u_tright_line7); u_tright_line8=(ImageView)findViewById(R.id.u_tright_line8);
        u_tright_line9=(ImageView)findViewById(R.id.u_tright_line9); u_tright_line10=(ImageView)findViewById(R.id.u_tright_line10); u_tright_line11=(ImageView)findViewById(R.id.u_tright_line11);
        u_tright_line12=(ImageView)findViewById(R.id.u_tright_line12); u_tright_line13=(ImageView)findViewById(R.id.u_tright_line13); u_tleft_line1=(ImageView)findViewById(R.id.u_tleft_line1);
        u_tleft_line2=(ImageView)findViewById(R.id.u_tleft_line2); u_tleft_line3=(ImageView)findViewById(R.id.u_tleft_line3); u_tleft_line4=(ImageView)findViewById(R.id.u_tleft_line4);
        u_tleft_line5=(ImageView)findViewById(R.id.u_tleft_line5); u_tleft_line6=(ImageView)findViewById(R.id.u_tleft_line6); u_tleft_line7=(ImageView)findViewById(R.id.u_tleft_line7);
        u_tleft_line8=(ImageView)findViewById(R.id.u_tleft_line8); u_tleft_line9=(ImageView)findViewById(R.id.u_tleft_line9); u_tleft_line10=(ImageView)findViewById(R.id.u_tleft_line10);
        u_tleft_line11=(ImageView)findViewById(R.id.u_tleft_line11); u_tleft_line12=(ImageView)findViewById(R.id.u_tleft_line12); u_tleft_line13=(ImageView)findViewById(R.id.u_tleft_line13);
        c_tright_line1=(ImageView)findViewById(R.id.c_tright_line1); c_tright_line2=(ImageView)findViewById(R.id.c_tright_line2); c_tright_line3=(ImageView)findViewById(R.id.c_tright_line3);
        c_tright_line4=(ImageView)findViewById(R.id.c_tright_line4); c_tright_line5=(ImageView)findViewById(R.id.c_tright_line5); c_tright_line6=(ImageView)findViewById(R.id.c_tright_line6);
        c_tright_line7=(ImageView)findViewById(R.id.c_tright_line7); c_tright_line8=(ImageView)findViewById(R.id.c_tright_line8); c_tright_line9=(ImageView)findViewById(R.id.c_tright_line9);
        c_tright_line10=(ImageView)findViewById(R.id.c_tright_line10); c_tright_line11=(ImageView)findViewById(R.id.c_tright_line11); c_tright_line12=(ImageView)findViewById(R.id.c_tright_line12);
        c_tright_line13=(ImageView)findViewById(R.id.c_tright_line13); c_tright_line14=(ImageView)findViewById(R.id.c_tright_line14); c_tright_line15=(ImageView)findViewById(R.id.c_tright_line15);
        c_tright_line16=(ImageView)findViewById(R.id.c_tright_line16); c_tright_line17=(ImageView)findViewById(R.id.c_tright_line17); c_tright_line18=(ImageView)findViewById(R.id.c_tright_line18);
        c_tright_line19=(ImageView)findViewById(R.id.c_tright_line19); c_tright_line20=(ImageView)findViewById(R.id.c_tright_line20); c_tright_line21=(ImageView)findViewById(R.id.c_tright_line21);
        c_tright_line22=(ImageView)findViewById(R.id.c_tright_line22); c_tleft_line1=(ImageView)findViewById(R.id.c_tleft_line1); c_tleft_line2=(ImageView)findViewById(R.id.c_tleft_line2);
        c_tleft_line3=(ImageView)findViewById(R.id.c_tleft_line3); c_tleft_line4=(ImageView)findViewById(R.id.c_tleft_line4); c_tleft_line5=(ImageView)findViewById(R.id.c_tleft_line5);
        c_tleft_line6=(ImageView)findViewById(R.id.c_tleft_line6); c_tleft_line7=(ImageView)findViewById(R.id.c_tleft_line7); c_tleft_line8=(ImageView)findViewById(R.id.c_tleft_line8);
        c_tleft_line9=(ImageView)findViewById(R.id.c_tleft_line9); c_tleft_line10=(ImageView)findViewById(R.id.c_tleft_line10); c_tleft_line11=(ImageView)findViewById(R.id.c_tleft_line11);
        c_tleft_line12=(ImageView)findViewById(R.id.c_tleft_line12); c_tleft_line13=(ImageView)findViewById(R.id.c_tleft_line13); c_tleft_line14=(ImageView)findViewById(R.id.c_tleft_line14);
        c_tleft_line15=(ImageView)findViewById(R.id.c_tleft_line15); c_tleft_line16=(ImageView)findViewById(R.id.c_tleft_line16); c_tleft_line17=(ImageView)findViewById(R.id.c_tleft_line17);
        c_tleft_line18=(ImageView)findViewById(R.id.c_tleft_line18); c_tleft_line19=(ImageView)findViewById(R.id.c_tleft_line19); c_tleft_line20=(ImageView)findViewById(R.id.c_tleft_line20);
        c_tleft_line21=(ImageView)findViewById(R.id.c_tleft_line21); c_tleft_line22=(ImageView)findViewById(R.id.c_tleft_line22);
        u_tright_txt1=(TextView)findViewById(R.id.u_tright_txt1); u_tright_txt2=(TextView)findViewById(R.id.u_tright_txt2); u_tleft_txt1=(TextView)findViewById(R.id.u_tleft_txt1);
        u_tleft_txt2=(TextView)findViewById(R.id.u_tleft_txt2); c_tright_txt1=(TextView)findViewById(R.id.c_tright_txt1); c_tright_txt2=(TextView)findViewById(R.id.c_tright_txt2);
        c_tleft_txt1=(TextView)findViewById(R.id.c_tleft_txt1);c_tleft_txt2=(TextView)findViewById(R.id.c_tleft_txt2);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            Intent intent;

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {

                level=1;

                switch (v.getId()) {
                    case R.id.backw:
                            finish();
                        break;

                    case R.id.underright:
                        intent = new Intent(getBaseContext(), TreatActivity_underright.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.underleft:
                        intent = new Intent(getBaseContext(), TreatActivity_underleft.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.cheek_right:
                        intent = new Intent(getBaseContext(), TreatActivity_cheekright.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.cheek_left:
                        intent = new Intent(getBaseContext(), TreatActivity_cheekleft.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
            }
        };
        back.setOnClickListener(onClickListener);
        forehead.setOnClickListener(onClickListener);
        underleft.setOnClickListener(onClickListener);
        underright.setOnClickListener(onClickListener);
        cheekl.setOnClickListener(onClickListener);
        cheekr.setOnClickListener(onClickListener);
        mouth.setOnClickListener(onClickListener);
        eyeleft.setOnClickListener(onClickListener);
        eyeright.setOnClickListener(onClickListener);
    }

    public void onStart() {
        super.onStart();
        underleftdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                underleftstring=dataSnapshot.getValue(String.class);
                if (underleftstring.equals("true")) {
                    underleft.setEnabled(false);
                    underleft.setImageResource(R.drawable.underleftdone);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        underrightdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                underrightstring=dataSnapshot.getValue(String.class);
                if (underrightstring.equals("true")) {
                    underright.setEnabled(false);
                    underright.setImageResource(R.drawable.underrightdone);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        cheekrightdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cheekrightstring=dataSnapshot.getValue(String.class);
                if (cheekrightstring.equals("true")) {
                    cheekr.setEnabled(false);
                    cheekr.setImageResource(R.drawable.cheekrightdone);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        cheekleftdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cheekleftstring=dataSnapshot.getValue(String.class);
                if (cheekleftstring.equals("true")) {
                    cheekl.setEnabled(false);
                    cheekl.setImageResource(R.drawable.cheekleftdone);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

}
