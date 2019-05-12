package gent.zeus.tappb.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import gent.zeus.tappb.R;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("ENABLE_NOTIFICATIONS", false)) {
            Map<String, String> data = remoteMessage.getData();
            String body = data.getOrDefault("body", "");
            String title = data.getOrDefault("title", "Tabbp notification");
            // Create new notification channel, this does nothing if it already exists
            NotificationChannel channel = new NotificationChannel("TAPPB",
                    "Tappb notification channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            // Create new notification
            Notification notification = new NotificationCompat.Builder(this, channel.getId())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body))
                    .build();
            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
            manager.notify(new Random().nextInt(1024), notification);
        }
    }
}