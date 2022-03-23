package fon.master.nst.userservice.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fon.master.nst.userservice.model.User;
import fon.master.nst.userservice.repository.TokenRepository;
import fon.master.nst.userservice.repository.UserRepository;
import fon.master.nst.userservice.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    public void addUser(User user) {
        User userSave = new User();
        userSave.setUsername(user.getUsername());
        userSave.setEmail(user.getEmail());
        userSave.setPassword("{bcrypt}" + new BCryptPasswordEncoder(10).encode(user.getPassword()));
        userRepository.save(userSave);
    }

    public void logout(String username) {
        tokenRepository.deleteByUsername(username);
    }
}
