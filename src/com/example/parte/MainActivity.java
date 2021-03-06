package com.example.parte;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String MyPREFERENCES = "UserPartyPref";

	static final int REQUEST_QR_SCAN = 2;
	static final int REQUEST_LOGIN = 1;
	public static final String User = "username";
	public static final String PartyName = "PartyName";
	public static final String LoginError = "Please login first";
	public static final String ListName = "picturesList";
	public static final String GroupID = "G13";
	public static final String Pictures = "Pictures";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		//application core
		final SharedPreferences sp = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		if (sp.getString(User, LoginError).equals(LoginError)) {
			Intent intent = new Intent(this, LogInActivity.class);
			startActivityForResult(intent, REQUEST_LOGIN);
		} else {

			setContentView(R.layout.activity_main);
			final ListView listview = (ListView) findViewById(R.id.list_party);

			String DirectoryPath = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
					+ File.separator
					+ "Part-E"
					+ File.separator
					+ sp.getString(MainActivity.User, "defaultUser")
					+ File.separator;
			File f = new File(DirectoryPath);
			File[] file = f.listFiles();

			final ArrayList<String> titles = new ArrayList<String>();
			//retrieving all parties to be displayed in the listview under the buttons

			if (file != null) {

				for (int i = 0; i < file.length; i++) {

					if (file[i].isDirectory()) {

						titles.add(file[i].getName());

					}

				}
			}

			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, titles);
			listview.setAdapter(adapter);

			listview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Editor e = sp.edit();
					e.putString(PartyName, titles.get(position));
					e.commit();

					
					//differentiation between expired parties and still running parties not really implemented
					//we don't store the different data for the different party, we just check (wrongly) the last party created
					Calendar c = Calendar.getInstance();

					long currentTime = c.getTimeInMillis();
					long endTime = sp.getLong(CreateParty.EndingDate, 0);
					Intent i;
					if (currentTime > endTime) {
						i = new Intent(getBaseContext(),
								ShowGallery.class);
					} else {
						i = new Intent(getBaseContext(), PartyManager.class);
					}
					startActivity(i);
				}
			});

			TextView top = (TextView) findViewById(R.id.top);
			top.setText("Welcome " + sp.getString(User, LoginError));

			final Button create = (Button) findViewById(R.id.button_create);
			create.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					createParty();
				}
			});

			final Button join = (Button) findViewById(R.id.button_join);
			join.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					joinParty();
				}
			});

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		final SharedPreferences sp = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		switch (requestCode) {
		case REQUEST_LOGIN:
			
			//if it's the first time the user runs the app, the log activity will be called
			if (resultCode == RESULT_OK) {

				Toast.makeText(this,
						"Hello " + sp.getString(User, LoginError) + "!",
						Toast.LENGTH_SHORT).show();
				setContentView(R.layout.activity_main);
				TextView top = (TextView) findViewById(R.id.top);
				top.setText("Welcome " + sp.getString(User, LoginError));

				final Button create = (Button) findViewById(R.id.button_create);
				create.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						createParty();

					}
				});

				final Button join = (Button) findViewById(R.id.button_join);
				join.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						joinParty();

					}
				});
			}
			break;
		case REQUEST_QR_SCAN:

			if (resultCode == RESULT_OK) {

				//handling scanning result
				String result = data.getExtras().getString(
						la.droid.qr.Services.RESULT);

				Editor e = sp.edit();
				e.putString(PartyName, result);
				e.commit();
				Intent intent = new Intent(this, PartyManager.class);
				startActivity(intent);
			} else if (resultCode == RESULT_CANCELED) {
			}
			break;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void joinParty() {
		
		//if the user doesn't have a qr scanner, he/she will be asked to install the right one
		Intent qrDroid = new Intent(la.droid.qr.Services.SCAN);
		try {
			startActivityForResult(qrDroid, REQUEST_QR_SCAN);
		} catch (ActivityNotFoundException activity) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri
					.parse("https://play.google.com/store/apps/details?id=la.droid.qr&hl=en"));
			startActivity(intent);

		}
	}

	protected void createParty() {
		Intent createParty = new Intent(this, CreateParty.class);
		startActivity(createParty);
	}

}
