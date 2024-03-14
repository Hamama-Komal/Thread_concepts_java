package com.example.myapplication.LooperAndHandler.classes.activities;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.LooperAndHandler.classes.activities.LooperThread;

public class CustomHandlerThread extends HandlerThread {
    public CustomHandlerThread(String name) {
        super(name);
    }

    private static final String TAG = LooperThread.class.getSimpleName();
    public Handler myHandler;

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        myHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "Thread id when message is posted: "+Thread.currentThread().getId() + ", Count:" + msg.obj);
            }
        };
    }
}
