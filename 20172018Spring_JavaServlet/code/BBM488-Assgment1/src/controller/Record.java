package controller;

public class Record {

	public Record() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String ID = new String();
	private String Title = new String();
	private String Location = new String();
	private String Description = new String();
	private String StartTime = new String();
	private String EndTime = new String();
	
	
	public Record(String iD, String title, String location, String description, String startTime, String endTime) {
		super();
		ID = iD;
		Title = title;
		Location = location;
		Description = description;
		StartTime = startTime;
		EndTime = endTime;
	}


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getTitle() {
		return Title;
	}


	public void setTitle(String title) {
		Title = title;
	}


	public String getLocation() {
		return Location;
	}


	public void setLocation(String location) {
		Location = location;
	}


	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		Description = description;
	}


	public String getStartTime() {
		return StartTime;
	}


	public void setStartTime(String startTime) {
		StartTime = startTime;
	}


	public String getEndTime() {
		return EndTime;
	}


	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	
	

}
