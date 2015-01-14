package com.example.parte;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class ShowQR extends ActionBarActivity {
	
	//simple activity used to show the QR code passed as extra within the intent

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_qr);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
		
		getSupportActionBar().setTitle("QR Code");	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		
		ImageView imgResult = (ImageView) findViewById(R.id.img_result);
		
		String qrCode = i.getExtras().getString(
				la.droid.qr.Services.RESULT);

		if (null == qrCode || 0 == qrCode.trim().length()) {
			// If image path was not returned, it could not be
			// saved. Check SD card is mounted and is writable
			return;
		}

		// Load QR code image from given path
		imgResult.setImageURI(Uri.parse(qrCode));
		imgResult.setVisibility(View.VISIBLE);


	}
}
