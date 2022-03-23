package fon.master.nst.orderservice.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentLoggedInUserService {

    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
