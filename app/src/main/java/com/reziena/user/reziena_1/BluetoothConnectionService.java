package com.reziena.user.reziena_1;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by User on 12/21/2016.
 */

public class BluetoothConnectionService {
    private static final String TAG = "BluetoothConnectionServ";

    private static final String appName = "MYAPP";

    private static final UUID MY_UUID_INSECURE =
            //UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");  /** Galaxy s9 */
            UUID.fromString("00000003-0000-1000-8000-00805f9b34fb"); /** Galaxy s9+ */

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;

    private ConnectedThread mConnectedThread;

    public BluetoothConnectionService(Context context) {
        Log.e(TAG, "come in BluetoothConnectionService");
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.e(TAG, "BluetoothConnectionService finished");
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

                Log.e(TAG, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);
            }catch (IOException e){
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
            }

            mmServerSocket = tmp;
        }

        public void run(){
            Log.e(TAG, "run: AcceptThread Running.");

            BluetoothSocket socket = null;

            try{
                // This is a blocking call and will only return on a
                // successful connection or an exception
                Log.e(TAG, "run: RFCOM server socket start.....");

                socket = mmServerSocket.accept();

                Log.e(TAG, "run: RFCOM server socket accepted connection.");

            }catch (IOException e){
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
            }

            //talk about this is in the 3rd
            if(socket != null){
                connected(socket,mmDevice);
            }

            Log.e(TAG, "END mAcceptThread ");
        }

        public void cancel() {
            Log.e(TAG, "cancel: Canceling AcceptThread.");
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage() );
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
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
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
            Log.e(TAG, "ConnectThread: started.");
            mmDevice = device;
            deviceUUID = uuid;
        }

        public void run(){
            BluetoothSocket tmp = null;
            Log.e(TAG, "RUN mConnectThread ");

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                Log.e(TAG, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: " +MY_UUID_INSECURE );
                tmp = mmDevice.createRfcommSocketToServiceRecord(deviceUUID);
                //tmp =(BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mmDevice,1);
                //tmp = createBluetoothSocket(mmDevice);
                Log.e(TAG, "ConnectThread: Compelete to create InsecureRfcommSocket!!");
            } catch (Exception e) {
                Log.e(TAG, "ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
            }

            mmSocket = tmp;

            Log.e("mmSocket.", "getRemoteDevice()"+String.valueOf(mmSocket.getRemoteDevice()));

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Make a connection to the BluetoothSocket
            boolean success = false;
            try {
                Log.e(TAG, "run: Try to ConnectThread");
                mmSocket.connect();
                success = true;
                Log.e(TAG, "run: ConnectThread connected.");
            } catch (IOException e) {
                try {
                    Log.e("reConnect", "Started " + e.getMessage());
                    mmSocket =(BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mmDevice,1);
                    mmSocket.connect();
                    Log.e("reConnect", "complete");
                    success = true;
                } catch (Exception e1) {
                    Log.e("reConnect", "Error again with " + e1.getMessage());

                    // Close the socket
                    try {
                        mmSocket.close();
                        Log.e(TAG, "run: Closed Socket.");
                    } catch (IOException e2) {
                        Log.e(TAG, "mConnectThread: run: Unable to close connection in socket " + e2.getMessage());
                        try {
                            mmSocket.close();
                            Log.e(TAG, "run: Closed Socket.");
                        } catch (IOException e3) {
                            Log.e(TAG, "mConnectThread: run: Unable to close connection in socket " + e3.getMessage());
                        }
                    }
                }
            }

            //will talk about this in the 3rd video
            Log.e("SUCCESS?", String.valueOf(success));
            connected(mmSocket,mmDevice);
        }
        public void cancel() {
            try {
                Log.e(TAG, "cancel: Closing Client Socket.");
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: close() of mmSocket in Connectthread failed. " + e.getMessage());
            }
        }
    }



    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
    public synchronized void start() {
        Log.e(TAG, "start");

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

    /**

     AcceptThread starts and sits waiting for a connection.
     Then ConnectThread starts and attempts to make a connection with the other devices AcceptThread.
     **/

    public void startClient(BluetoothDevice device, UUID uuid){
        Log.e(TAG, "startClient: Started.");

        mConnectThread = new ConnectThread(device, uuid);
        Log.e(TAG, "ConnectThread: created.");
        mConnectThread.start();
        Log.e(TAG, "startClient: Finished.");
    }

    /**
     Finally the ConnectedThread which is responsible for maintaining the BTConnection, Sending the data, and
     receiving incoming data through input/output streams respectively.
     **/
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.e(TAG, "ConnectedThread: constructer init.");

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            //dismiss the progressdialog when connection is established
            try{
                mProgressDialog.dismiss();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
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
                    Log.e(TAG, "bytes = mmInStream.read(buffer); complete!!!");
                    String incomingMessage = new String(buffer, 0, bytes);
                    Log.e(TAG, "InputStream: " + incomingMessage + "real complete!!!!!!!!!!!!!!!!!");
                } catch (IOException e) {
                    Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage());
                    break;
                }
            }
            Log.e("ConnectedThread", "run Fin");
        }

        //Call this from the main activity to send data to the remote device
        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            Log.e(TAG, "write: Writing to outputstream: " + text);
            try {
                mmOutStream.write(bytes);
                Log.e("Write", text + "Complete!!!!");
            } catch (IOException e) {
                Log.e(TAG, "write: Error writing to output stream. " + e.getMessage() );
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
        Log.e(TAG, "connected: Starting.");

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // Create temporary object
        // Synchronize a copy of the ConnectedThread
        Log.e(TAG, "write: Write Called.");
        //perform the write
        mConnectedThread.write(out);
    }

}
























