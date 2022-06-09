package ru.mirea.gasanyan.looper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    private int number = 0;
    Handler handler;
    @SuppressLint("HandlerLeak")
    @Override
    public void run(){
        Log.d("ru.mirea.gasanyan.looper.MyLooper", "run");
        Looper.prepare();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                Log.d("ru.mirea.gasanyan.looper.MyLooper", number + ":"+ msg.getData().getString("key_job") + msg.getData().getString("key_age"));
                number++;
            }
        };
        Looper.loop();
    }
}