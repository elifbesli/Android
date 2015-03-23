/**
 * this is free software 
 * It is under the The MIT License http://www.opensource.org/licenses/mit-license.php
 */
package root.magicword;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
 
public class MagicWord extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
 
 
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_5:
                scheduleNotification(getNotification("5 second delay"), 5000);
                return true;
            case R.id.action_10:
                scheduleNotification(getNotification("10 second delay"), 10000);
                return true;
            case R.id.action_30:
                scheduleNotification(getNotification("30 second delay"), 30000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
 
    private void scheduleNotification(Notification notification, int delay) {
 
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
 
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
 
    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.icon);
        return builder.build();
    }
 
}