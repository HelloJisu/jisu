package com.reziena.user.reziena_1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class BTOnActivity extends AppCompatActivity {

    int countDown;
    TextView txt1, txt2, txt3;
    Handler mHandler;
    Thread t;
    Button retry, no_retry;
    boolean isAlive;
    String action="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bton);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        retry = findViewById(R.id.retry);
        no_retry = findViewById(R.id.no_retry);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 100%
        int height = (int) (display.getHeight() * 0.4);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        Log.e("aakkkkk", "init");

        mHandler = new Handler();

        txt1.setText("기기없음\n앱과 연결하려면 기기를 켜세요\n기기를 켜면 BLUE불빛이 30초동안 반짝 거릴겁니다\n");

        startThread();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.no_retry:
                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.retry:
                        startThread();
                        break;
                }
            }
        };
        retry.setOnClickListener(onClickListener);
        no_retry.setOnClickListener(onClickListener);

    }

    private void startThread() {
        Log.e("init", "startThread");
        countDown = 45;

        txt2.setText(countDown+"초");
        retry.setVisibility(View.INVISIBLE);
        no_retry.setVisibility(View.INVISIBLE);

        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (countDown>0) {
                    countDown--;
                    Log.e("countdown", String.valueOf(countDown));
                    if (action.equals("DISCOVERY_FINISHED")) {
                        t.interrupt();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    // UI 작업 X
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // UI 작업 O
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // UI 작업 O
                            txt3.setText(countDown+"초");
                            if (countDown<=0) {
                                txt2.setText("디바이스를 못 찾았어요");
                                retry.setVisibility(View.VISIBLE);
                                no_retry.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                if (countDown<=0) {
                    unregisterReceiver(mBroadcastReceiver1);
                    t.interrupt();
                }
            }
        });
        t.start();
        discoveryStart();
    }

    private void discoveryStart() {
        Log.e("init: ", "discoveryStart!!;");

        /** Filtering Broadcast Receiver */
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(BluetoothDevice.ACTION_FOUND);
        filter1.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter1.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        /** Start Broadcast Receiver */
        this.registerReceiver(mBroadcastReceiver1, filter1);

        HomeActivity.mBtAdapter.startDiscovery();
    }

    public boolean dispatchTouchEvent(MotionEvent ev){
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);
        if(!dialogBounds.contains((int)ev.getX(),(int) ev.getY())){
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            action = intent.getAction();
            Log.e("BT", "onReceive: ACTION____________come in Receiver1");
            Log.e("action", action);

            /** When Deviece Found */
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.e("BT", "action_found");
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                if (device.getName()!=null) {
                    if (device.getName().equals(HomeActivity.devName)) {
                        Log.e(HomeActivity.devName, "디바이스 찾ㅇa!");
                        t.interrupt();
                        unregisterReceiver(mBroadcastReceiver1);
                        HomeActivity back = new HomeActivity();
                        back.connectToDevice(device.getAddress());
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.e("꺅!!", action);
                t.interrupt();
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            }
        }
    };
}
