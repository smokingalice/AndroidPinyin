package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyIntentService extends Service {

    private MediaPlayer mediaPlayer;
    private boolean music=true;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        }
    }


    public void onPause() {

  if(music==true)
  {
      if (mediaPlayer == null) {

          // R.raw.mmp是资源文件，MP3格式的
          mediaPlayer.pause();
          music=false;
      }
  }

    }
    public void onContinue() {

        if(music==false)
        {
            if (mediaPlayer == null) {

                // R.raw.mmp是资源文件，MP3格式的
                mediaPlayer.start();
                music=true;
            }
        }

    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mediaPlayer.stop();
    }
}