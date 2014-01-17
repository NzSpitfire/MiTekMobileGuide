package com.example.mitekmobileguide;

import java.util.ArrayList;

public class Section {

	private String name;
	private ArrayList<Item> itemList = new ArrayList<Item>();

	public Section(String name, ArrayList<Item> itemList) {
		super();
		this.name = name;
		this.itemList = itemList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	

}