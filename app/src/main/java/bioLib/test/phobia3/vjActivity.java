package bioLib.test.phobia3;

import android.Manifest;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class vjActivity extends AppCompatActivity {

    public static View view123;

    private Button button_DB;

    private Button button_3;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String difficulty_level=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vj);


        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);




        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = mPreferences.edit();


        difficulty_level = mPreferences.getString("difficulty_level", null);




        //int callingActivity = getIntent().getIntExtra("calling-activity", 0);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );

            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }

     //
        /*
        try {
            Process wh = Runtime.getRuntime().exec("su", null, null);

            OutputStream ws = wh.getOutputStream();

            ws.write(("/system/bin/screencap -p " + Environment.getExternalStorageDirectory().getPath() +
                    "/Pictures/" + "Test.jpg").getBytes("ASCII"));

            ws.flush();

            ws.close();
            wh.waitFor();

        } catch(Exception LL){
            Toast.makeText(getApplicationContext(), "Exception vjActivity", Toast.LENGTH_SHORT).show();
        }
        */
//

        /////////////////////7 /////

        button_DB = (Button) findViewById(R.id.buttonDB);
        button_DB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                checkService(MyService.class);

                //Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
                stopService(new Intent(getApplicationContext(), MyService.class));

                checkService(MyService.class);

                Intent intent_MenuActivity = new Intent(vjActivity.this, MenuActivity.class);
                if (intent_MenuActivity != null) {
                    startActivity(intent_MenuActivity);
                }
            }
        });

        button_3 = (Button) findViewById(R.id.button3);
        button_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // ALTERAR

                Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
                stopService(new Intent(getApplicationContext(), MyService.class));


                Intent intent_MenuActivity = new Intent(vjActivity.this, MenuActivity.class);
                if (intent_MenuActivity != null) {
                    startActivity(intent_MenuActivity);
                }
            }
        });

        checkService(MyService.class);
        startService(new Intent(vjActivity.this, MyService.class));
        checkService(MyService.class);



        ///GUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUI


        Intent launchIntent;
        if(difficulty_level.equalsIgnoreCase("normal"))
            launchIntent = getPackageManager().getLaunchIntentForPackage("com.Covil.Vuforia2");
        else
            launchIntent = getPackageManager().getLaunchIntentForPackage("com.covil.vuforia_hard3"); //alterar-----------------------------------------



        //GUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUIGUSTAVOOLHAAQUI







        //Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.Covil.Vuforia2");


        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }

        /*
        Intent intent_DummyActivity = new Intent(getApplicationContext(), DummyActivity.class);
        if (intent_DummyActivity != null) {
                startActivity(intent_DummyActivity);
        }
        */

        //--------------------------------




    }


    private void checkService(Class<?> xx){
        if(isMyServiceRunning(xx)){

           //Toast.makeText(getApplicationContext(), "SERVICE YES", Toast.LENGTH_SHORT).show();
        } else{
            //Toast.makeText(getApplicationContext(), "SERVICE NO", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
