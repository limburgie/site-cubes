package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.repository.UserRepository;
import be.webfactor.sitecubes.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Inject private UserRepository userRepository;

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}

	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
	}

}
