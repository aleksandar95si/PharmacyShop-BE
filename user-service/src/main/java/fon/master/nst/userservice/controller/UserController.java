package fon.master.nst.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fon.master.nst.userservice.model.User;
import fon.master.nst.userservice.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@PostMapping("/save")
	public ResponseEntity addUser(@RequestBody User user) {
		userServiceImpl.addUser(user);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/logout/{username}")
	public void logout(@PathVariable("username") String username) {
		userServiceImpl.logout(username);
	}	
}
