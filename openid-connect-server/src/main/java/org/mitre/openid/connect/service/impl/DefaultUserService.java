package org.mitre.openid.connect.service.impl;

import org.mitre.openid.connect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

	@Autowired
	private UserDetailsManager userDetailsManager;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return userDetailsManager.loadUserByUsername(username);
	}

	@Override
	public void save(UserDetails user) {
		if(userDetailsManager.userExists(user.getUsername())) {
			userDetailsManager.createUser(user);
		} else {
			userDetailsManager.updateUser(user);
		}
	}

	@Override
	public void deleteUser(String username) {
		if(userDetailsManager.userExists(username)) {
			userDetailsManager.deleteUser(username);
		}
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		userDetailsManager.changePassword(oldPassword, newPassword);
	}
}
