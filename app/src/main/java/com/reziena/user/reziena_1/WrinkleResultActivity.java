package com.reziena.user.reziena_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class WrinkleResultActivity extends AppCompatActivity {

  private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference databaseReference = firebaseDatabase.getReference();
  ImageButton imageButton;
  TextView okay, result_grade, result_per;
  String grade, per;
  HomeActivity homeactivity = (HomeActivity)HomeActivity.homeactivity;
  MainActivity mainActivity = (MainActivity)MainActivity.mainnactivity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wrinkle_result);

    WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
    lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    lpWindow.dimAmount = 0.0f;
    getWindow().setAttributes(lpWindow);

    Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 100%
    int height = (int) (display.getHeight() * 0.9);
    getWindow().getAttributes().width = width;
    getWindow().getAttributes().height = height;

    imageButton = findViewById(R.id.imageButton);
    okay = findViewById(R.id.okay);
    result_grade = findViewById(R.id.result_grade);
    result_per = findViewById(R.id.result_per);

    Random rand = new Random();
    int r = rand.nextInt(3);
    switch (r) {
      case 0:
        grade = "A"; per = "10%"; break;
      case 1:
        grade = "B"; per = "20%";break;
      case 2:
        grade = "C"; per = "30%";break;
    }


    result_grade.setText(grade);
    result_per.setText(per +"% of wrinkle \n detected");

    databaseReference.child("result").child("winkle").setValue(grade);

    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        switch (v.getId()) {
          case R.id.okay: case R.id.imageButton:
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            homeactivity.dashback.setImageResource(0);
            mainActivity.finish();
            finish();
        }
      }
    };
    imageButton.setOnClickListener(onClickListener);
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
    //super.onBackPressed();
  }
}