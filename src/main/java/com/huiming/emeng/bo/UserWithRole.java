package com.huiming.emeng.bo;

import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.School;
import com.huiming.emeng.model.User;

public class UserWithRole {
	private User user;
	private Role role;
	private School school;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

}
