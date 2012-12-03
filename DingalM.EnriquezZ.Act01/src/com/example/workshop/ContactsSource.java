package com.example.workshop;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ContactsSource {

	  // Database fields
	  private SQLiteDatabase database;
	  private ContactsDatabase dbHelper;
	  private String[] allColumns = { 
			  ContactsDatabase.COLUMN_ID,
			  ContactsDatabase.COLUMN_NAME, 
			  ContactsDatabase.COLUMN_NUMBER, 
			  ContactsDatabase.COLUMN_EMAIL };

	  public ContactsSource(Context context) {
	    dbHelper = new ContactsDatabase(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Contact createContact(String name, String number, String email) {
	    ContentValues values = new ContentValues();
	    values.put(ContactsDatabase.COLUMN_NAME, name);
	    values.put(ContactsDatabase.COLUMN_NUMBER, number);
	    values.put(ContactsDatabase.COLUMN_EMAIL, email);
	    
	    long insertId = database.insert(ContactsDatabase.TABLE_CONTACTS, null,
	        values);
	    Log.d("ID: " + insertId, "Name: " + name);
	    Cursor cursor = database.query(ContactsDatabase.TABLE_CONTACTS,
	        allColumns, ContactsDatabase.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Contact newContact = cursorToContact(cursor);
	    cursor.close();
	    return newContact;
	  }

	  public List<Contact> getAllContacts() {
	    List<Contact> contacts = new ArrayList<Contact>();

	    Cursor cursor = database.query(ContactsDatabase.TABLE_CONTACTS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Contact contact = cursorToContact(cursor);
	      contacts.add(contact);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return contacts;
	  }

	  private Contact cursorToContact(Cursor cursor) {
		Contact contact = new Contact();
		contact.setId(cursor.getLong(0));
		contact.setName(cursor.getString(1));
		contact.setNumber(cursor.getString(2));
		contact.setEmail(cursor.getString(3));
	    return contact;
	  }
	} 