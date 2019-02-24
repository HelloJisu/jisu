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
import java.util.TimerTask;import java.util.UUID;

public class BTOnActivity extends AppCompatActivity {

    int countDown;
    TextView txt1, txt2, txt3;
    Handler mHandler;
    Thread t;
    Button retry, no_retry;
    boolean isFound=false;
    String action="";
    BluetoothDevice device;
    private static final UUID MY_UUID = UUID.fromString("00000003-0000-1000-8000-00805f9b34fb");

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
                    // UI 작업 X
                    try {
                        t.sleep(1000);
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
                /*if (countDown<=0) {
                    unregisterReceiver(mBroadcastReceiver1);
                    t.interrupt();
                }*/
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

    private void bond() {
        Log.e("init: ", "bonding!!;");

        /** Filtering Broadcast Receiver */
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        /** Start Broadcast Receiver */
        this.registerReceiver(mBroadcastReceiver2, filter2);
    }

    public boolean connectToDevice(String address) {
        device = HomeActivity.mBtAdapter.getRemoteDevice(address);

        /** Filtering Broadcast Receiver */
        IntentFilter filter3 = new IntentFilter();
        filter3.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter3.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter3.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter3.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        /** Start Broadcast Receiver */
        this.registerReceiver(mBroadcastReceiver3, filter3);

        if (HomeActivity.mBtAdapter == null || address == null) {
            Log.e("BT", "mBtAdapter==null & address==null");
            return false;
        }

        Log.e("BT", "startBTConnection: initializing RFCOM Bluetooth Connection");

        BluetoothConnectionService mBluetoothConnection = new BluetoothConnectionService(getApplicationContext());
        mBluetoothConnection.startClient(device, MY_UUID);

        //discoveryStart();

        return true;
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
                device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                if (device.getName()!=null) {
                    //Log.e("action_foundt", HomeActivity.devName);
                    if (device.getName().equals("상아")) {
                        isFound = true;
                        Log.e(HomeActivity.devName, "디바이스 찾ㅇa!");
                        t.interrupt();
                        unregisterReceiver(mBroadcastReceiver1);
                        if (device.getBondState()==BluetoothDevice.BOND_NONE) {
                            Log.e("bondedState?!", String.valueOf(device.getBondState())+"BOND_NONE");
                            device.createBond();
                            bond();

                        } else if (device.getBondState()==BluetoothDevice.BOND_BONDED) {
                            Log.e("bondedState?!", String.valueOf(device.getBondState())+"BOND_BONDED");// BOND_BONDED -> 12
                            connectToDevice(device.getAddress());
                        }
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

    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            action = intent.getAction();
            Log.e("BT", "onReceive: ACTION____________come in mBroadcastReceiver2");
            Log.e("action", action);


            device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device.getBondState()==BluetoothDevice.BOND_NONE) {
                Log.e("BT", "BOND_NONE");
            } else if (device.getBondState()==BluetoothDevice.BOND_BONDED) {
                Log.e("bondedState?!", "BOND_BONDED");
                Log.e("Address", device.getAddress());
                connectToDevice(device.getAddress());
            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();
            Log.e("broadcasereceiver", "onReceive: ACTION____________come in Receiver3");
            //Log.e(TAG, "Now Action?::" + action);

            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Log.e("broadcasereceiver","Now Action?:: " + action);
                unregisterReceiver(mBroadcastReceiver3);
                Intent intent2 = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent2);
                Toast toast = Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT);
                toast.show();
            }
            else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction())) {
                Log.e("broadcasereceiver","Now Action?:: " + action);
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(intent.getAction())) {
                Log.e("broadcasereceiver","Now Action?:: " + action);
            }
            else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(intent.getAction())) {
                Log.e("broadcasereceiver","Now Action?:: " + action);
            }
        }
    };
}
