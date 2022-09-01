package fon.master.nst.oauthserver.service;


import javax.transaction.Transactional;

import fon.master.nst.oauthserver.model.User;
import fon.master.nst.oauthserver.repository.TokenRepository;
import fon.master.nst.oauthserver.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private TokenRepository tokenRepository;

    public void addUser(User user) {
        User userSave = new User();
        userSave.setUsername(user.getUsername());
        userSave.setEmail(user.getEmail());
        userSave.setPassword("{bcrypt}" + new BCryptPasswordEncoder(10).encode(user.getPassword()));
        userDetailRepository.save(userSave);
    }

    public void logout(String username) {
        tokenRepository.deleteByUsername(username);
    }


}
