package com.example.parte;

import android.app.Activity;
import android.content.Intent;
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

			final Button join = (Button) findViewById(R.id.button_create);
			create.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// join_party(); TO-DO
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

				final Button join = (Button) findViewById(R.id.button_create);
				create.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						// join_party(); TO-DO

					}
				});
			}
			break;
		case 2:
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

}
