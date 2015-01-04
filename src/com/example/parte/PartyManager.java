package com.example.parte;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class PartyManager extends ActionBarActivity {

	public static final String MyPREFERENCES = "UserPartyPref";
	public static final String StartingDate = "StartingDate";
	public static final String EndingDate = "EndingDate";
	public static final String PartyName = "PartyName";
	public static final String User = "username";

	static final int REQUEST_QR_ENCODE = 1;
	static final int REQUEST_IMAGE_CAPTURE = 2;

	protected void onCreate(Bundle savedInstanceState) {

		final String[] PARTY_TASK = new String[] { "Who Am I?", "Gallery",
				"Invite" };
		final SharedPreferences sp = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.party_manager);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
		
		getSupportActionBar().setTitle(sp.getString(PartyName, "default"));	
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this, PARTY_TASK));

		// Toast.makeText(
		// this,
		// "Party starts : "
		// + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(sp
		// .getLong(StartingDate, -1)), Toast.LENGTH_SHORT)
		// .show();
		// Toast.makeText(
		// this,
		// "Party ends : "
		// + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(sp
		// .getLong(EndingDate, -1)), Toast.LENGTH_SHORT)
		// .show();

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(),
				// ((TextView)
				// view.findViewById(R.id.grid_item_label)).getText() + " " +
				// Integer.toString(position), Toast.LENGTH_SHORT).show();

				switch (position) {

				case 0:
					Intent iW = new Intent(getBaseContext(), WhoAmI.class);
					startActivity(iW);

					break;

				case 1:

					Intent iG = new Intent(getBaseContext(), ShowGallery.class);
					startActivity(iG);

					break;
				case 2:

					Intent qrDroid = new Intent(la.droid.qr.Services.ENCODE); // need
																				// to
																				// pass
																				// correct
																				// parameters
																				// to
																				// encode
																				// the
																				// "party network stuff"

					qrDroid.putExtra(
							la.droid.qr.Services.CODE,
							sp.getString(User, "")
									+ sp.getString(PartyName, "")
									+ sp.getLong(StartingDate, 0)
									+ sp.getLong(EndingDate, 0));

					qrDroid.putExtra(la.droid.qr.Services.IMAGE, true);

					qrDroid.putExtra(la.droid.qr.Services.SIZE, 0);

					try {
						startActivityForResult(qrDroid, REQUEST_QR_ENCODE);
					} catch (ActivityNotFoundException activity) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri
								.parse("https://play.google.com/store/apps/details?id=la.droid.qr&hl=en"));
						startActivity(intent);

					}

					break;
				/*
				 * case 3:
				 * 
				 * File mediaStorageDir = new File( Environment
				 * .getExternalStoragePublicDirectory
				 * (Environment.DIRECTORY_PICTURES), "Part-E" + File.separator +
				 * sp.getString(User, "defaultUser") + File.separator +
				 * sp.getString(PartyName, "defaultParty"));
				 * 
				 * if (!mediaStorageDir.exists()) { if
				 * (!mediaStorageDir.mkdirs()) Toast.makeText(getBaseContext(),
				 * "failed to create directory", Toast.LENGTH_LONG).show();
				 * 
				 * }
				 * 
				 * String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				 * .format(new Date()); File media = new
				 * File(mediaStorageDir.getPath() + File.separator + "IMG_" +
				 * timeStamp + ".jpg");
				 * 
				 * Uri uriSavedImage = Uri.fromFile(media);
				 * 
				 * Toast.makeText(getBaseContext(), "Image 1 saved to:\n" +
				 * uriSavedImage.getPath(), Toast.LENGTH_LONG).show();
				 * 
				 * Intent takePictureIntent = new Intent(
				 * MediaStore.ACTION_IMAGE_CAPTURE);
				 * 
				 * takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				 * uriSavedImage); if
				 * (takePictureIntent.resolveActivity(getPackageManager()) !=
				 * null) { startActivityForResult(takePictureIntent,
				 * REQUEST_IMAGE_CAPTURE); }
				 * 
				 * break;
				 */

				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.party_manager_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		final SharedPreferences sp = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		switch (item.getItemId()) {
		case R.id.action_camera:
			File mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"Part-E" + File.separator
							+ sp.getString(User, "defaultUser")
							+ File.separator
							+ sp.getString(PartyName, "defaultParty"));

			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs())
					Toast.makeText(getBaseContext(),
							"failed to create directory", Toast.LENGTH_LONG)
							.show();

			}

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(new Date());
			File media = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");

			Uri uriSavedImage = Uri.fromFile(media);

			// Toast.makeText(getBaseContext(),
			// "Image 1 saved to:\n" + uriSavedImage.getPath(),
			// Toast.LENGTH_LONG).show();

			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);

			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
			if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case REQUEST_QR_ENCODE:
				if ((data != null) && (data.getExtras() != null)) {

					Intent intent = new Intent(this, ShowQR.class);
					intent.putExtras(data);
					startActivity(intent);

				}

				break;

			case REQUEST_IMAGE_CAPTURE:
//				if ((data != null) && (data.getExtras() != null)) {
//					Toast.makeText(
//							this,
//							"Image saved to:\n"
//									+ data.getStringExtra(MediaStore.EXTRA_OUTPUT),
//							Toast.LENGTH_LONG).show();
//				} else {
//					Toast.makeText(this, "Intent null", Toast.LENGTH_LONG)
//							.show();
//
//				}
				break;
			}
		}
	}
}
