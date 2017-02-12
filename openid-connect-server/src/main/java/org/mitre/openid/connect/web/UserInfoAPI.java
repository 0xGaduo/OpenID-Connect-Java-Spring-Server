package org.mitre.openid.connect.web;

import com.google.gson.Gson;
import java.security.Principal;
import org.mitre.oauth2.service.SystemScopeService;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.service.UserInfoService;
import org.mitre.openid.connect.view.JsonEntityView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/" + UserInfoAPI.URL)
public class UserInfoAPI {

	public static final String URL = RootController.API_URL + "/userinfo";

	@Autowired
	private UserInfoService userInfoService;

	private static final Logger logger = LoggerFactory.getLogger(UserInfoAPI.class);

	private Gson gson = new Gson();

	@PreAuthorize("hasRole('ROLE_USER') and #oauth2.hasScope('" + SystemScopeService.OPENID_SCOPE + "')")
	@RequestMapping(value = {""}, method = RequestMethod.POST)
	public String save(@RequestBody String json, ModelMap m, Principal p) {
		try {
			UserInfo _userInfo = userInfoService.getByUsername(p.getName());

			if (_userInfo == null) {
				return JsonEntityView.VIEWNAME;
			}

			DefaultUserInfo userinfo = gson.fromJson(json, DefaultUserInfo.class);
			UserInfo newUserinfo = userInfoService.save(userinfo);
			m.put(JsonEntityView.ENTITY, newUserinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonEntityView.VIEWNAME;
	}
}
