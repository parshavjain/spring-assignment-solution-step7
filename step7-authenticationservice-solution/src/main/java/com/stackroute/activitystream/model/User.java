package com.stackroute.activitystream.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/*
 * The class "User" will be acting as the data model for the user Table in the database. Please
 * note that this class is annotated with @Entity annotation. Hibernate will scan all package for 
 * any Java objects annotated with the @Entity annotation. If it finds any, then it will begin the 
 * process of looking through that particular Java object to recreate it as a table in your database.
 *
 * Please note that you will have to use @Component annotation on this class if wish
 * to autowire the class from any other components of the application
 */
@Entity
@Component
@Table(name = "user")
public class User {
	/*
	 * This class should have three fields
	 * (username,name,password). Out of these three fields, the
	 * field username should be the primary key. This class should also contain
	 * the getters and setters for the fields.
	 */
	
	@Id
	@Column(name = "username", nullable = false)
	public String username;

	@Column(name = "name", nullable = false)
	public String name;

	@Column(name = "password", nullable = false)
	public String password;

	public User(String username, String name, String password) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	public void setName(String string) {
		this.name = string;
	}

	public void setPassword(String string) {
		this.password = string;

	}

	public void setUsername(String string) {
		this.username = string;

	}

	public String getPassword() {
		return this.password;
	}

	/**
	 * @return the userName
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
}