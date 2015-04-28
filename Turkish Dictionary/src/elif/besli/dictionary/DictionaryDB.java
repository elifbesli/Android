package elif.besli.dictionary;

import java.util.ArrayList;


import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

public class DictionaryDB {
	
	public static final String ID = "_id";
	public static final String ENGLISH = "en_word";
	public static final String TURKISH = "tr_word";
	public static final String STATUS = "status";
	public static final String USER = "user_created";
    public static final String TABLE_NAME = "words";
    public static final String DATABASE_NAME = "sozluk";
	public static final String BOOKMARKED = "b";
	public static final String SETTINGS = "s";
	public static final String USER_CREATED = "u";
	public static int DATABASE_VERSION=1;
	
	
	DatabaseInitializer initializer;
	
	public DictionaryDB(DatabaseInitializer initializer) {
		this.initializer = initializer;
	}
	
	
	public void addWord(String englishWord, String turkishWord) {
		SQLiteDatabase db = initializer.getWritableDatabase();
		
		String sql = "INSERT INTO " + TABLE_NAME + " (" + ENGLISH + 
				", " + TURKISH + ") VALUES ('" + englishWord +
				"', '" + turkishWord + "') ";
		db.execSQL(sql);
	}
	
	
	public List<Bean> getEnglishWords(String englishWord) {
		if(englishWord.equals(""))
			return new ArrayList<Bean>();
		
		
		String sql = "SELECT * FROM " + TABLE_NAME +
    			" WHERE "  +ENGLISH+" LIKE ? ORDER BY " +ENGLISH + " LIMIT 100 ";
		
		SQLiteDatabase db = initializer.getReadableDatabase();

		Cursor cursor = null;
		try {
			 cursor = db.rawQuery(sql, new String[]{englishWord+"%"});
	       
	        List<Bean> wordList = new ArrayList<Bean>();
	        while(cursor.moveToNext()) {
	        	int id = cursor.getInt(0);
	        	String english = cursor.getString(1);
	        	String turkish = cursor.getString(2);
	        	String status = cursor.getString(3);
				wordList.add(new Bean(id, english, turkish, status));
			}
	        
	        return wordList;
		} catch (SQLiteException exception) {
			exception.printStackTrace();
			return null;
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}
	public List<Bean> getTurkishWords(String turkishWord) {
		if(turkishWord.equals(""))
			return new ArrayList<Bean>();
		
		
		String sql = "SELECT * FROM " + TABLE_NAME +
    			" WHERE "  +TURKISH+" LIKE ? ORDER BY " +TURKISH + " LIMIT 100 ";
		
		SQLiteDatabase db = initializer.getReadableDatabase();

		Cursor cursor = null;
		try {
			 cursor = db.rawQuery(sql, new String[]{turkishWord+"%"});
	       
	        List<Bean> wordList = new ArrayList<Bean>();
	        while(cursor.moveToNext()) {
	        	int id = cursor.getInt(0);
	        	String english = cursor.getString(1);
	        	String turkish = cursor.getString(2);
	        	String status = cursor.getString(3);
				wordList.add(new Bean(id, english, turkish, status));
			}
	        
	        return wordList;
		} catch (SQLiteException exception) {
			exception.printStackTrace();
			return null;
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}
	
	public List<Bean> getBookmarkedWords() {
		SQLiteDatabase db = initializer.getReadableDatabase();
		
		String sql = "SELECT * FROM " + TABLE_NAME +
    			" WHERE " + STATUS + " = '" + BOOKMARKED + "'";
        
        Cursor cursor = db.rawQuery(sql, null);
        
        List<Bean> wordList = new ArrayList<Bean>();
        while(cursor.moveToNext()) {
        	int id = cursor.getInt(0);
        	String english = cursor.getString(1);
        	String turkish = cursor.getString(2);
        	String status = cursor.getString(3);
			wordList.add(new Bean(id, english, turkish, status));
		}
        
        cursor.close();
        db.close();
        return wordList;
	}
	
	public void bookmark(int _id) {
		SQLiteDatabase db = initializer.getWritableDatabase();
		
		String sql = "UPDATE " + TABLE_NAME + " SET " + STATUS + " = '"
				+ BOOKMARKED + "' WHERE " + ID + " = " + _id;
		db.execSQL(sql);
		db.close();
	}
	
	public void deleteBookmark(int _id) {
		SQLiteDatabase db = initializer.getWritableDatabase();
		
		String sql = "UPDATE " + TABLE_NAME + " SET " + STATUS + " = '' " +
				" WHERE " + ID + " = " + _id;
		db.execSQL(sql);
		db.close();
	}
public List<Bean> getAllWords() {
		
		String sql = "SELECT * FROM " + TABLE_NAME;
		
		SQLiteDatabase db = initializer.getWritableDatabase();

		Cursor cursor = null;
		try {
			 cursor = db.rawQuery(sql,null);
	       
	        List<Bean> wordList = new ArrayList<Bean>();
	        while(cursor.moveToNext()) {
	        	int id = cursor.getInt(0);
	        	String english = cursor.getString(1);
	        	String turkish = cursor.getString(2);
	        	String status = cursor.getString(3);
	        	wordList.add(new Bean(id, english, turkish, status));
	        	
			}
	        
	        return wordList;
		} catch (SQLiteException exception) {
			exception.printStackTrace();
			return null;
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}



}
