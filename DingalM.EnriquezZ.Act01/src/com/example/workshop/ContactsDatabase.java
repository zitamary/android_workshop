package com.example.workshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactsDatabase extends SQLiteOpenHelper {

	  public static final String TABLE_CONTACTS = "contacts";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_NUMBER = "number";
	  public static final String COLUMN_EMAIL = "email";
	  
	  private static final String DATABASE_NAME = "test.db";
	  private static final int DATABASE_VERSION = 1;
	  
	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_CONTACTS + "(" + COLUMN_ID
	      + " integer primary key autoincrement, "
	      + COLUMN_NAME + " text not null,"
	      + COLUMN_NUMBER + " text not null," 
	      + COLUMN_EMAIL + " text not null);";

	  public ContactsDatabase(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(ContactsDatabase.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
	    onCreate(db);
	  }
	  
	  public void updateContact(SQLiteDatabase db, String name, String number, String email, long id)
	  {
		  ContentValues args = new ContentValues();
		  args.put(COLUMN_NAME, name);
		  args.put(COLUMN_NUMBER, number);
		  args.put(COLUMN_EMAIL, email);
		  db.update(TABLE_CONTACTS, args,
				  COLUMN_ID + "=" + id, null);
	  }
	  
	  public void deleteContact(SQLiteDatabase db, long id)
	  {
		  Log.d("ID: " + id, "Name: ");
		  db.delete(TABLE_CONTACTS, COLUMN_ID + "=" + id, null);
		 
	  }
	  
	} 
