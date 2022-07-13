package fon.master.nst.oauthserver.controller;

import fon.master.nst.oauthserver.model.User;
import fon.master.nst.oauthserver.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) {
        logger.info("Registration of User {}" + user);
        userServiceImpl.addUser(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{username}/logout")
    public void logout(@PathVariable("username") String username) {
        logger.info("User " + username + " clicked on logout method");
        userServiceImpl.logout(username);
    }

}
