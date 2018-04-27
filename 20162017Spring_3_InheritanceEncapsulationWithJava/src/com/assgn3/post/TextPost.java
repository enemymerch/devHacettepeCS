package com.assgn3.post;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.assgn3.location.Location;

public class TextPost extends Post{

	
	
	
	/**
	 * 
	 * @return
	 */
	public String toString(){
		DateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
		return this.text+"\nDate: "+ dateformat.format(this.originationDate)+"\n" + this.location.toString();	
	}

	@Override
	public void setText(String text){
		this.text = text;
	}

	@Override
	public String getText(){
		return text;
	}

	@Override
	public UUID getID(){
		return postID;
	}

	@Override
	public Date getDate(){
		return originationDate;
	}

	@Override
	public void setPostID(UUID postID) {
		this.postID = postID;
	}
	
	@Override
	public void setOriginationDate(Date originationDate) {
		this.originationDate = originationDate;
	}
	
	@Override
	public ArrayList<String> getTaggedUsers() {
		return taggedUsers;
	}

	@Override
	public void setTaggedUsers(ArrayList<String> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
	}
}
