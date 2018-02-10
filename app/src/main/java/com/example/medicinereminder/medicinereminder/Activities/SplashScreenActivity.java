package com.example.medicinereminder.medicinereminder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.medicinereminder.medicinereminder.R;

public class SplashScreenActivity extends AppCompatActivity {

    public static int time_splash=2000;         //this is to set the time limit for the splash screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable(){

            @Override
            public void run()
            {
                Intent i= new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        },time_splash);

    }
}
