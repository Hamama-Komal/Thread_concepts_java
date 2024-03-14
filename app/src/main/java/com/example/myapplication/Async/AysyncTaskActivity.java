package com.example.myapplication.Async;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAysyncTaskBinding;

public class AysyncTaskActivity extends AppCompatActivity {

    ActivityAysyncTaskBinding binding;
    private boolean stopLoop;
    int count = 0;

    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAysyncTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myAsyncTask = new MyAsyncTask();
        // Can not be restart
        binding.btnStart.setOnClickListener(view -> {

            stopLoop = true;

            myAsyncTask.execute(count);

        });

        binding.btnStop.setOnClickListener(view -> {
           // stopLoop = false;
            myAsyncTask.cancel(true);
        });

    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        private int customCounter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCounter = 0;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            customCounter = integers[0];

            while (stopLoop) {
                try {
                    Thread.sleep(1000);
                    customCounter++;
                    publishProgress(customCounter);
                } catch (InterruptedException e) {
                    Log.i(TAG, e.getMessage());
                }
                if(isCancelled()){
                    break;
                }
            }
            return customCounter;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            binding.txtCount.setText("" + integer);
            count = integer;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            binding.txtCount.setText("" + values[0]);
        }
    }
}