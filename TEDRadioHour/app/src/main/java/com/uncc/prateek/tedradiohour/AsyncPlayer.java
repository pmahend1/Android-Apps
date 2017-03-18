/*
a. Assignment #. Homework 7 
b. File Name. 
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.tedradiohour;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;

import java.io.IOException;

/**
 * Created by Prateek on 12-03-2017.
 */

class AsyncPlayer extends AsyncTask<String, Void, Boolean> {
    private ProgressDialog progress;
    private MediaPlayer mediaPlayer;
    private boolean intialStage = true;
    private boolean playPause;
    private Context context;
    ImageButton resourceId;
    ImageButton imageButton_playStatus;

    public AsyncPlayer(Context context,ImageButton resourceId, MediaPlayer mediaPlayer) {
        this.context = context;
        this.resourceId=resourceId;
        progress = new ProgressDialog(context);
        this.mediaPlayer = mediaPlayer;
        this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        // TODO Auto-generated method stub
        Boolean prepared;

        try {

            mediaPlayer.setDataSource(params[0]);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    intialStage = true;
                    playPause=false;
                    resourceId.setBackgroundResource(android.R.drawable.ic_media_play);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
            mediaPlayer.prepare();
            prepared = true;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            Log.d("IllegarArgument", e.getMessage());
            prepared = false;
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            prepared = false;
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            prepared = false;
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            prepared = false;
            e.printStackTrace();
        }
        return prepared;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (progress.isShowing()) {
            progress.cancel();
        }
        Log.d("Prepared", "//" + result);
        mediaPlayer.start();

        intialStage = false;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        this.progress.setMessage("Buffering...");
        this.progress.show();

    }
}