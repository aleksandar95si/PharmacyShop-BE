package fon.master.nst.userservice.service;

import fon.master.nst.userservice.model.User;

public interface UserService {
	void addUser(User user);
	void logout(String username);
}
