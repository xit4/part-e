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

		TextView From, To, Start, End;
		int year, month, day, hour, minutes;

		From = (TextView) findViewById(R.id.from_et);
		To = (TextView) findViewById(R.id.to_et);

		Start = (TextView) findViewById(R.id.fromH_et);
		End = (TextView) findViewById(R.id.toH_et);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH);

		minutes = c.get(Calendar.MINUTE);
		hour = c.get(Calendar.HOUR_OF_DAY);

		From.setText(new StringBuilder().append(day).append("/").append(month)
				.append("/").append(year).append(" "));

		To.setText(new StringBuilder().append(day + 1).append("/")
				.append(month).append("/").append(year).append(" "));

		Start.setText(new StringBuilder().append(hour).append(":")
				.append(minutes));

		End.setText(new StringBuilder().append((hour + 6)%24).append(":")
				.append(minutes));

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
