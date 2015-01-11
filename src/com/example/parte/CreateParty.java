package com.example.parte;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parte.ListenerEditText.KeyImeChange;

public class CreateParty extends Activity {

	public static final String MyPREFERENCES = "UserPartyPref";
	public static final String StartingDate = "StartingDate";
	public static final String EndingDate = "EndingDate";
	public static final String PartyName = "PartyName";

	@Override
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
				.append("/").append(year));

		Start.setText(new StringBuilder().append(hour).append(":")
				.append(minutes));

		if ((hour + 6) > 24)
			day++;

		To.setText(new StringBuilder().append(day).append("/").append(month)
				.append("/").append(year));

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

		ListenerEditText tv = (ListenerEditText) findViewById(R.id.creation_et);
		tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout l = (LinearLayout) findViewById(R.id.spacer);
				l.setVisibility(LinearLayout.GONE);

			}
		});

		tv.setKeyImeChangeListener(new KeyImeChange() {

			@Override
			public void onKeyIme(int keyCode, KeyEvent event) {

				LinearLayout l = (LinearLayout) findViewById(R.id.spacer);
				l.setVisibility(LinearLayout.VISIBLE);
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

	@SuppressLint("NewApi")
	public void partyManager(View view) { // used by the onClick attribute in
											// the xml file

		TextView Title = (TextView) findViewById(R.id.creation_et);

		if (Title.getText().toString().equals("")
				|| Title.getText().toString().equals(null)) {
			Toast.makeText(getApplicationContext(),
					"The title of the party cannot be empty",
					Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(this, PartyManager.class); // need to store
																// details about
																// the party,
																// maybe using
																// shared
																// preferences?

		final SharedPreferences sp = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		TextView From = (TextView) findViewById(R.id.from_et);
		TextView To = (TextView) findViewById(R.id.to_et);

		TextView Start = (TextView) findViewById(R.id.fromH_et);
		TextView End = (TextView) findViewById(R.id.toH_et);

		String[] FromDate = From.getText().toString().split("/");
		String[] FromHour = Start.getText().toString().split(":");

		String[] ToDate = To.getText().toString().split("/");
		String[] ToHour = End.getText().toString().split(":");

		Calendar FromC = Calendar.getInstance();
		FromC.set(Integer.parseInt(FromDate[2]),
				Integer.parseInt(FromDate[1]) - 1,
				Integer.parseInt(FromDate[0]), Integer.parseInt(FromHour[0]),
				Integer.parseInt(FromHour[1]));

		Calendar ToC = Calendar.getInstance();
		ToC.set(Integer.parseInt(ToDate[2]), Integer.parseInt(ToDate[1]) - 1,
				Integer.parseInt(ToDate[0]), Integer.parseInt(ToHour[0]),
				Integer.parseInt(ToHour[1]));

		// parse textview and add to shared preference the date in milliseconds.
		// It would be easier to use it later on (maybe)

		Editor editor = sp.edit();
		editor.putLong(StartingDate, FromC.getTimeInMillis());
		editor.putLong(EndingDate, ToC.getTimeInMillis());
		editor.putString(PartyName, Title.getText().toString());
		editor.commit();

		startActivity(intent);
		finish();

	}
}
