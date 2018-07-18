package com.tag.tai.tag.Activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.tag.tai.tag.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Jam on 05-04-2018.
 */

public class Video extends AppCompatActivity {

    VideoView videoView;
    MediaPlayer mPlayer;

    int pausedat = 0;
    boolean videoPaused = false;

    TextView tv_video_hindi,tv_video_english;
    FloatingActionButton fab_changelang;
    CardView cv_lang_holder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);

        videoView = findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();

        mPlayer = MediaPlayer.create(Video.this, R.raw.en_audio);
        mPlayer.start();

        tv_video_hindi = findViewById(R.id.tv_video_hindi);
        tv_video_english = findViewById(R.id.tv_video_english);

        cv_lang_holder = findViewById(R.id.cv_lang_holder);
        fab_changelang = findViewById(R.id.fab_changelang);

        fab_changelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cv_lang_holder.getVisibility() == VISIBLE)
                    cv_lang_holder.setVisibility(GONE);
                else
                    cv_lang_holder.setVisibility(VISIBLE);
            }
        });


        tv_video_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideoByLanguage(R.raw.hi_audio);
            }
        });

        tv_video_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideoByLanguage(R.raw.en_audio);
            }
        });


        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(videoPaused){

                    if(mPlayer!=null) {
                        mPlayer.start();
                        mPlayer.seekTo(pausedat);

                    }

                    if(videoView!=null) {
                        videoView.seekTo(pausedat);
                        videoView.start();
                    }

                    videoPaused = false;

                }else{

                    Toast.makeText(Video.this, "Video Paused.", Toast.LENGTH_SHORT).show();

                    if(mPlayer!=null)
                        mPlayer.pause();

                    if(videoView!=null)
                        videoView.pause();

                    pausedat = mPlayer.getCurrentPosition();

                    videoPaused = true;
                }

            }
        });
    }

    private void playVideoByLanguage(int languagefile) {
        // 0 for english; 1 for hindi

        mPlayer.release();

        mPlayer = MediaPlayer.create(Video.this, languagefile);

        if(mPlayer!=null) {
            mPlayer.seekTo(0);
            mPlayer.start();
        }

        if(videoView!=null) {
            videoView.seekTo(0);
            videoView.start();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mPlayer!=null)
            mPlayer.pause();

        if(videoView!=null)
            videoView.pause();

        pausedat = mPlayer.getCurrentPosition();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mPlayer!=null) {
            mPlayer.seekTo(pausedat);
            mPlayer.start();
        }

        if(videoView!=null) {
            videoView.seekTo(pausedat);
            videoView.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }
}
