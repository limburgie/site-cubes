package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.User;

import java.util.List;

public interface UserService {

	List<User> getUsers();

	User save(User user);

	void delete(User user);

	User getByUsername(String username);

	User getUser(long id);

}
