package model.user.Employees;

import java.util.Date;

import model.user.User;

public class Employee extends User{
	private int Salary;

	/**
	 * @param name
	 * @param email
	 * @param birthDay
	 * @param salary
	 */
	public Employee(String name, String email, Date birthDay, int salary) {
		super(name, email, birthDay);
		Salary = salary;
	}

	/**
	 * @return the salary
	 */
	public int getSalary() {
		return Salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(int salary) {
		Salary = salary;
	}
	
	
}
