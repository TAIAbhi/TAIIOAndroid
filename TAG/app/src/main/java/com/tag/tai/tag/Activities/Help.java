package com.tag.tai.tag.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tag.tai.tag.Common.LoaderControl;
import com.tag.tai.tag.Fragments.Introduction.IntroductionFragment;
import com.tag.tai.tag.Fragments.OthersFragment.OtherFragment;
import com.tag.tai.tag.R;

/**
 * Created by Jam on 01-05-2018.
 */

public class Help extends AppCompatActivity implements LoaderControl{

    LinearLayout ll_loader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ll_loader = findViewById(R.id.ll_loader);
        Glide.with(this).load(R.drawable.loader).into((ImageView) findViewById(R.id.iv_loadericon));

        String itemid = "" + getIntent().getExtras().get("itemid");

        setFragment(itemid);
        
    }

    private void setFragment(String itemid) {

        if(itemid.equals("5")){
            Bundle b = new Bundle();
            b.putInt("activity_reference",1);

            IntroductionFragment intro = new IntroductionFragment();
            intro.setArguments(b);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,intro).commit();
        }else if(itemid.equals("6")){

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new OtherFragment()).commit();
        }

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
