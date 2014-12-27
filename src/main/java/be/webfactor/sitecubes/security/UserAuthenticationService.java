package be.webfactor.sitecubes.security;

import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.service.UserAdminService;
import be.webfactor.sitecubes.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
public class UserAuthenticationService implements UserDetailsService {

	@Inject private UserService userService;
	@Inject private UserAdminService userAdminService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getByUsername(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (userAdminService.isUserAdmin(user, null)) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
	}

	public User getAuthenticatedUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			return userService.getByUsername(username);
		}
		return null;
	}

}
