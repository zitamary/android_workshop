package com.example.workshop;

public class Contact{
	  private long id;
	  private String name, number, email;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }
	  
	  public String getNumber() {
		    return number;
	  }
	  
	  public void setNumber(String number) {
		    this.number = number;
	  }
	  
	  public void setEmail(String email) {
		    this.email = email;
	  }
	  
	  public String getEmail() {
		    return email;
	  }

	  

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return name;
	  }
	} 
