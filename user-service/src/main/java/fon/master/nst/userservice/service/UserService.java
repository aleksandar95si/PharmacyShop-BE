package fon.master.nst.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fon.master.nst.userservice.model.User;
import fon.master.nst.userservice.repository.TokenRepository;
import fon.master.nst.userservice.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenRepository tokenRepository;
	
	public void addUser(User user) {
		User userSave=new User();
		userSave.setUsername(user.getUsername());
		userSave.setEmail(user.getEmail());
		userSave.setPassword("{bcrypt}"+new BCryptPasswordEncoder(10).encode(user.getPassword()));
		userRepository.save(userSave);
	}
	
	public void logout(String accessToken) {
		tokenRepository.deleteByTokenId(accessToken);
	}
}
