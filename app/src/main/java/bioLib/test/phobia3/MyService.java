package bioLib.test.phobia3;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;


import Bio.Library.namespace.BioLib;

import bioLib.test.phobia3.model.VJRecord;
import bioLib.test.phobia3.sqlite.MySQLiteHelper;

/**
 * Created by Gustavo on 06/12/2017.
 */

public class MyService extends Service {

    private BioLib lib = null;

    //private String address = "";
    // Cliche:
    //private String address = "00:23:FE:00:0B:54";
    // Filipa:
    //private String address = "00:23:FE:00:0B:34";
    // Gustavo:
    private String address = "00:23:FE:00:0B:23";
    private String macaddress = "";
    private String mConnectedDeviceName = "";
    private BluetoothDevice deviceToConnect;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private int BATTERY_LEVEL = 0;
    public static int PULSE = 0;
    private Date DATETIME_PUSH_BUTTON = null;
    private Date DATETIME_RTC = null;
    private Date DATETIME_TIMESPAN = null;
    private int SDCARD_STATE = 0;
    private int numOfPushButton = 0;
    private BioLib.DataACC dataACC = null;
    private String deviceId = "";
    private String firmwareVersion = "";
    private byte accSensibility = 1;	// NOTE: 2G= 0, 4G= 1
    private byte typeRadioEvent = 0;
    private byte[] infoRadioEvent = null;
    private short countEvent = 0;

    private boolean isConn = false;

    private byte[][] ecg = null;
    private int nBytes = 0;

    private String accConf = "";

    private int cc=0;
    public static int dd=0;

    private ArrayList<VJRecord> list_thisSession;
    private ArrayList<VJRecord>list_allSessions;
    MySQLiteHelper db;
    private Button btn_newSession;
    int session=0;

    private final String pictures_folder="/Pictures";


    long startTime = 0;

    ArrayList<Integer> pulse_array;

