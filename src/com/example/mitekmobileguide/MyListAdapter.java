package com.example.mitekmobileguide;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import nz.co.mitek.search.SimplePDFSearch;



import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<Section> sectionList;
	private ArrayList<Section> originalList;

	public MyListAdapter(Context context, ArrayList<Section> sectionList) {
		this.context = context;
		this.sectionList = new ArrayList<Section>();
		this.sectionList.addAll(sectionList);
		this.originalList = new ArrayList<Section>();
		this.originalList.addAll(sectionList);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<Item> countryList = sectionList.get(groupPosition)
				.getItemList();
		return countryList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {

		Item item = (Item) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.child_row, null);
		}

		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(item.getName().trim());

		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		ArrayList<Item> countryList = sectionList.get(groupPosition)
				.getItemList();
		return countryList.size();

	}

	@Override
	public Section getGroup(int groupPosition) {
		return sectionList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return sectionList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {

		Section section = (Section) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.group_row, null);
		}

		TextView heading = (TextView) view.findViewById(R.id.heading);
		heading.setText(section.getName().trim());

		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void filterData(String query, boolean searchPDF) {

		query = query.toLowerCase();
		Log.v("MyListAdapter", String.valueOf(sectionList.size()));
		sectionList.clear();

		if (query.isEmpty()) {
			sectionList.addAll(originalList);
		} else {

			for (Section section : originalList) {

				ArrayList<Item> itemList = section.getItemList();
				ArrayList<Item> newList = new ArrayList<Item>();
				for (Item item : itemList) {
					if(!searchPDF){
						if (item.getName().toLowerCase().contains(query)) {
							newList.add(item);
						}
					}
					else{
						SimplePDFSearch pdfSearch = new SimplePDFSearch();
						if(pdfSearch.checkPDF(new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+item.getPath()), query)){
							newList.add(item);
						}
					}
				}
				if (newList.size() > 0) {
					Section nContinent = new Section(section.getName(),
							newList);
					sectionList.add(nContinent);
				}
			}
		}

		Log.v("MyListAdapter", String.valueOf(sectionList.size()));
		notifyDataSetChanged();

	}

}