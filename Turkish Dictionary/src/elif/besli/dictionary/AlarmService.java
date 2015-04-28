package elif.besli.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.IntentService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
                            

 public class AlarmService extends IntentService 
{
      
   private static final int NOTIFICATION_ID = 1;
   private static final String TAG = "Turkish Dictionary";
   private NotificationManager notificationManager;
   private PendingIntent pendingIntent;
   int i;
  
   public AlarmService() {
	      super("AlarmService");
	  }
   
   
   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
       return super.onStartCommand(intent,flags,startId);
   }
   
   @Override
   protected void onHandleIntent(Intent intent) {
           // don't notify if they've played in last 24 hr
	   Log.i(TAG,"Alarm Service has started.");
       Context context = this.getApplicationContext();
       notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
       
       Bundle bund = intent.getExtras();
       ArrayList<String> array = (ArrayList<String>)bund.getStringArrayList("word");
       Random r = new Random(); //random sýnýfý
       int i = array.size();
       int index = r.nextInt(i);
       String word = array.get(index);
      
        Intent mIntent = new Intent(this, Dictionary.class);
        Bundle bundle = new Bundle(); 
        bundle.putString("test", "test");
        mIntent.putExtras(bundle);
		pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);     
		
		 
		
		Resources res = this.getResources();
		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentIntent(pendingIntent)
		            .setSmallIcon(R.drawable.ic_launcher)
		            .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
		            .setTicker(res.getString(R.string.notification_title))
		            .setAutoCancel(true)
		            .setContentTitle(res.getString(R.string.notification_title))
		            .setContentText(word);

		notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, builder.build());
		Log.i(TAG,"Notifications sent.");
		
		}

    }
 
