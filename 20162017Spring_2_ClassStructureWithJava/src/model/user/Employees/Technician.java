package model.user.Employees;

import java.util.Date;

public class Technician extends Employee{
	private boolean isSenior;

	/**
	 * @param name
	 * @param email
	 * @param birthDay
	 * @param salary
	 * @param isSenior
	 */
	public Technician(String name, String email, Date birthDay, int salary, boolean isSenior) {
		super(name, email, birthDay, salary);
		this.isSenior = isSenior;
	}

	/**
	 * @return the isSenior
	 */
	public boolean isSenior() {
		return isSenior;
	}

	/**
	 * @param isSenior the isSenior to set
	 */
	public void setSenior(boolean isSenior) {
		this.isSenior = isSenior;
	}
	
	
}
