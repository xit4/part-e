package com.example.parte;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends Activity {
	
	public static final String MyPREFERENCES = "UserPartyPref";				//different shared preferences user / party?
	public static final String User = "username"; 	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		final SharedPreferences sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);	

		Button button = (Button) findViewById(R.id.button_confirm);
		final EditText Name = (EditText) findViewById(R.id.welcome_et);

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
					
					Editor editor = sp.edit();
					editor.putString(User, login_name);			//adding username to shared preferences
					editor.commit();
					
					Intent intent = new Intent();
					intent.putExtra("USER", login_name);		//probably useless, just use sp in main activity
					setResult(Activity.RESULT_OK, intent);
					finish();
				}

			}
		});

	}

}
