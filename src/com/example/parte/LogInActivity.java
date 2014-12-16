package com.example.parte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Button button = (Button) findViewById(R.id.button_confirm);
		final EditText Name = (EditText) findViewById(R.id.welcome_et);
		 // used for qdn
																// login?

		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Perform action on click final String login_name =
				// Name.getText().toString(); //used for qdn login?
				String login_name = Name.getText().toString();
				if (login_name.equals("") || login_name.equals(null)) {
					Toast.makeText(getApplicationContext(),
							"Please insert your name first!",
							Toast.LENGTH_SHORT).show();

				} else {

					Intent intent = new Intent();
					intent.putExtra("USER", login_name);
					setResult(Activity.RESULT_OK, intent);
					finish();
				}

			}
		});

	}

}
