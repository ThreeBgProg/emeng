package com.huiming.emeng.bo;

import com.huiming.emeng.model.Location;
import com.huiming.emeng.model.School;

public class SchoolWithLocation {

	private School school;
	private Location location;
	
	
	
	

	public SchoolWithLocation() {
		super();
	}

	public SchoolWithLocation(School school) {
		super();
		this.school = school;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
