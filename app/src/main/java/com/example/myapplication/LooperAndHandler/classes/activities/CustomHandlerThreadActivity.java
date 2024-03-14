package com.example.myapplication.LooperAndHandler.classes.activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCustomHandlerThreadBinding;

public class CustomHandlerThreadActivity extends AppCompatActivity {

    ActivityCustomHandlerThreadBinding binding;
    private boolean stopLoop;
    int count = 0;

    CustomHandlerThread customHandlerThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCustomHandlerThreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // shows main Ui thread Id
        Log.i(TAG, "Thread id of Main thread: "+Thread.currentThread().getId());

       customHandlerThread = new CustomHandlerThread("CustomHandlerThread");
       customHandlerThread.start();


        binding.btnStart.setOnClickListener(view -> {

            stopLoop = true;

            // executeCustomLooper();
            executeCustomLooperWithCustomHandler();
        });

        binding.btnStop.setOnClickListener(view -> {
            stopLoop = false;
        });
    }

    private void executeCustomLooperWithCustomHandler() {

        customHandlerThread.myHandler.post(new Runnable() {
            @Override
            public void run() {
                while (stopLoop){
                    try {
                        Thread.sleep(1000);
                        count++;
                        // shows Looper Thread ID
                        Log.i(TAG, "Thread id Runnable posted: "+Thread.currentThread().getId());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // runOnUiThread id is same as mainUiThread id, that means it runs on Main UI Thread
                                Log.i(TAG, "Thread id runOnUiThread: "+Thread.currentThread().getId()+",  Count: "+ count);
                                binding.txtCount.setText("" + count);
                            }


                        });
                    }
                    catch (InterruptedException e){
                        Log.i(TAG, e.getMessage());
                    }

                }
            }
        });
    }

    private void executeCustomLooper() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (stopLoop){
                    try {
                        // shows Looper Thread ID
                        Log.i(TAG, "Thread id that sends message: "+Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;

                        Message message = new Message();
                        message.obj = " " + count;
                        customHandlerThread.myHandler.sendMessage(message);
                    }
                    catch (InterruptedException e){
                        Log.i(TAG, e.getMessage());
                    }

                }
            }}).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(customHandlerThread != null){
            customHandlerThread.getLooper().quit();
        }
    }
}