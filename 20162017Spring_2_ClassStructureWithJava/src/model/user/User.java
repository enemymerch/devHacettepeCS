package model.user;

import java.util.Date;

public class User {
	private String Name;
	private String Email;
	private Date BirthDay;
	
	/**
	 * @param name
	 * @param email
	 * @param birthDay
	 */
	public User(String name, String email, Date birthDay) {
		super();
		Name = name;
		Email = email;
		BirthDay = birthDay;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}

	/**
	 * @return the birthDay
	 */
	public Date getBirthDay() {
		return BirthDay;
	}

	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(Date birthDay) {
		BirthDay = birthDay;
	}
	
	
}
