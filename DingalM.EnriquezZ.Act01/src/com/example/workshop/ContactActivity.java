package com.example.workshop;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ContactActivity extends Activity {
	private TextView name, number, email;
	private ContactsDatabase contactsdb;
	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		
		name = (TextView) findViewById(R.id.c_name);
		number = (TextView) findViewById(R.id.c_number);
		email = (TextView) findViewById(R.id.c_email);
		Button back = (Button) findViewById(R.id.back);
		Button edit = (Button) findViewById(R.id.edit);
		Button delete = (Button) findViewById(R.id.delete);

		name.setText(getIntent().getExtras().getString("Name"));
		number.setText(getIntent().getExtras().getString("Number"));
		email.setText(getIntent().getExtras().getString("Email"));
		id = getIntent().getExtras().getInt("Id");
		
		contactsdb = new ContactsDatabase(this);
		
		back.setOnClickListener(new OnClickListener () {
			public void onClick(View v) {
	    		 Intent back = new Intent(ContactActivity.this, MainActivity.class);
	    		 ContactActivity.this.startActivity(back);
	    	 }
		});
		
		edit.setOnClickListener(new OnClickListener () {
			public void onClick(View v) {
	    		 Intent edit = new Intent(ContactActivity.this, EditActivity.class);
	    		 
	    		 edit.putExtra("Id", id);
	    		 edit.putExtra("Name", name.getText().toString());
	    		 edit.putExtra("Number", number.getText().toString());
	    		 edit.putExtra("Email", email.getText().toString());
	    		 
	    		 ContactActivity.this.startActivity(edit);
	    	 }
		});
		
		delete.setOnClickListener(new OnClickListener () {
			public void onClick(View v) {
	    		 SQLiteDatabase db = contactsdb.getWritableDatabase();
	    		 
	    		 contactsdb.deleteContact(db, MainActivity.contact.getId());
	    		 
	    		 Intent back = new Intent(ContactActivity.this, MainActivity.class);
	    		 ContactActivity.this.startActivity(back); 
	    	 }
		});
		
	}

}
