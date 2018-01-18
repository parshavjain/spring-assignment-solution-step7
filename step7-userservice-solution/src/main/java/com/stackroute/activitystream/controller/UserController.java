package com.stackroute.activitystream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.activitystream.model.User;
import com.stackroute.activitystream.service.UserService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

	/*
	 * Autowiring should be implemented for the UserService. 
	 * Please note that we should not create any object using the new keyword 
	 */
	@Autowired
	private UserService userService;
	
	/* Define a handler method which will list all the available users.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 
	 * This handler method should map to the URL "/api/user" using HTTP GET method
	*/
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<List<User>>(userService.list(), HttpStatus.OK);
	}
	
	/* Define a handler method which will show details of a specific user.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 2. 404(NOT FOUND) - If the user with the searched for is not found
	 * This handler method should map to the URL "/api/user/{username}" using HTTP GET method
	 * where "username" should be replaced by a username without {}
	*/
	@RequestMapping(value = "/{username}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		if(null != username
				&& !username.isEmpty()) {
			User user = userService.get(username);
			if(null != user) {
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}			
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	/* Define a handler method which will create a specific user by reading the 
	 * Serialized object from request body and save the user details in user table 
	 * in database. This handler method should return any one of the status messages
	 *  basis on different situations:
	 * 1. 201 (CREATED) - If the user is successfully created
	 * 2. 409 (CONFLICT) - If the username conflicts with any existing user
	 * 
	 * Note:
	 * ------
	 * This method can be called without being logged in as well as when a new user will
	 * use the app, he will register himself first before login.
	 * This handler method should map to the URL "/api/user" using HTTP POST method
	*/
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		if (null != user && null != user.getUsername()) {
			User tempUser = userService.get(user.getUsername());
			if (null == tempUser) {
				userService.save(user);
				return new ResponseEntity<User>(user, HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<User>(HttpStatus.CONFLICT);
	}
	
	/* Define a handler method which will update an specific user by reading the 
	 * Serialized object from request body and save the updated user details in user table 
	 * in database. This handler method should return any one of the status messages
	 *  basis on different situations:
	 * 1. 200(OK) - If the user is successfully updated
	 * 2. 404(NOT FOUND) - If the user with specified username is not found
	 * 
	 * This handler method should map to the URL "/api/user/{username}" using HTTP PUT method
	*/
	@RequestMapping(value = "/{username}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody User user) {		
		if(null != username) {
			User tempUser = userService.get(username);
			if(null != user && null != tempUser) {
				userService.update(user);
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
}
