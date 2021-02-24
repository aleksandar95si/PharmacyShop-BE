package fon.master.nst.oauthserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fon.master.nst.oauthserver.model.AuthUserDetail;
import fon.master.nst.oauthserver.model.User;
import fon.master.nst.oauthserver.repository.UserDetailRepository;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser=userDetailRepository.findByUsername(username);
		
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Wrond username or password"));
		
		UserDetails userDetails=new AuthUserDetail(optionalUser.get());
		
		new AccountStatusUserDetailsChecker().check(userDetails);
		
		return userDetails;
	}

}
