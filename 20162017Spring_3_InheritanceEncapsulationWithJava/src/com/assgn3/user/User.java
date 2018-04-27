package com.assgn3.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.assgn3.post.Post;

public class User {
	
	private int userID;
	private String name;
	private String username;
	private String password;
	private Date dateOfBirth;
	private String schoolInformation;
	private Date lastLoginDate;
	private ArrayList<String> friends;
	private ArrayList<String> blockedUsers;
	private ArrayList<Post> posts;
	
	
	
	/**
	 * @param userID
	 * @param name
	 * @param username
	 * @param password
	 * @param dateOfBirth
	 * @param schoolInformation
	 */
	public User(int userID, String name, String username, String password, Date dateOfBirth, String schoolInformation) {
		super();
		this.userID = userID;
		this.name = name;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.schoolInformation = schoolInformation;
		this.lastLoginDate = new Date();
		this.friends = new ArrayList<String>();
		this.blockedUsers = new ArrayList<String>();
		this.posts = new ArrayList<Post>();
	}
	
	
	/**
	 * 
	 */
	public User() {
		super();
	}


	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the schoolInformation
	 */
	public String getSchoolInformation() {
		return schoolInformation;
	}
	/**
	 * @param schoolInformation the schoolInformation to set
	 */
	public void setSchoolInformation(String schoolInformation) {
		this.schoolInformation = schoolInformation;
	}
	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	/**
	 * @return the friends
	 */
	public ArrayList<String> getFriends() {
		return friends;
	}
	/**
	 * @param friends the friends to set
	 */
	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}
	/**
	 * @return the blockedUsers
	 */
	public ArrayList<String> getBlockedUsers() {
		return blockedUsers;
	}
	/**
	 * @param blockedUsers the blockedUsers to set
	 */
	public void setBlockedUsers(ArrayList<String> blockedUsers) {
		this.blockedUsers = blockedUsers;
	}
	/**
	 * @return the posts
	 */
	public ArrayList<Post> getPosts() {
		return posts;
	}
	/**
	 * @param posts the posts to set
	 */
	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
	
	/**
	 * toString method
	 */
	public String toString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String instanceString = "Name: ";
		instanceString = instanceString + this.name+"\n";
		instanceString = instanceString + "Username: " + this.username + "\n";
		instanceString = instanceString + "Date of Birth: " + dateFormat.format(this.dateOfBirth) + "\n";
		instanceString = instanceString + "School: " + this.schoolInformation ;
		return instanceString;
	}
	
	
	public int getTotalPostNumber(){
		return posts.size();
	}
}
