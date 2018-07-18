package com.tag.tai.tag.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tag.tai.tag.Fragments.Notification.NotificationFragment;
import com.tag.tai.tag.R;

public class NotificationActivity extends AppCompatActivity {

    public NotificationActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new NotificationFragment()).commit();
    }
}
