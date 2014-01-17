package com.example.mitekmobileguide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import nz.co.mitek.search.SimplePDFSearch;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.ExpandableListView.OnChildClickListener;

public class MainActivity extends Activity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener {

	private SearchView search;
	private MyListAdapter listAdapter;
	private ExpandableListView myList;
	private ArrayList<Section> sectionList = new ArrayList<Section>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		int width = display.getWidth();
		int height = display.getHeight();
		System.out.println(width + " " + height);
//		SimplePDFSearch pdfSearch = new SimplePDFSearch();
//	    pdfSearch.checkPDF(new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+"6 & 12kN Stud to Bottom Plate Fixng 09_2011.pdf"),"Ideal");
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		search = (SearchView) findViewById(R.id.search);
		search.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		search.setIconifiedByDefault(false);
		search.setOnQueryTextListener(this);
		search.setOnCloseListener(this);
		myList = (ExpandableListView) findViewById(R.id.expandableList);
		myList.setOnChildClickListener(new OnChildClickListener() {
		
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            	Item item = listAdapter.getGroup(groupPosition).getItemList().get(childPosition);
            	
                

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ item.getPath());
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(file),"application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(target, "Open " + item.getName() + " with..." );
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                } 
            	
                return false;
            }
        });
		// display the list
		displayList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	

	// method to expand all groups
	private void expandAll() {
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			myList.expandGroup(i);
		}
	}

	// method to expand all groups
	private void displayList() {

		// display the list
		loadSomeData();

		// get reference to the ExpandableListView
		myList = (ExpandableListView) findViewById(R.id.expandableList);
		// create the adapter by passing your ArrayList data
		listAdapter = new MyListAdapter(MainActivity.this, sectionList);
		// attach the adapter to the list
		myList.setAdapter(listAdapter);
	}

	private void loadSomeData() {
		
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/Index File.txt");

		try {
			Scanner scanner = new Scanner(new FileReader(file));
			while (scanner.hasNextLine())  
			{  
				String line = scanner.nextLine();
			   String[] array = line.split(" ~~ ");
			   for (int i = 0; i < array.length; i++) {
				   //removes all unknown characters 
				   array[i] = array[i].replaceAll("\uFFFD", "");
			   }
			   if(array.length == 3){
				   String selectedSection = array[0];
				   String selectedItem = array[1];
				   String correspondingFile = array[2];
				   boolean listContainsSection = false;
				   Section currentSection = null;
				   for (Section section : sectionList) {
					   if(section.getName().equals(selectedSection)){
						   listContainsSection = true;
						   currentSection = section;
					   }
				   }
				   if(!listContainsSection){
					   ArrayList<Item> countryList = new ArrayList<Item>();
					   Item item = new Item(selectedItem, correspondingFile);
					   countryList.add(item);
					   Section section = new Section(selectedSection, countryList);
					   sectionList.add(section);
				   }
				   else{
					   ArrayList<Item> countryList = currentSection.getItemList();
					   Item item = new Item(selectedItem, correspondingFile);
					   countryList.add(item);
					   Section section = new Section(selectedSection, countryList);
					   sectionList.set(sectionList.indexOf(currentSection), section);
				   }
 
			   }
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}

	@Override
	public boolean onClose() {
		listAdapter.filterData("", false);
		expandAll();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String query) {
		listAdapter.filterData(query, false);
		expandAll();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		listAdapter.filterData(query,false);
		expandAll();
		return false;
	}
}