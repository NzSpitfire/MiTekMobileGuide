package com.example.mitekmobileguide;

public class Item {

	private String path = "";
	private String name = "";

	public Item(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}



}