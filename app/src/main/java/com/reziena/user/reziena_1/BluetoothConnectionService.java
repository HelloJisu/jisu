package com.reziena.user.reziena_1;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.UUID;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BluetoothConnectionService {
    private static final String appName = "Reziena";

    String btTag = "BLUETOOTH_CONNECT";

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00000003-0000-1000-8000-00805f9b34fb");

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    private boolean success=false;

    private ConnectedThread mConnectedThread;

    public BluetoothConnectionService(Context context) {
        Log.e(btTag, "come in BluetoothConnectionService");
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.e(btTag, "BluetoothConnectionService finished");
        start();
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread {

        // The local server socket
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;

            // Create a new listening server socket
            try{
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);

                Log.e(btTag, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);
            }catch (IOException e){
                Log.e(btTag, "AcceptThread: IOException: " + e.getMessage() );
            }

            mmServerSocket = tmp;
        }

        public void run(){
            Log.e(btTag, "run: AcceptThread Running.");

            BluetoothSocket socket = null;

            try{
                // This is a blocking call and will only return on a
                // successful connection or an exception
                Log.e(btTag, "run: RFCOM server socket start.....");

                socket = mmServerSocket.accept();

                Log.e(btTag, "run: RFCOM server socket accepted connection.");

            }catch (IOException e){
                Log.e(btTag, "AcceptThread: IOException: " + e.getMessage() );
            }

            //talk about this is in the 3rd
            if(socket != null){
                connected(socket,mmDevice);
            }

            Log.e(btTag, "END mAcceptThread ");
        }

        public void cancel() {
            Log.e(btTag, "cancel: Canceling AcceptThread.");
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(btTag, "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage() );
            }
        }

    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device)
            throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID_INSECURE);
            } catch (Exception e) {
                Log.e(btTag, "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID_INSECURE);
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.e(btTag, "ConnectThread: started.");
            mmDevice = device;
            deviceUUID = uuid;
        }

        public void run(){
            BluetoothSocket tmp = null;
            Log.e(btTag, "RUN mConnectThread ");

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                Log.e(btTag, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: " +MY_UUID_INSECURE );
                tmp = mmDevice.createRfcommSocketToServiceRecord(deviceUUID);
                //tmp =(BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mmDevice,1);
                //tmp = createBluetoothSocket(mmDevice);
                Log.e(btTag, "ConnectThread: Compelete to create InsecureRfcommSocket!!");
            } catch (Exception e) {
                Log.e(btTag, "ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
            }

            mmSocket = tmp;

            Log.e("mmSocket.", "getRemoteDevice()"+String.valueOf(mmSocket.getRemoteDevice()));

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Make a connection to the BluetoothSocket
            try {
                Log.e(btTag, "run: Try to ConnectThread");
                mmSocket.connect();
                success = true;
                Log.e(btTag, "run: ConnectThread connected.");
            } catch (IOException e) {
                try {
                    success = false;
                    Log.e("reConnect", "Started " + e.getMessage());
                    mmSocket =(BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mmDevice,1);
                    mmSocket.connect();
                    Log.e("reConnect", "complete");
                    success = true;
                } catch (Exception e1) {
                    success = false;
                    Log.e("reConnect", "Error again with " + e1.getMessage());

                    // Close the socket
                    try {
                        mmSocket.close();
                        Log.e(btTag, "run: Closed Socket.");
                    } catch (IOException e2) {
                        Log.e(btTag, "mConnectThread: run: Unable to close connection in socket " + e2.getMessage());
                        try {
                            mmSocket.close();
                            Log.e(btTag, "run: Closed Socket.");
                        } catch (IOException e3) {
                            Log.e(btTag, "mConnectThread: run: Unable to close connection in socket " + e3.getMessage());
                        }
                    }
                }
            }

            //will talk about this in the 3rd video
            Log.e("SUCCESS?", String.valueOf(success));

            if (success) {
                connected(mmSocket, mmDevice);
            } else {
                cancel();
                Intent intent = new Intent(getApplicationContext(), BTOnActivity.class);
                mContext.startActivity(intent);
                Log.e("Error","doesn't connected");
            }
        }
        public void cancel() {
            try {
                Log.e(btTag, "cancel: Closing Client Socket.");
                mmSocket.close();
                mConnectThread.interrupt();
                Log.e("제발!!", String.valueOf(mConnectThread.isInterrupted()));
            } catch (IOException e) {
                Log.e(btTag, "cancel: close() of mmSocket in Connectthread failed. " + e.getMessage());
            }
        }
    }

    public synchronized void start() {
        Log.e(btTag, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    public void startClient(BluetoothDevice device, UUID uuid){
        Log.e(btTag, "startClient: Started.");

        mConnectThread = new ConnectThread(device, uuid);
        Log.e(btTag, "ConnectThread: created.");
        mConnectThread.start();
        Log.e(btTag, "startClient: Finished.");
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.e(btTag, "ConnectedThread: constructer init.");

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

            Log.e("ConnectedThread", "constructer fin");
        }

        public void run(){
            Log.e("ConnectedThread", "run init");
            byte[] buffer = new byte[1024];  // buffer store for the stream

            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                Log.e("ConnectedThread", "while init");
                // Read from the InputStream
                try {
                    bytes = mmInStream.read(buffer);
                    Log.e(btTag, "bytes = mmInStream.read(buffer); complete!!!");
                    String incomingMessage = new String(buffer, 0, bytes);
                    Log.e(btTag, "InputStream: " + incomingMessage + "real complete!!!!!!!!!!!!!!!!!");
                } catch (IOException e) {
                    Log.e(btTag, "write: Error reading Input Stream. " + e.getMessage());
                    break;
                }
            }
            Log.e("ConnectedThread", "run Fin");
        }

        //Call this from the main activity to send data to the remote device
        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            Log.e(btTag, "write: Writing to outputstream: " + text);
            try {
                mmOutStream.write(bytes);
                Log.e("Write", text + "Complete!!!!");
            } catch (IOException e) {
                Log.e(btTag, "write: Error writing to output stream. " + e.getMessage() );
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        Log.e(btTag, "connected: Starting.");

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    public void write(byte[] out) {
        // Create temporary object
        // Synchronize a copy of the ConnectedThread
        Log.e(btTag, "write: Write Called.");
        //perform the write
        mConnectedThread.write(out);
    }

}

