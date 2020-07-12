package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(getApplicationContext(), Main_SketchActivity.class);
                startActivity(intent);
                finish();
            }
        },2000); //2초 뒤 run() 메소드 실행
    }
}
