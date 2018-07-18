package com.tag.tai.tag.Fragments.HelpFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.tag.tai.tag.R;

import static android.view.View.GONE;

/**
 * Created by Jam on 13-04-2018.
 */

public class HelpData extends Fragment {

    TextView tv_data;
    ImageView iv_helpimg;
    LinearLayout ll_video_content;
    ScrollView sv_text_content;

    public HelpData() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_help_data,container,false);

        ll_video_content = v.findViewById(R.id.ll_video_content);
        iv_helpimg = v.findViewById(R.id.iv_helpimg);
        sv_text_content = v.findViewById(R.id.sv_text_content);

        YouTubePlayerSupportFragment youtube_view = (YouTubePlayerSupportFragment) getChildFragmentManager().findFragmentById(R.id.youtube_view);


        Boolean isVideo = getArguments().getBoolean("isVideo");
        String text = "";


        if(isVideo == true){
            final String video = getArguments().getString("video");

            youtube_view.initialize("AIzaSyCioSw4LvPb2gyquifkidhfrX2P1kN84uU", new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    if(!b){
                        youTubePlayer.cueVideo(video.split("v=")[1]);
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
        }else{
            sv_text_content.setVisibility(View.VISIBLE);
            ll_video_content.setVisibility(GONE);

            if(URLUtil.isValidUrl(getArguments().getString("text")))
            Glide.with(getActivity()).load(getArguments().getString("text")).into(iv_helpimg);
            //text = getArguments().getString("text");// + " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
        }

        tv_data = v.findViewById(R.id.tv_data);
        tv_data.setText(text);

        return v;
    }
}