    ArrayList<Integer> pulse_array_final=new ArrayList<Integer>();
    int last_pulse;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public synchronized void run() {
            //long millis = System.currentTimeMillis() - startTime;
            /*
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            */

            try {
                //last_pulse = pulse_array.get(pulse_array.size() - 1);
                //pulse_array_final.add(PULSE);
                Toast.makeText(getApplicationContext(), Integer.toString(PULSE), Toast.LENGTH_SHORT).show();
                //
                // Toast.makeText(getApplicationContext(), "Im here", Toast.LENGTH_SHORT).show();
                pulse_array_final.add(PULSE);
                snapShot();

            } catch(Exception a){

                Toast.makeText(getApplicationContext(), "EXCEPÇAO LAST PULSE", Toast.LENGTH_SHORT).show();

            }

            //timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 2000);
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {




        db = new MySQLiteHelper(this);

        list_allSessions = db.getAllRecords();

        int id=list_allSessions.size();
        if(id==0)
            session=0;
        else session=db.getRecord(id).getSession();

        session++;

        File f = new File(Environment.getExternalStorageDirectory().getPath()+pictures_folder, "Session"+session);
        if (!f.exists()) {
            f.mkdirs();
            Toast.makeText(getApplicationContext(), "Directory created", Toast.LENGTH_SHORT).show();
        }


        try
        {
            lib = new BioLib(this, mHandler);
            Toast.makeText(getApplicationContext(), "Init BioLib", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error to init BioLib", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        //Toast.makeText(getApplicationContext(), "Before connect", Toast.LENGTH_SHORT).show();


        Connect();

        //Toast.makeText(getApplicationContext(), "After connect", Toast.LENGTH_SHORT).show();

        //startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 2000);



        return START_STICKY;

    }

    private void Connect()
    {
        try
        {
            deviceToConnect =  lib.mBluetoothAdapter.getRemoteDevice(address);
            Reset();
            lib.Connect(address, 5);

        } catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error to connect device: "+address, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //@Override
    public void onDestroy() {
        //super.onDestroy();

        for(int i=0;i<pulse_array_final.size();i++){
            db.addRecord(new VJRecord(pulse_array_final.get(i), session));
        }


        //Toast.makeText(getApplicationContext(), "DB POPULATED ", Toast.LENGTH_LONG).show();

        if (lib.mBluetoothAdapter != null)
        {
            lib.mBluetoothAdapter.cancelDiscovery();
        }

        lib = null;
        timerHandler.removeCallbacksAndMessages(null);
        mHandler.removeCallbacksAndMessages(null);


    }

    private void Disconnect()
    {
        try
        {
            lib.Disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Reset();
        }
    }

    private void Reset()
    {
        try
        {
            /*
            textPULSE.setText("PULSE: - - bpm");
            textHR.setText("PEAK: --  BPMi: -- bpm  BPM: -- bpm  R-R: -- ms");
            textTimeSpan.setText("SPAN: - - - ");
            */

            SDCARD_STATE = 0;
            BATTERY_LEVEL = 0;
            PULSE = 0;
            DATETIME_PUSH_BUTTON = null;
            DATETIME_RTC = null;
            DATETIME_TIMESPAN = null;
            numOfPushButton = 0;
            countEvent = 0;
            accConf = "";
            firmwareVersion = "";
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void snapShot(){

    try{

        cc++;

    Process sh = Runtime.getRuntime().exec("su", null,null);

    OutputStream os = sh.getOutputStream();
                        //os.write(("/system/bin/screencap -p " + "/sdcard/img.jpg").getBytes("ASCII"));

        //os.write(("/system/bin/screencap -p " + "/sdcard/snap"+Integer.toString(cc)+".jpg").getBytes("ASCII"));
        os.write(("/system/bin/screencap -p " + Environment.getExternalStorageDirectory().getPath()+
                pictures_folder +"/" + "Session"+Integer.toString(session) + "/" +
                Integer.toString(cc) + ".jpg").getBytes("ASCII"));
        //os.write(("/system/bin/screencap -p " + "/sdcard/"+Calendar.getInstance().getTime()).getBytes("ASCII"));

        //Toast.makeText(getApplicationContext(), "/system/bin/screencap -p " + Environment.getExternalStorageDirectory().getPath()+
              //  pictures_folder +"/" + "Session"+Integer.toString(session) + "/" +
              //  Integer.toString(cc) + ".jpg", Toast.LENGTH_LONG).show();


                        os.flush();

                        os.close();
                        sh.waitFor();

    }
        catch(Exception e){

            //Toast.makeText(getApplicationContext(), "EXCEPÇÂO ", Toast.LENGTH_LONG).show();
        }
    }

    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {

                case BioLib.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;

                case BioLib.MESSAGE_BLUETOOTH_NOT_SUPPORTED:
                    Toast.makeText(getApplicationContext(), "Bluetooth NOT supported. Aborting! ", Toast.LENGTH_SHORT).show();
                    isConn = false;
                    break;

                case BioLib.MESSAGE_BLUETOOTH_ENABLED:
                    Toast.makeText(getApplicationContext(), "Bluetooth is now enabled! ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Macaddress selected: "+ address, Toast.LENGTH_SHORT).show();

                    /*
                    imageview_green2.setVisibility(View.VISIBLE);
                    imageview_red2.setVisibility(View.INVISIBLE);
                    buttonConnect.setEnabled(true);
                    */
                    break;

                case BioLib.MESSAGE_BLUETOOTH_NOT_ENABLED:
                    /*
                    Toast.makeText(getApplicationContext(), "Bluetooth not enabled! ", Toast.LENGTH_SHORT).show();
                    imageview_green2.setVisibility(View.INVISIBLE);
                    imageview_red2.setVisibility(View.VISIBLE);
                    */
                    isConn = false;
                    break;

                case BioLib.STATE_CONNECTING:
                    Toast.makeText(getApplicationContext(), "Connecting to device ... ", Toast.LENGTH_SHORT).show();

                    break;

                case BioLib.STATE_CONNECTED:
                    Toast.makeText(getApplicationContext(), "Connected to " + deviceToConnect.getName(), Toast.LENGTH_SHORT).show();
                    isConn = true;
                    /*

                    imageview_green1.setVisibility(View.VISIBLE);
                    imageview_red1.setVisibility(View.INVISIBLE);

                    buttonConnect.setEnabled(false);
                    buttonDisconnect.setEnabled(true);
                    */

                    break;

                case BioLib.UNABLE_TO_CONNECT_DEVICE:
                    Toast.makeText(getApplicationContext(), "Unable to connect device! ", Toast.LENGTH_SHORT).show();
                    isConn = false;

                    /*

                    imageview_green1.setVisibility(View.INVISIBLE);
                    imageview_red1.setVisibility(View.VISIBLE);

                    buttonConnect.setEnabled(true);
                    buttonDisconnect.setEnabled(false);
                    */

                    break;

                case BioLib.MESSAGE_DISCONNECT_TO_DEVICE:
                    Toast.makeText(getApplicationContext(), "Device connection was lost", Toast.LENGTH_SHORT).show();
                    //text.append("   Disconnected from " + deviceToConnect.getName() + " \n");
                    isConn = false;

                    /*

                    buttonConnect.setEnabled(true);
                    buttonDisconnect.setEnabled(false);
                    */
                    break;
	            	/*
	            case BioLib.MESSAGE_TIMESPAN:
	            	DATETIME_TIMESPAN = (Date)msg.obj;
	            	textTimeSpan.setText("SPAN: " + DATETIME_TIMESPAN.toString());
	            	break;
	            	*/

                case BioLib.MESSAGE_DATA_UPDATED:
                    BioLib.Output out = (BioLib.Output)msg.obj;
                    BATTERY_LEVEL = out.battery;
                    //textBAT.setText("BAT: " + BATTERY_LEVEL + " %");
                    //Toast.makeText(getApplicationContext(), Integer.toString(BATTERY_LEVEL) , Toast.LENGTH_SHORT).show();
                    PULSE = out.pulse;
                    //pulse_array=new ArrayList<Integer>();
                    //pulse_array.add(PULSE);
                    //textPULSE.setText("HR: " + PULSE + " bpm");
                    dd++;
                    //Toast.makeText(getApplicationContext(), Integer.toString(cc), Toast.LENGTH_SHORT).show();
                    Log.e("|||||||||||||||||||||||", Integer.toString(dd));

                    //Toast.makeText(getApplicationContext(), "Hey", Toast.LENGTH_SHORT).show();
                    //db.addRecord(new VJRecord(PULSE, session));





                    if (System.currentTimeMillis()-startTime>=2){

                    }


                    startTime = System.currentTimeMillis();




                    break;

                case BioLib.MESSAGE_PEAK_DETECTION:
                    BioLib.QRS qrs = (BioLib.QRS)msg.obj;
                    // textHR.setText("PEAK: " + qrs.position + "  BPMi: " + qrs.bpmi + " bpm  BPM: " + qrs.bpm + " bpm  R-R: " + qrs.rr + " ms");
                    break;

                case BioLib.MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
