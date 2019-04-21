package com.example2017.android.keyboard;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyboardShortcutInfo;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button but;
    int PICK_KEYBOARD_REQUEST = 1;
    AlarmManager alarmManager;
    PendingIntent alarmIntent;
    int TIME_IN_MIILESECOND = 1000;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        but=(Button)findViewById(R.id.activate);





            alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getBaseContext(), MyReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //we cancel alarmManger to avoid dublicated
            alarmManager.cancel(alarmIntent);

            Calendar calendar= Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

         // intent every 3 hours
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),TIME_IN_MIILESECOND * 60 * 60 * 3 , alarmIntent);






        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (isExist() == false){
                    Intent enableIntent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                    enableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(enableIntent, PICK_KEYBOARD_REQUEST);;

                }else{
                   ShowKeyboardsDialog();
                    Toast.makeText(MainActivity.this, "Select Real Madrid Keyboard", Toast.LENGTH_LONG).show();
                }
            }
        });

          }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_KEYBOARD_REQUEST){


            if (isExist()==false){

                Intent enableIntent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                enableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(enableIntent, PICK_KEYBOARD_REQUEST);;
                Toast.makeText(MainActivity.this, "Please active Real Madrid Keyboard", Toast.LENGTH_SHORT).show();
            }else {
                but.setText("Apply KeyBoard");
                Toast.makeText(MainActivity.this, "Select Real Madrid Keyboard", Toast.LENGTH_LONG).show();

            }

        }


    }

    private boolean isExist(){

        boolean exist=false;
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> list = inputManager.getEnabledInputMethodList();


        //loop to check if keyboard is exist or no
        for (int i=0 ;i<list.size();i++){
            if ( list.get(i).getId().equals("com.example2017.android.keyboard/.MyService"))
            {

                exist=true;

            }}

       return exist;

    }

    private void ShowKeyboardsDialog(){
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showInputMethodPicker();
        }catch (Exception e){
            Log.d("exce",e.getMessage());
        }
    }




}
