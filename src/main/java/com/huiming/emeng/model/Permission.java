package com.huiming.emeng.model;

public class Permission {
	private Integer id;

	private String mapping;

	private String description;
	
	private Byte state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object o) {
		return description.equals(((Permission) o).getDescription()) && mapping.equals(((Permission) o).getMapping());
	}

	public Permission(Integer id, Byte state) {
		super();
		this.id = id;
		this.state = state;
	}

	public Permission() {
		super();
	}

	
}
