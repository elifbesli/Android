package elif.besli.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	private DictionaryDB dictionaryDB;
	private static final String TAG = "Turkish Dictionary";
	Intent intent;
	PendingIntent pendingIntent;
	NotificationManager notificationManager;
	String mean = null;
	String[] words;
	final ArrayList<String> array = new ArrayList<String>();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "BroadcastReceiver has received alarm intent.");
		
		DatabaseInitializer initializer = new DatabaseInitializer(context);
	    initializer.initializeDataBase();
	    dictionaryDB = new DictionaryDB(initializer);
	    
	    
	    List<Bean> wordList = dictionaryDB.getAllWords();
		 for (Bean word : wordList) {
			 
		       mean = word.toString();
		       array.add(mean);
		 }
		 
		        Intent service1 = new Intent(context, AlarmService.class);
				service1.putExtra("word", array);
				context.startService(service1);

	}

}
