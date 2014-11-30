package be.webfactor.sitecubes.security;

import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;

@Named
public class UserAuthenticationService implements UserDetailsService {

	@Inject private UserService userService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getByUsername(username);
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), true, true, true, true, Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
	}


}
