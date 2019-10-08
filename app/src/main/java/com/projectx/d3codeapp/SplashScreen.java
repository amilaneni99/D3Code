package com.projectx.d3codeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                }
                catch (InterruptedException e) { e.printStackTrace(); }
                finally {
                    if (isFirstTime()){
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("com.d3code.app",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor =sp.edit();
                        editor.putBoolean("firstTime",false);
                        editor.apply();
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SplashScreen.this,ViewPDF.class);
                        startActivity(intent);
                    }
                }
            }
        };
        myThread.start();
    }

    private boolean isFirstTime() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("com.d3code.app", Context.MODE_PRIVATE);
        if(sp.contains("firstTime")){
            return false; }
        return true;
    }
}
