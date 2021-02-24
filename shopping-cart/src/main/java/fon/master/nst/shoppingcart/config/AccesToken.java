package fon.master.nst.shoppingcart.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class AccesToken {

	public static String getAccesToken() {
		
		OAuth2AuthenticationDetails authenticationDetails=(OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return authenticationDetails.getTokenType().concat(" ").concat(authenticationDetails.getTokenValue());
	} 
	
}
