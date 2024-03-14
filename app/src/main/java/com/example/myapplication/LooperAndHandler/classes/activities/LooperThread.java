package com.example.myapplication.LooperAndHandler.classes.activities;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class LooperThread extends Thread{

    private static final String TAG = LooperThread.class.getSimpleName();

    Handler handler;
    // Handler
    @Override
    public void run() {

        // Creating Looper
        Looper.prepare();

        // Setting Custom message Queue
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                // shows counter thread id
                Log.i(TAG, "Thread id:" + Thread.currentThread().getId() +", count:" + msg.obj);
            }
        };

        Looper.loop();
    }
}
