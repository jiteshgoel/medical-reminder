package com.example.medicinereminder.medicinereminder.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import com.example.medicinereminder.medicinereminder.Activities.MainActivity;
import com.example.medicinereminder.medicinereminder.R;

/**
 * Created by sukrit on 10/2/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        Log.d("notif_task", "onReceive: " + intent.getStringExtra("task"));

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentText(intent.getStringExtra("task")).setContentTitle("Task").setOngoing(true);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);

        int notifyId = intent.getIntExtra("millis",0);
        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,notifyId

                , intent2, 0);


        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setOngoing(false);


        Notification notification = builder.build();
        // this is the main thing to do to make a non removable notification
        // 2nd method for permanent notification: is to add flags..(for API levels < 11)
        //  notification.flags |=Notification.FLAG_NO_CLEAR;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //manager.notify(sPref.getInt("size",-1), notification);
        manager.notify((int)System.currentTimeMillis(), notification);

    }
}
