package com.tag.tai.tag.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tag.tai.tag.Common.LoaderControl;
import com.tag.tai.tag.Fragments.Login.LoginFragment;
import com.tag.tai.tag.R;

/**
 * Created by Jam on 15-03-2018.
 */

public class LoginActivity extends AppCompatActivity implements LoaderControl {

    LinearLayout ll_loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ll_loader = findViewById(R.id.ll_loader);
        Glide.with(this).load(R.drawable.loader).into((ImageView) findViewById(R.id.iv_loadericon));

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment()).commit();
    }

    @Override
    public void showLoader() {
        ll_loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        ll_loader.setVisibility(View.GONE);
    }
}
