package com.petersoboyejo.yiblr;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;


public class SplashActivity extends Activity {

    public static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, AlarmListActivity.class);
                startActivity(i);

                finish();

            }
<<<<<<< HEAD
        }, SPLASH_TIME_OUT);
=======
        }, SPLASH_TIME_OUT);g

>>>>>>> origin/master
    }
}