package com.example.workshop;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;


public class MainActivity extends ListActivity {
	private SQLiteDatabase database;
	private ContactsDatabase contactsDatabase;
	private Adapter adapter;
	private ContactsSource contactsSource;
	private List<Contact> contacts = new ArrayList<Contact>();
	private String name, number, email;
	public static boolean first = true, firsts = true;
	public static Contact contact;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        if(MainActivity.first)
        {
        	MainActivity.first = false;
        	contactsDatabase = new ContactsDatabase(this);					//para delete ra ni sa existing contacts
        	database = contactsDatabase.getWritableDatabase();				//kada run man gud sa program mu add cya sa database
        	database.delete(ContactsDatabase.TABLE_CONTACTS, null, null);	//murag clrscr() sa database...
        }
		ListView lv = getListView();
        lv.addHeaderView(getLayoutInflater().inflate(R.layout.activity_main, null));
	    lv.setAdapter(adapter);
	    
	    contactsSource = new ContactsSource(this);;
	    contactsSource.open();
	    
	   
	    contacts = contactsSource.getAllContacts();
	    
	    Cursor cursor = getName();
	    Cursor phones = getNumber(); 
	    Cursor emails = getEmail();
	    if(MainActivity.firsts){
	    	while (cursor.moveToNext() && phones.moveToNext() && emails.moveToNext()) {
	    		name = cursor.getString(cursor
	    				.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
	    		number = phones.getString(phones
	    				.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	    		email = emails.getString(emails
	    				.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));            
	    		Contact person = null;      
	    		MainActivity.firsts = false;
	    		person = contactsSource.createContact(name, number, email);	        
	    		contacts.add(person);	       
	    	}
        }
	    adapter = new Adapter(this, contacts);
	    setListAdapter(adapter);
	    cursor.close();
	    phones.close();
	    emails.close();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @SuppressWarnings("deprecation")
	private Cursor getName() {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] { ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME};
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
            + ("1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " ASC";

        
        return managedQuery(uri, projection, selection, selectionArgs,
            sortOrder);
    }
    
    @SuppressWarnings("deprecation")
   	private Cursor getNumber() {
           // Run query
           Uri uri =  ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
           String[] projection = new String[] { ContactsContract.Contacts._ID,
        		   ContactsContract.CommonDataKinds.Phone.NUMBER};
           String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
               + ("1") + "'";
           String[] selectionArgs = null;
           String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " ASC";
           
           return managedQuery(uri, projection, selection, selectionArgs,
               sortOrder);
    }
    
    @SuppressWarnings("deprecation")
   	private Cursor getEmail() {
           // Run query
           Uri uri =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
           String[] projection = new String[] { ContactsContract.Contacts._ID,
        		   ContactsContract.CommonDataKinds.Email.DATA};
           String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
               + ("1") + "'";
           String[] selectionArgs = null;
           String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " ASC";
           
           return managedQuery(uri, projection, selection, selectionArgs,
               sortOrder);
    } 
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
  
		Intent intent = new Intent(MainActivity.this, ContactActivity.class);
		MainActivity.contact = null;
		contact = (Contact) getListAdapter().getItem(position-1);
		intent.putExtra("Id", contact.getId());
		intent.putExtra("Name", contact.getName());
		intent.putExtra("Number", contact.getNumber());
		intent.putExtra("Email", contact.getEmail());
		
		MainActivity.this.startActivity(intent);
    }
}
