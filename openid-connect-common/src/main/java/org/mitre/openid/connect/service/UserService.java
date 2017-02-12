package org.mitre.openid.connect.service;


import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	UserDetails loadUserByUsername(String username);

	void save(UserDetails user);

	void deleteUser(String username);

	void changePassword(String oldPassword, String newPassword);
}
