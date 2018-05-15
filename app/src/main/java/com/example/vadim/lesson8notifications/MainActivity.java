package com.example.vadim.lesson8notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this.showNotificationAfterDelay(getNotificationWithProgres());
    OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init();
  }

  private void showNotificationAfterDelay(final NotificationCompat.Builder notification) {

    final NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        mNotificationManager.notify(0, notification.build());
      }
    }, 5000);
  }

  private NotificationCompat.Builder getBasicNotification() {

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
            .setSmallIcon(R.drawable.white_notification_icon)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    return mBuilder;
  }

  private NotificationCompat.Builder getNotificationWithAction() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
            .setSmallIcon(R.drawable.white_notification_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

    return mBuilder;
  }

  private NotificationCompat.Builder getNotificationWithProgres() {

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default");
    mBuilder.setContentTitle("Picture Download")
            .setContentText("Download in progress")
            .setSmallIcon(R.drawable.white_notification_icon)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE);

    int PROGRESS_MAX = 100;
    int PROGRESS_CURRENT = 40;
    mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
    return mBuilder;
  }
}
