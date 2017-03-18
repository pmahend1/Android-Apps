/*
a. Assignment #. Homework 7 
b. File Name. PlayActivity
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.tedradiohour;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PlayActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    boolean bPlayStatus = false;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_play);


        final Radio radio = (Radio) getIntent().getSerializableExtra("PLAY_RADIO");

        Log.d("play", "radio at play :" + radio.toString());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_play);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Play!");
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_action_bar, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView textView_episode_title_dtls;
        ImageView imageView_episode_image;
        TextView textView_description;
        TextView textView_pub_date;
        TextView textView_duration;
        final ImageButton imageButton_playStatus;

        textView_episode_title_dtls = (TextView) findViewById(R.id.textView_episode_title_dtls);
        imageView_episode_image = (ImageView) findViewById(R.id.imageView_episode_image);
        textView_description = (TextView) findViewById(R.id.textView_description);
        textView_pub_date = (TextView) findViewById(R.id.textView_pub_date);
        textView_duration = (TextView) findViewById(R.id.textView_duration);
        imageButton_playStatus = (ImageButton) findViewById(R.id.imageButton_playStatus);
        if (radio != null) {
            try {
                Picasso.with(this).load(radio.getImageLink()).resize(200, 200).into(imageView_episode_image);


            } catch (Exception e) {
                Log.d("ex", "Exc " + e.getMessage());
            }
            try{
                textView_episode_title_dtls.setText(radio.getTitle().toString());
                textView_description.setText(radio.getDescription().toString());
                textView_pub_date.setText(radio.getPubDate());
                textView_duration.setText(radio.getDuration() + "");
            }catch (Exception e){
                Log.d("ex", "Exc 1" + e.getMessage());
            }

            imageButton_playStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bPlayStatus=true;
                    if(bPlayStatus){
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        //imageButton_playStatus.setOnClickListener(this);
                        new AsyncPlayer(PlayActivity.this,imageButton_playStatus,mediaPlayer).execute(radio.getPodcastLink());
                    }else{
                        mediaPlayer.pause();
                        bPlayStatus=false;
                    }

                }
            });
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
