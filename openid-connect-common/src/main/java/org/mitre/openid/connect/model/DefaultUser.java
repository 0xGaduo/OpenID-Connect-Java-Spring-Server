package org.mitre.openid.connect.model;


import com.google.common.collect.Sets;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DefaultUser implements UserDetails {

	private String username;
	private String password;
	private Collection<GrantedAuthority> authorities;
	private Boolean enabled;

	public DefaultUser() {
		this.authorities = Sets.newConcurrentHashSet();
		this.enabled = true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthorities(GrantedAuthority authority) {
		if (this.authorities == null) {
			this.authorities = Sets.newConcurrentHashSet();
		}
		this.authorities.add(authority);
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
