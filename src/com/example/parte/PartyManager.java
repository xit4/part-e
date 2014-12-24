package com.example.parte;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PartyManager extends Activity {

	static final int REQUEST_QR_ENCODE = 1;
	static final int REQUEST_IMAGE_CAPTURE = 2;

	protected void onCreate(Bundle savedInstanceState) {

		final String[] PARTY_TASK = new String[] { "Who Am I?", "Gallery",
				"Invite", "Take Picture" };

		super.onCreate(savedInstanceState);
		setContentView(R.layout.party_manager);

		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this, PARTY_TASK));

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

					Intent iG = new Intent(getBaseContext(), Gallery.class);
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
					try {
						startActivityForResult(qrDroid, REQUEST_QR_ENCODE);		//TODO?
					} catch (ActivityNotFoundException activity) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri
								.parse("https://play.google.com/store/apps/details?id=la.droid.qr&hl=en"));
						startActivity(intent);

					}

					break;
				case 3:
					Intent takePictureIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
						startActivityForResult(takePictureIntent,
								REQUEST_IMAGE_CAPTURE);
					}

					break;

				}

			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case REQUEST_QR_ENCODE:
				break;

			case REQUEST_IMAGE_CAPTURE:
				break;
			}
		}
	}

}
