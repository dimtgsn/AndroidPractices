package ru.mirea.gasanyan.mireaproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.util.ArrayList;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Operation Exception");
    }

    @Override
    public void onCreate(){
        mediaPlayer = MediaPlayer.create(this, MusicFragment.idSong());
        mediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}
