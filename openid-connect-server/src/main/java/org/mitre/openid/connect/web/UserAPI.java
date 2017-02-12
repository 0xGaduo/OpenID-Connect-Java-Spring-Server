package org.mitre.openid.connect.web;

import com.google.gson.Gson;
import java.security.Principal;
import org.mitre.openid.connect.model.DefaultUser;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.service.UserInfoService;
import org.mitre.openid.connect.service.UserService;
import org.mitre.openid.connect.view.JsonEntityView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/" + UserAPI.URL)
public class UserAPI {
	public static final String URL = RootController.API_URL + "/user";
	private static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
	private static final Logger logger = LoggerFactory.getLogger(UserInfoAPI.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserInfoService userInfoService;

	private Gson gson = new Gson();

	@RequestMapping(value = {""}, method = RequestMethod.POST)
	public String save(@RequestBody String json, ModelMap m, Principal p) {
		try {
			DefaultUser user = gson.fromJson(json, DefaultUser.class);
			if(user.getUsername() == null || user.getPassword() == null) {
				m.put(JsonEntityView.ENTITY, "username is null or password is null.");
				return JsonEntityView.VIEWNAME;
			}

			user.setUsername(user.getUsername());
			user.setEnabled(true);
			user.addAuthorities(ROLE_USER);
			userService.save(user);
			m.put(JsonEntityView.ENTITY, user);

			UserInfo userInfo = new DefaultUserInfo();
			userInfo.setPreferredUsername(user.getUsername());
			userInfo.setName(user.getUsername());
			userInfoService.save(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			m.put(JsonEntityView.ENTITY, e);
		}
		return JsonEntityView.VIEWNAME;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = {""}, method = RequestMethod.DELETE)
	public String deleteUser(String username) {
		userService.deleteUser(username);

		return JsonEntityView.VIEWNAME;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = {"/changePassword"}, method = RequestMethod.POST)
	public String changePassword(String oldPassword, String newPassword) {
		userService.changePassword(oldPassword, newPassword);

		return JsonEntityView.VIEWNAME;
	}
}
