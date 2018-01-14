package model.user.Employees;

import java.util.Date;

public class Admin extends Employee{
	private String Password;

	/**
	 * @param name
	 * @param email
	 * @param birthDay
	 * @param salary
	 * @param password
	 */
	public Admin(String name, String email, Date birthDay, int salary, String password) {
		super(name, email, birthDay, salary);
		Password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}
	
	
}
