package com.example.parte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private final String[] mobileValues;

	public ImageAdapter(Context context, String[] mobileValues) {
		this.context = context;
		this.mobileValues = mobileValues;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout
			gridView = inflater.inflate(R.layout.grid_item, null);

			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(mobileValues[position]);

			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);

			String mobile = mobileValues[position];

			if (mobile.equals("Who Am I")) {
				imageView.setImageResource(R.drawable.ic_action_help);
				textView.setText("Who Am I");
			} else if (mobile.equals("Gallery")) {
				imageView.setImageResource(R.drawable.ic_action_storage);
				textView.setText("Gallery");
			} else if (mobile.equals("Invite")) {
				imageView.setImageResource(R.drawable.icon_invite);
				textView.setText("Invite");
			} else {
				imageView.setImageResource(R.drawable.ic_action_camera);
				textView.setText("Camera");
			}

		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
