package com.example.parte;

import la.droid.qr.*;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean authentication = false; // authentication stuff

		if (!authentication) {
			Intent intent = new Intent(this, LogInActivity.class);
			startActivityForResult(intent, 1);
		} else {
			String User = new String("BANANA");
			setContentView(R.layout.activity_main);
			TextView top = (TextView) findViewById(R.id.top);
			top.setText("WELCOME " + User);

			final Button create = (Button) findViewById(R.id.button_create);
			create.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// create_party(); TO-DO
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

		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {

				String User = data.getStringExtra("USER");
				Toast.makeText(this, "Hello " + User + "!", Toast.LENGTH_SHORT)
						.show();
				setContentView(R.layout.activity_main);
				TextView top = (TextView) findViewById(R.id.top);
				top.setText("WELCOME " + User);

				final Button create = (Button) findViewById(R.id.button_create);
				create.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						// create_party(); TO-DO

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
		case 2:
			if (resultCode == RESULT_OK) {
				String result = data.getExtras().getString(la.droid.qr.Services.RESULT);
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
		Intent qrDroid = new Intent(la.droid.qr.Services.SCAN);
		try {
			startActivityForResult(qrDroid, 2);
		} catch (ActivityNotFoundException activity) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri
					.parse("https://play.google.com/store/apps/details?id=la.droid.qr&hl=en"));
			startActivity(intent);

		}
	}

	protected void createParty() {

	}

}
