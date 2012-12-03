package com.example.workshop;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter extends ArrayAdapter<Contact> {
	private Context con = null;
	private List<Contact> contacts = null;
	
	public Adapter(Context context, List<Contact> contacts) {
		super(context,R.layout.list_view, contacts);
		this.con = context;
		this.contacts = contacts;
	}
	
	@Override
	public View getView(int position, View v, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.list_view, parent, false);
		TextView name = (TextView) view.findViewById(R.id.name);
		
		name.setText(contacts.get(position).getName());
		notifyDataSetChanged();
		return view;
	}

}
