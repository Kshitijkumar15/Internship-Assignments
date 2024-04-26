package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView bpressure, temp;
    EditText bp, t;
    Integer bp_int, temp_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bp = findViewById(R.id.bp);
        t = findViewById(R.id.temp);
        btn = findViewById(R.id.check);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp_int = Integer.parseInt(bp.getText().toString());
                temp_int = Integer.parseInt(t.getText().toString());
                String msg;
                int notificationId = 100;
                if ((bp_int >= 60 && bp_int <= 100) || temp_int == 98) {
                    msg = "you are normal";
                } else {
                    msg = "you are abnormal";
                }
                String CHANNEL_ID = "ch";
                String CHANNEL_NAME = "channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
                Intent i = new Intent(MainActivity.this, NotificationActivity.class);
                i.putExtra("Message", msg);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent intent = PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_IMMUTABLE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
                builder.setSmallIcon(R.drawable.noti);
                builder.setContentIntent(intent);
                builder.setContentTitle("Emergency");
                builder.setContentText("BP: " + bp_int + "Temperature: " + temp_int);
                manager.notify(notificationId, builder.build());
            }
        });
    }
}