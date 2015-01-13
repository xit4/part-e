package com.example.parte;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.qdn.NetworkingEventHandler;
import com.example.qdn.NetworkingManager;

public class PartyManager extends ActionBarActivity {

	public static final String MyPREFERENCES = "UserPartyPref";
	public static final String StartingDate = "StartingDate";
	public static final String EndingDate = "EndingDate";
	public static final String PartyName = "PartyName";
	public static final String User = "username";

	static final int REQUEST_QR_ENCODE = 1;
	static final int REQUEST_IMAGE_CAPTURE = 2;

	protected void onCreate(Bundle savedInstanceState) {

		final String[] PARTY_TASK = new String[] { "Invite", "Gallery",
				"Who Am I?", "Jungle Speed", "Slap Vito", "Jump Matteo" };
		final SharedPreferences sp = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.party_manager);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}

		getSupportActionBar().setTitle(sp.getString(PartyName, "default"));
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"Part-E" + File.separator + sp.getString(User, "defaultUser")
						+ File.separator
						+ sp.getString(PartyName, "defaultParty"));

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs())
				Toast.makeText(getBaseContext(), "failed to create directory",
						Toast.LENGTH_LONG).show();

		}
		ArrayList<String> localList = new ArrayList<String>();
		File[] file = mediaStorageDir.listFiles();

		for (int i = 0; i < file.length; i++) {
			localList.add(file[i].getName());
		}
		String result = "";
		for (String arg : localList) {
			result += arg + ",";
		}
		Editor e = sp.edit();
		e.putString(MainActivity.ListName, result);
		e.commit();

		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this, PARTY_TASK));
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {

				case 2:
					Intent iW = new Intent(getBaseContext(), WhoAmI.class);
					startActivity(iW);

					break;

				case 1:

					Intent iG = new Intent(getBaseContext(), ShowGallery.class);
					startActivity(iG);

					break;
				case 0:

					Intent qrDroid = new Intent(la.droid.qr.Services.ENCODE);
					qrDroid.putExtra(la.droid.qr.Services.CODE,
					// sp.getString(User, "")+
							sp.getString(PartyName, ""));
					// + sp.getLong(StartingDate, 0)
					// + sp.getLong(EndingDate, 0));

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
				}
			}
		});

	}
	
	protected void onResume(){
		super.onResume();
		
		final SharedPreferences sp = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		NetworkingManager NM = new NetworkingManager(
				new MyNetworkingEventHandler() {
					@Override
					public void valueChangedForKeyOfUser(JSONObject json,
							String key, String user) {
						String list = "";
						list = sp.getString(MainActivity.ListName, "");

						ArrayList<String> localPicturesList = new ArrayList<String>(
								Arrays.asList(list.split(",")));
						try {
							list = json.getJSONArray("records").getJSONObject(0).getString("value");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						ArrayList<String> picturesList = new ArrayList<String>(
								Arrays.asList(list.split(",")));

						NetworkingManager NM = new NetworkingManager(
								new MyNetworkingEventHandler() {

									@Override
									public void loadedValueForKeyOfUser(
											JSONObject json, String key,
											String user) {
										String imgString = "";
										try {
											imgString = json.getString("value");
										} catch (JSONException e) {
											e.printStackTrace();
										}
										File media = new File(
												Environment
														.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
														+ File.separator
														+ "Part-E"
														+ File.separator
														+ sp.getString(User,
																"defaultUser")
														+ File.separator
														+ sp.getString(
																PartyName,
																"defaultParty")
														+ File.separator + key);
										Bitmap bm = StringToBitMap(imgString);

										FileOutputStream out = null;
										try {
											out = new FileOutputStream(media);
											bm.compress(
													Bitmap.CompressFormat.JPEG,
													100, out);
										} catch (Exception e) {
											e.printStackTrace();
										} finally {
											try {
												if (out != null) {
													out.close();
												}
											} catch (IOException e) {
												e.printStackTrace();
											}
										}

									}
								}, MainActivity.GroupID, sp.getString(
										MainActivity.User, ""));
						for (String ciao : picturesList) {
							if (!ciao.equals("") && !localPicturesList.contains(ciao)) {
								NM.loadValueForKeyOfUser(ciao,
										MainActivity.Pictures);
								localPicturesList.add(ciao);
							}
						}
						
						String result = "";
						for (String arg : localPicturesList) {
							result += arg + ",";
						}
						Editor e = sp.edit();
						e.putString(MainActivity.ListName, result);
						e.commit();
					}
				}, MainActivity.GroupID, sp.getString(MainActivity.User, ""));
		NM.monitorKeyOfUser(sp.getString(MainActivity.PartyName, "PartyName"),
				MainActivity.ListName);
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

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(new Date());
			File media = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
							+ File.separator
							+ "Part-E"
							+ File.separator
							+ sp.getString(User, "defaultUser")
							+ File.separator
							+ sp.getString(PartyName, "defaultParty")
							+ File.separator + timeStamp + ".jpg");

			Uri uriSavedImage = Uri.fromFile(media);

			// Toast.makeText(getBaseContext(),
			// "Image 1 saved to:\n" + uriSavedImage.getPath(),
			// Toast.LENGTH_LONG).show();

			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);

			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
			Editor e = sp.edit();
			e.putString("imgName",timeStamp + ".jpg");
			e.putString("imgUri", uriSavedImage.getPath());
			e.commit();
			if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
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
				final SharedPreferences sp = getSharedPreferences(
						MyPREFERENCES, Context.MODE_PRIVATE);
				NetworkingManager NM = new NetworkingManager(
						new MyNetworkingEventHandler() {
							@Override
							public void loadedValueForKeyOfUser(
									JSONObject json, String key, String user) {

								String list = "";
								try {
									list = json.getString("value");
								} catch (JSONException e) {
									e.printStackTrace();
								}
								ArrayList<String> picturesList = new ArrayList<String>(
										Arrays.asList(list.split(",")));
								String imgPath = sp.getString("imgUri", "");
								String imgName = sp.getString("imgName", "");
								
								picturesList.add(imgName);
								Bitmap b = BitmapFactory.decodeFile(imgPath);
								String s = BitMapToString(b);
								NetworkingManager NM = new NetworkingManager(
										new MyNetworkingEventHandler(),
										MainActivity.GroupID, sp.getString(
												MainActivity.User, "username"));

								String result = "";
								for (String arg : picturesList) {
									result += arg + ",";
								}
								NM.saveValueForKeyOfUser(sp.getString(
										MainActivity.PartyName, "PartyName"),
										MainActivity.ListName, result);
								NM.unlockKeyOfUser(sp.getString(
										MainActivity.PartyName, "PartyName"),
										MainActivity.ListName);
								NM.saveValueForKeyOfUser(imgName,
										MainActivity.Pictures, s);
							}
						}, MainActivity.GroupID, sp.getString(
								MainActivity.User, "username"));
				NM.lockKeyOfUser(
						sp.getString(MainActivity.PartyName, "PartyName"),
						MainActivity.ListName);
				NM.loadValueForKeyOfUser(
						sp.getString(MainActivity.PartyName, "PartyName"),
						MainActivity.ListName);

				String list = "";
				list = sp.getString(MainActivity.ListName, "");

				ArrayList<String> localPicturesList = new ArrayList<String>(
						Arrays.asList(list.split(",")));
				
				String imgName = sp.getString("imgName", "");
				localPicturesList.add(imgName);
				String result = "";
				for (String arg : localPicturesList) {
					result += arg + ",";
				}
				Editor e = sp.edit();
				e.putString(MainActivity.ListName,result);
				e.commit();
				break;
			}
		}
	}

	public Bitmap StringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public String BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}
}
