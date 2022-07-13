package fon.master.nst.oauthserver.service;

import fon.master.nst.oauthserver.model.User;

public interface UserService {

    void addUser(User user);

    void logout(String username);

}
