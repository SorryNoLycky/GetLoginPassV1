package com.example.getloginpassapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    private TextView tv;
    private SharedPreferences sPref;
    public static String MY_PREF="GETLOGINPASSAPP_PREFERENCES_FILE";
    public final static String FULLUSERNAME = "fullusername";
    private int code = 0;

    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tvViewFullName);

        String fullUserName;
        if((fullUserName=loadUserFullNameFromMyPref())==null){
            getUserFullNameFromLoginActivity();
        } else {
            tv.setText("Hi "+fullUserName);
        }
    }

    private String loadUserFullNameFromMyPref(){
        sPref = getApplicationContext().getSharedPreferences(MY_PREF, MODE_PRIVATE);
        String fullUserName = sPref.getString(FULLUSERNAME, "");
        if(fullUserName.isEmpty()){
            return null;
        } else {
            return fullUserName;
        }
    }

    private void getUserFullNameFromLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==code){
            if (resultCode==RESULT_OK){
                String fullUserName=data.getStringExtra(FULLUSERNAME);
                saveFullUserNameInMyPref(fullUserName);
                tv.setText("Hi"+fullUserName);

            } else {
                int duration = Toast.LENGTH_SHORT;
                if (toast!=null){
                    toast.cancel();
                }
                toast = Toast.makeText(this,"This user is not found", duration);
                toast.show();
                getUserFullNameFromLoginActivity();
            }
        }
    }

    private void saveFullUserNameInMyPref(String data){
        sPref = getApplicationContext().getSharedPreferences(MY_PREF, MODE_PRIVATE);
        SharedPreferences.Editor ed= sPref.edit();
        ed.putString(FULLUSERNAME,data);
        ed.commit();
    }
}