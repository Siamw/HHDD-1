package com.hj.hd.hhdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

/**
 * Created by hwangil on 2018-03-12.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String flagPath = getFilesDir() + "/userdata/introFlag.dat";

        File files = new File (flagPath);
        try{
            Thread.sleep(1000);
            if (files.exists() == true)
            {
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
            }
            else
            {
                Intent introIntent = new Intent(this, IntroActivity.class);
                startActivity(introIntent);
            }

            finish();

        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
}
