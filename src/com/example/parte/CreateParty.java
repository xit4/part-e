package com.example.parte;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class CreateParty extends Activity {
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_party);

		final TextView From;
		final TextView To;
		final TextView Start;
		final TextView End;
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

		Start.setText(new StringBuilder().append(hour).append(":")
				.append(minutes));

		if ((hour + 6) > 24)
			day++;

		To.setText(new StringBuilder().append(day).append("/").append(month)
				.append("/").append(year).append(" "));

		End.setText(new StringBuilder().append((hour + 6) % 24).append(":")
				.append(minutes));

		ImageButton fromButton = (ImageButton) findViewById(R.id.button_from);
		fromButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				setDateFromDialog(CreateParty.this, "Start of Party", From,
						Start);

			}
		});

		ImageButton toButton = (ImageButton) findViewById(R.id.button_to);
		toButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setDateFromDialog(CreateParty.this, "End of Party", To, End);

			}
		});

	}

	public void setDateFromDialog(Context context, String title,
			final TextView toFillDate, final TextView toFillHour) {

		final View dialogView = View.inflate(context, R.layout.custom_dialog,
				null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Starting Party");
		builder.setView(dialogView);

		final TimePicker tp = (TimePicker) dialogView
				.findViewById(R.id.timePicker1);
		final DatePicker dp = (DatePicker) dialogView
				.findViewById(R.id.datePicker1);

		builder.setNeutralButton("Confirm",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						toFillDate.setText(new StringBuilder()
								.append(dp.getDayOfMonth()).append("/")
								.append(dp.getMonth()).append("/")
								.append(dp.getYear()));
						toFillHour.setText(new StringBuilder()
								.append(tp.getCurrentHour()).append(":")
								.append(tp.getCurrentMinute()));
						dialog.dismiss();
					}
				});

		AlertDialog d = builder.create();
		d.show();
	}

	public void partyManager(View view) {

		Intent intent = new Intent(this, PartyManager.class);		//need to store details about the party, maybe using shared preferences?
		startActivity(intent);
		finish();

	}
}
