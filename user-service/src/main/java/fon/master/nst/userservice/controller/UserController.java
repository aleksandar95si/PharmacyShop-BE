package fon.master.nst.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fon.master.nst.userservice.model.User;
import fon.master.nst.userservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public void addUser(@RequestBody User user) {
		userService.addUser(user);
	}
	
	@DeleteMapping("/logout/{token}")
	public void logout(@PathVariable("token") String token) {
		userService.logout(token);
	}	
}
