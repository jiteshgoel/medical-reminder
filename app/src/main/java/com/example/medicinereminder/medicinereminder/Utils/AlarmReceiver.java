package com.example.medicinereminder.medicinereminder.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import com.example.medicinereminder.medicinereminder.Activities.MainActivity;
import com.example.medicinereminder.medicinereminder.Models.Task;
import com.example.medicinereminder.medicinereminder.R;

public class AlarmReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";
    DatabaseHelper dbHelp;
    int found=0;


    @Override
    public void onReceive(Context context, Intent intent) {
        dbHelp=new DatabaseHelper(context);
        Cursor cursor = dbHelp.fetchData();
        while (cursor.moveToNext())
        {
            String taskName = cursor.getString(cursor.getColumnIndex("task"));
            String taskStatus = cursor.getString(cursor.getColumnIndex("status"));
            String taskDate = cursor.getString(cursor.getColumnIndex("date"));
            String taskTime = cursor.getString(cursor.getColumnIndex("time"));
            Task task=new Task(taskName,taskStatus,taskDate,taskTime);
            if(taskName.equalsIgnoreCase(intent.getStringExtra("task")))
            {
                Log.d("TAG", "onReceive:found "+"1");
                found=1;
                break;
            }
        }
        Log.d("TAG", "onReceive:found "+found);


        Log.d("notif_task", "onReceive: " + intent.getStringExtra("task"));

        if(found==1) {
            MediaPlayer mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
            mediaPlayer.start();
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentText(intent.getStringExtra("task")).setContentTitle("Task").setOngoing(true);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);

            int notifyId = intent.getIntExtra("millis", 0);
            Intent intent2 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, notifyId
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
            manager.notify((int) System.currentTimeMillis(), notification);
        }

    }
}
/*
package com.example.medicinereminder.medicinereminder.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.RemoteViews;

import com.example.medicinereminder.medicinereminder.R;

*/
/**
 * Created by sukrit on 10/2/18.
 *//*


public class AlarmReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";
    int notify_id;
    NotificationManager notificationManager;
    RemoteViews remoteViews;

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        notify_id=intent.getIntExtra("millis",0);
        Intent button_intent=new Intent("button_clicked");
        button_intent.putExtra("id",notify_id);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,123,button_intent,0);
        remoteViews.setOnClickPendingIntent(R.id.notifBtn,pendingIntent);

        notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        remoteViews=new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.messageDetails,intent.getStringExtra("task"));
    }
}
*/

