package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.repository.UserRepository;
import be.webfactor.sitecubes.service.UserService;
import be.webfactor.sitecubes.service.exception.*;
import org.apache.commons.lang3.StringUtils;
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
		validate(user);
		return userRepository.save(user);
	}

	public User getByUsername(String username) {
		if (username == null) {
			return null;
		}
		return userRepository.findByUsername(username);
	}

	public User getUser(long id) {
		return userRepository.findOne(id);
	}

	private void validate(User user) {
		checkForInvalidUsername(user);
		checkForDuplicateUsername(user);
		checkForInvalidPassword(user);
		checkForInvalidFirstName(user);
		checkForInvalidLastName(user);
	}

	private void checkForDuplicateUsername(User user) {
		User usernameUser = getByUsername(user.getUsername());
		if (usernameUser != null && !usernameUser.equals(user)) {
			throw new DuplicateUsernameException();
		}
	}

	private void checkForInvalidLastName(User user) {
		if (StringUtils.isBlank(user.getLastName())) {
			throw new InvalidUserLastNameException();
		}
	}

	private void checkForInvalidFirstName(User user) {
		if (StringUtils.isBlank(user.getFirstName())) {
			throw new InvalidUserFirstNameException();
		}
	}

	private void checkForInvalidPassword(User user) {
		if (StringUtils.isBlank(user.getPassword())) {
			throw new InvalidPasswordException();
		}
	}

	private void checkForInvalidUsername(User user) {
		if (StringUtils.isBlank(user.getUsername())) {
			throw new InvalidUsernameException();
		}
	}

	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
	}

}
