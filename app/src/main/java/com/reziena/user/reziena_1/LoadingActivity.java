package com.reziena.user.reziena_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reziena.user.reziena_1.utils.RSBlurProcessor;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {


    RenderScript rs;
    RenderScript rs2;
    LinearLayout content1, content2;
    Bitmap blurBitMap, blurBitMap2;
    LayoutInflater treatunderright,treatunderright2,treatunderleft,treatunderleft2, treatcheekright,treatcheekright2,treatcheekleft,treatcheekleft2;

    private static Bitmap bitamp;
    private static Bitmap bitamp2;
    TextView loadingtxt;
    int count = -1;
    TimerTask second;
    ImageView treatunderrightcontent1,treatunderrightcontent2,treatunderright2content1 ,treatunderright2content2
            ,treatcheekrightcontent1 ,treatcheekrightcontent2 ,treatcheekright2content1 ,treatcheekright2content2
            ,treatcheekleftcontent1  ,treatcheekleftcontent2 ,treatcheekleft2content1 ,treatcheekleft2content2
            ,treatunderleftcontent1 ,treatunderleftcontent2 , treatunderleft2content1 , treatunderleft2content2;

    private void startLoading() {
        second = new TimerTask() {
            @Override
            public void run() {
                Log.e("카운터", String.valueOf(count));
                count++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (count) {
                            case 0:
                                loadingtxt.setText("GETTING YOUR SKIN CONDITION DATA.");
                                break;
                            case 1:
                                loadingtxt.setText("GETTING YOUR SKIN CONDITION DATA..");
                                break;
                            case 2:
                                loadingtxt.setText("GETTING YOUR SKIN CONDITION DATA...");
                                break;
                            case 3:
                                loadingtxt.setText("ADJUSTING THE LEVEL SETTING.");
                                break;
                            case 4:
                                loadingtxt.setText("ADJUSTING THE LEVEL SETTING..");
                                break;
                            case 5:
                                loadingtxt.setText("ADJUSTING THE LEVEL SETTING...");
                                break;
                            case 7:
                                loadingtxt.setText("MAPPING CARE ZONE.");
                                break;
                            case 8:
                                loadingtxt.setText("MAPPING CARE ZONE..");
                                break;
                            case 9:
                                loadingtxt.setText("MAPPING CARE ZONE...");
                                break;
                            case 10:
                                Intent intent = new Intent(getBaseContext(), TreatActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
                        }
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 300);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startLoading();
        content1 = findViewById(R.id.treatup);
        content2 = findViewById(R.id.treatdown);
        loadingtxt = (TextView)findViewById(R.id.loadingText);
    }

    public void screenshot(){
        rs = RenderScript.create(this);
        content1.setDrawingCacheEnabled(false);
        content1.setDrawingCacheEnabled(true);
        bitamp = content1.getDrawingCache();
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        blurBitMap = rsBlurProcessor.blur(bitamp, 20f, 3);

        }
    public void screenshot2(){
        rs2 = RenderScript.create(this);
        content2.setDrawingCacheEnabled(false);
        content2.setDrawingCacheEnabled(true);
        bitamp2 = content2.getDrawingCache();
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs2);
        blurBitMap2 = rsBlurProcessor.blur(bitamp2, 20f, 3);

    }
}