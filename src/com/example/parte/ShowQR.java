package com.example.parte;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowQR extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_qr);
		Intent i = getIntent();
		
		ImageView imgResult = (ImageView) findViewById(R.id.img_result);
		
		//DON'T NEED TO STORE QR IMAGE
		
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

		// TODO: After using this QR code, you should move it to a
		// permanent location, or delete it

		
//		 String result =
//		 i.getExtras().getString(la.droid.qr.Services.RESULT);
//		 //Just set result to a toast to be able to view it
//		 Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
	}
}
