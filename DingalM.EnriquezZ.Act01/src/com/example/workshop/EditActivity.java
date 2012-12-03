package com.example.workshop;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {
	private ContactsDatabase contactsDatabase;
	private EditText name, number, email;
	private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		name = (EditText) findViewById(R.id.ET_name);
		number = (EditText) findViewById(R.id.ET_num);
		email = (EditText) findViewById(R.id.ET_email);
		
		Button save = (Button) findViewById(R.id.save);
		Button back = (Button) findViewById(R.id.edit_back);
				
		name.setText(getIntent().getExtras().getString("Name"));
		number.setText(getIntent().getExtras().getString("Number"));
		email.setText(getIntent().getExtras().getString("Email"));
		id = getIntent().getExtras().getInt("Id");
		
		contactsDatabase = new ContactsDatabase(this);
		
		back.setOnClickListener(new OnClickListener () {
			public void onClick(View v) {
	    		 Intent back = new Intent(EditActivity.this, ContactActivity.class);
	    		 
	    		 back.putExtra("Id", id);
	    		 back.putExtra("Name", name.getText().toString());
	    		 back.putExtra("Number", number.getText().toString());
	    		 back.putExtra("Email", email.getText().toString());
	    		 
	    		 EditActivity.this.startActivity(back);
	    	 }
		});
		
		save.setOnClickListener(new OnClickListener () {
			public void onClick(View v) {			
		        SQLiteDatabase database = contactsDatabase.getWritableDatabase();
				contactsDatabase.updateContact(database, name.getText().toString(), 
						number.getText().toString(), email.getText().toString(), MainActivity.contact.getId());
				
				Intent save = new Intent(EditActivity.this, ContactActivity.class);
				
				save.putExtra("Name", name.getText().toString());
	    		save.putExtra("Number", number.getText().toString());
	    		save.putExtra("Email", email.getText().toString());
				
	    		EditActivity.this.startActivity(save);
	    	 }
		});
	}
}
