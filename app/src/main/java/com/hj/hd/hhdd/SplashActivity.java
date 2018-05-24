package com.hj.hd.hhdd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

/**
 * Created by hwangil on 2018-03-12.
 */

public class SplashActivity extends AppCompatActivity {

    //TitanicTextView titanicText;

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    String flagPath;
    File files;
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.splash_view);

        //titanicText = (TitanicTextView)findViewById(R.id.titanic_tv);
        //Titanic titanic = new Titanic();
        //titanicText.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));
        //titanic.start(titanicText);
        flagPath = getFilesDir() + "/userdata/introFlag.dat";
        files = new File (flagPath);

//        Handler handler = new Handler();
//        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/

//            },2000);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 2000); // 1초 후에 hd handler 실행  3000ms = 3초


    }
    private class splashhandler implements Runnable{
        public void run(){
              if (files.exists() == true) {
                  Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                  startActivity(mainIntent);
                  finish();
                } else {
                  Intent introIntent = new Intent(SplashActivity.this, IntroActivity.class);
                  startActivity(introIntent);
                  finish();
              }
        }
    }
    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}



//        try{
//            Thread.sleep(1000);
//            if (files.exists() == true)
//            {
//                Intent mainIntent = new Intent(this, MainActivity.class);
//                startActivity(mainIntent);
//            }
//            else
//            {
//                Intent introIntent = new Intent(this, IntroActivity.class);
//                startActivity(introIntent);
//            }
//
//            finish();
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }