package com.tag.tai.tag.Fragments.Introduction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tag.tai.tag.Activities.Help;
import com.tag.tai.tag.Activities.LoginActivity;
import com.tag.tai.tag.Activities.MainActivity;
import com.tag.tai.tag.Activities.Video;
import com.tag.tai.tag.Common.Loader;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Login;
import com.tag.tai.tag.Services.Requests.SkipVideo.SkipRequest;
import com.tag.tai.tag.Services.Responses.SkipVideo.SkipResponse;
import com.tag.tai.tag.Services.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tag.tai.tag.Services.RetroClient.TAG;

/**
 * Created by Jam on 05-04-2018.
 */

public class IntroductionFragment extends Fragment{

    Button btn_video,btn_skip;
    TextView tv_userText,tv_name;
    SessionManager session;
    ImageView iv_user_image;

    CheckBox ck_skip;

    Loader loader;

    public IntroductionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_introduction,container,false);

        session = new SessionManager(getActivity());

        if(getArguments().getInt("activity_reference") == 1){
            loader = new Loader(getActivity(),(Help)getActivity());
            v.findViewById(R.id.ll_skip_holder).setVisibility(View.GONE);
        }else{
            loader = new Loader(getActivity(),(LoginActivity)getActivity());
        }

        ck_skip = v.findViewById(R.id.ck_skip);

        tv_userText = v.findViewById(R.id.tv_userText);
        String userText = session.getUserIntro();

        String placeholder = "**source name**";
        String usernamae = session.getSourceName();
        userText = userText.replace(placeholder,usernamae);
        tv_userText.setText(userText);

        tv_name = v.findViewById(R.id.tv_name);
        tv_name.setText(session.getSourceName());

        iv_user_image = v.findViewById(R.id.iv_user_image);
        Glide.with(getActivity()).load(session.getUserimage()).into(iv_user_image);

        btn_video = v.findViewById(R.id.btn_video);
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Video.class);
                startActivity(i);
            }
        });

        btn_skip = v.findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ck_skip.isChecked()){
                    skipVideoClicked();
                }else{
                    callMainActivity();
                }

            }
        });

        return v;
    }

    private void skipVideoClicked(){
        loader.showLoader();

        SkipRequest r = new SkipRequest(session.getUserID(),"true");

        Login l = RetroClient.getClient().create(Login.class);
        Call<SkipResponse> call = l.skipVideo(session.getToken(),r);
        call.enqueue(new Callback<SkipResponse>() {
            @Override
            public void onResponse(Call<SkipResponse> call, Response<SkipResponse> response) {
                loader.hideLoader();
                callMainActivity();
            }

            @Override
            public void onFailure(Call<SkipResponse> call, Throwable t) {
                loader.hideLoader();
                callMainActivity();
            }
        });
    }

    private void callMainActivity(){

        Intent i = new Intent(getActivity(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
