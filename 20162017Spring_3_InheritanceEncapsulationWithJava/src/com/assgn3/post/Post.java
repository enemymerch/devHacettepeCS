package com.assgn3.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.assgn3.location.Location;

public abstract class Post implements PostInterface{
	
	protected UUID postID;
	protected String text;
	protected Date originationDate;
	protected ArrayList<String> taggedUsers;
	protected Location location;
	
	public abstract void setPostID(UUID postID);

	public abstract void setOriginationDate(Date originationDate);

	public abstract ArrayList<String> getTaggedUsers() ;

	public abstract void setTaggedUsers(ArrayList<String> taggedUsers) ;

	public abstract Location getLocation() ;
	
	public abstract void setLocation(Location location);
}
