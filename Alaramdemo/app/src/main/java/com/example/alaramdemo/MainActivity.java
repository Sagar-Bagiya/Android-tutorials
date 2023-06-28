package com.example.alaramdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        Intent intent = new Intent(this, MyBroadcastReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, 0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.parseInt(editText.getText().toString());
                long trigerTime = time * 1000 + System.currentTimeMillis();

                alarmManager.set(AlarmManager.RTC_WAKEUP, trigerTime, pendingIntent);
                Toast.makeText(MainActivity.this, "Alarm set in " + time + " seconds",Toast.LENGTH_LONG).show();
            }
        });

    }


}