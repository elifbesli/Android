package elif.besli.dictionary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseInitializer extends SQLiteOpenHelper {

	public static final String DATABASE_CREATE = 
			"CREATE TABLE " + DictionaryDB.TABLE_NAME + " (" +
			DictionaryDB.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DictionaryDB.ENGLISH + " TEXT NOT NULL, " +
            DictionaryDB.TURKISH + " TEXT,"+ DictionaryDB.STATUS + " TEXT );";
	
	public static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + DictionaryDB.TABLE_NAME;
	
	public DatabaseInitializer(Context context) {
		super(context, DictionaryDB.DATABASE_NAME, null, DictionaryDB.DATABASE_VERSION);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w("DatabaseHelper", "Veritabani " + oldVersion + "\'dan" + newVersion + "\'a guncelleniyor");
		
		db.execSQL(DATABASE_DROP);
		onCreate(db);
		
	}
	public void initializeDataBase() {
        /*
         * Creates or updates the database in internal storage if it is needed
         * before opening the database. In all cases opening the database copies
         * the database in internal storage to the cache.
         */
        getWritableDatabase();


	}
}