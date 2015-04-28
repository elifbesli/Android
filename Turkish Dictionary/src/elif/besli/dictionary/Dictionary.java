package elif.besli.dictionary;

import java.util.Calendar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Dictionary extends ListActivity {
	private EditText input;
	private TextView empty;
	private static final String TAG = "ALARM";
	private DictionaryDB dictionaryDB;
	private WordListAdapter adapter;
	
	public static final String FONT = "SolaimanLipi.ttf";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DatabaseInitializer initializer = new DatabaseInitializer(getBaseContext());
        initializer.initializeDataBase();
        dictionaryDB = new DictionaryDB(initializer);
        
        
        input = (EditText) findViewById(R.id.input);
        empty = (TextView) findViewById(android.R.id.empty);
        
        adapter = new WordListAdapter(this, dictionaryDB);
		setListAdapter(adapter);
        
        input.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				loadData(input.getText().toString());
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			public void afterTextChanged(Editable s) {
				
			}
		});
        
       
    }
    
    private void loadData(String word) {
		EnglishDataLoader loader = new EnglishDataLoader(dictionaryDB, adapter);
		loader.execute(word);
		
		if(word.equals("")) {
			empty.setText("");
		}
		else {
		
		TurkishDataLoader load = new TurkishDataLoader(dictionaryDB, adapter);
		load.execute(word);
		
		if(word.equals(""))
			empty.setText("");
		else
			empty.setText("No match found");
		}
		
    }
   
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if(this.getIntent().getExtras() != null){ //Olmaması durumunda uygulama kapanır
			Log.i(TAG,"extras: " + this.getIntent().getExtras());
		}
    	
    	loadData(input.getText().toString());
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
  
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId() == R.id.bookmarked_words) {
    		Intent intent = new Intent(this, BookMarkedWords.class);
    		startActivity(intent);
    		
    	}
    	else if(item.getItemId() == R.id.about) {
    		Intent intent = new Intent(this, About.class);
    		startActivity(intent);
    	}
    	
    	else if(item.getItemId() == R.id.add_new) {
    		showInputDialog();
    	}
    	
    	
    	return super.onOptionsItemSelected(item);	
    }
	
   
	public void showInputDialog() {
		LayoutInflater factory = LayoutInflater.from(this);

		final View addNew = factory.inflate(R.layout.add_new, null);
		

		final EditText english = (EditText) addNew.findViewById(R.id.english_input);
		final EditText turkish = (EditText) addNew.findViewById(R.id.turkish_input);
		
		turkish.setTypeface(Typeface.createFromAsset(getAssets(), Dictionary.FONT));

		final AlertDialog.Builder newWordInputDialog = new AlertDialog.Builder(this);
		newWordInputDialog
			.setTitle("Add a new word")
			.setView(addNew)
			.setPositiveButton("Save",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							String englishWord = english.getText().toString();
							String turkishWord = turkish.getText().toString();
							if((englishWord.equals("") || turkishWord.equals("")))
								Toast.makeText(getBaseContext(), "Field can't be blank",
										Toast.LENGTH_SHORT).show();
							else {
								
								dictionaryDB.addWord(englishWord, turkishWord);
								
								Toast.makeText(getBaseContext(), "Added successfully.",
										Toast.LENGTH_SHORT).show();

							}
						}
					})
			.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							
						}
					});
		newWordInputDialog.show();
	}
	
	public void createNotification(int a) {
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		
		Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(  this, 0, alarmIntent, 0);
		
		Calendar alarmStartTime = Calendar.getInstance();
		alarmStartTime.add(Calendar.MINUTE, a);
	 	alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval(), pendingIntent);
	 	Toast.makeText(getBaseContext(),"Noification'll send after "+a+" minute.",
				Toast.LENGTH_SHORT).show();
	}
	
	 public void setTime(View v) {
			LayoutInflater factory = LayoutInflater.from(this);

			final View addNew = factory.inflate(R.layout.notification_add, null);
			

			final EditText english = (EditText) addNew.findViewById(R.id.notification);
			
			
			english.setTypeface(Typeface.createFromAsset(getAssets(), Dictionary.FONT));

			final AlertDialog.Builder newWordInputDialog = new AlertDialog.Builder(this);
			newWordInputDialog
				.setTitle("Set notification time")
				.setView(addNew)
				.setPositiveButton("Save",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								String time = english.getText().toString();
								int time2=Integer.parseInt(time);
								if((time2==0))
									Toast.makeText(getBaseContext(), "Field can't be blank",
											Toast.LENGTH_SHORT).show();
								else {
									
									createNotification(time2);

								}
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								
							}
						});
			newWordInputDialog.show();
		}
	 
	private int getInterval(){
	    int seconds = 60;
	    int milliseconds = 1000;
	    int repeatMS = seconds * 1 * milliseconds;
	    return repeatMS;
	}
	
	 @Override
	 protected void onNewIntent( Intent intent ) {
		 Log.i( TAG, "onNewIntent(), intent = " + intent );
	        if (intent.getExtras() != null)
	        {
	            Log.i(TAG, "in onNewIntent = " + intent.getExtras().getString("test"));
	        }
	        super.onNewIntent( intent );
	        setIntent( intent );
	  }

		
		public void cancelNotifications(){
			Log.i(TAG,"All notifications cancelled.");
		}
		
		
		
}