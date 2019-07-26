package com.project.jhasan.soudagor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread mythread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        mythread.start();

    }
}
