package com.capgemini.cab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.cab.entity.BookRide;
import com.capgemini.cab.entity.User;
import com.capgemini.cab.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)

public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private RestTemplate restTemplate;
	

	@PostMapping("/signUp")
	public ResponseEntity<User> signUpDetailsOfUser(@RequestBody User user) {

		User status = service.addDetails(user);

		return new ResponseEntity<User>(status, HttpStatus.CREATED);

	}

	@GetMapping("/loginuser/{email}/{password}")
	public ResponseEntity<User> logInDetailsForUser(@PathVariable String email, @PathVariable String password)
			throws NullPointerException {
		System.out.println(email);
		System.out.println(password);

		User user1 = service.findByEmail(email);
		System.out.println(user1.getEmail());
		System.out.println(user1.getPassword());

		if ((user1.getEmail().equals(email) && (user1.getPassword().equals(password)))) {

			return new ResponseEntity<User>(user1, HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);

	}

	@PostMapping("/bookride")
	public ResponseEntity<BookRide> bookRideForUser(@RequestBody BookRide bookRide) {
		BookRide b=restTemplate.getForEntity("http://localhost:8012/distancecalculator"+bookRide.getPickUpAt()+bookRide.getDropAt(),BookRide.class).getBody();
		System.out.println(b.getPickUpAt());
		System.out.println(b.getDropAt());

		return null;

	}

}
