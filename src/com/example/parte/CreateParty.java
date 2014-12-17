package com.example.parte;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CreateParty extends Activity {
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_party);

		TextView From, To;
		int year, month, day;

		From = (TextView) findViewById(R.id.from_et);
		To = (TextView) findViewById(R.id.to_et);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH);

		From.setText(new StringBuilder().append(day).append("/").append(month)
				.append("/").append(year).append(" "));

		To.setText(new StringBuilder().append(day + 1).append("/")
				.append(month).append("/").append(year).append(" "));

		ImageButton fromButton = (ImageButton) findViewById(R.id.button_from);
		fromButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		ImageButton toButton = (ImageButton) findViewById(R.id.button_to);
		toButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		Button create = (Button) findViewById(R.id.button_createparty);
		create.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

	}
}
