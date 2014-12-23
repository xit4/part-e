package com.example.parte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class PartyManager extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {

		final String[] PARTY_TASK = new String[] { 
			"Who Am I?", "Gallery","Invite", "Take Picture" };
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.party_manager);
		
	    GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this, PARTY_TASK));
	    
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
