package org.rest.demo.model;

import java.util.ArrayList;

public class Users {

	private ArrayList<User> users;

	public Users() {
		
	}
	
	public Users(ArrayList<User> users) {
		this.users = users;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
}
