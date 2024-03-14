package com.example.myapplication.LooperAndHandler.classes.activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLooperHandlerBinding;

public class LooperHandlerActivity extends AppCompatActivity {

    ActivityLooperHandlerBinding binding;
    private boolean stopLoop;
    int count = 0;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLooperHandlerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        handler = new Handler(getApplicationContext().getMainLooper());

        // Can not be restart
        binding.btnStart.setOnClickListener(view -> {

            stopLoop = true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (stopLoop){
                        try {
                            Thread.sleep(1000);
                            count++;
                        }
                        catch (InterruptedException e){
                            Log.i(TAG, e.getMessage());
                        }

                        Log.i(TAG, "Thread id:"+Thread.currentThread().getId() + " count: " + count);
                        // Not possible. so, we use handler
                        //binding.txtCount.setText(" " + count);

                        /*handler.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.txtCount.setText(" " + count);
                            }
                        });*/
                        // other method

                        binding.txtCount.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.txtCount.setText(" " + count);
                            }
                        });
                }
            }}).start();

        });

        binding.btnStop.setOnClickListener(view -> {
            stopLoop = false;
        });

    }
}