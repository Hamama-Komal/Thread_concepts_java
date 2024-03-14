package com.example.myapplication.SimpleThread;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityUithreadBinding;

public class UIThreadActivity extends AppCompatActivity {

    ActivityUithreadBinding binding;
    private boolean stopLoop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUithreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.i(TAG, "Thread id: "+ Thread.currentThread().getId());

        binding.btnStart.setOnClickListener(view -> {

            stopLoop = true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (stopLoop){
                        Log.i(TAG, "Thread id: "+ Thread.currentThread().getId());

                    }
                }
            }).start();
        });

        binding.btnStop.setOnClickListener(view -> {
            stopLoop = false;
        });

    }
}