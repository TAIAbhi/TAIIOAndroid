package com.tag.tai.tag.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;

import io.fabric.sdk.android.Fabric;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jam on 15-03-2018.
 */

public class SplashActivity extends AppCompatActivity {

    SessionManager session;
    ImageView iv_logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        session = new SessionManager(this);
        iv_logo = findViewById(R.id.iv_logo);


//        new AlertDialog.Builder(this)
//                .setTitle()


//       session.Logout();

        long after5mins = (Long.parseLong(session.getlastsplashsreentime() + "") + 300000);

        if(after5mins < Calendar.getInstance().getTimeInMillis()){

            Glide.with(this).load(R.drawable.splash).into(iv_logo);
            session.setlastsplashsreentime(Calendar.getInstance().getTimeInMillis());


            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    checkIfUserLoggedIn();

                }
            },10000);
            
        }else{
            checkIfUserLoggedIn();
        }


    }

    private void checkIfUserLoggedIn() {

        if(session.IsLoggedIn()){
            Intent i = new Intent(SplashActivity.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }else{
            Intent i = new Intent(SplashActivity.this,LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}
