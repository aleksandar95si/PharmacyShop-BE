package fon.master.nst.shoppingcart.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentLoggedInUserService {

public String getCurrentUser() {
		
		//String currentUser=  SecurityContextHolder.getContext().getAuthentication().getName();
		 return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
}
