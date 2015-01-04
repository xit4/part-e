package com.example.parte;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class ShowGallery extends Activity {

		int currentPosition;
		File[] file;
		ArrayList<String> imageIDs= new ArrayList<String>();
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.gallery);
			
			final SharedPreferences sp = getSharedPreferences(MainActivity.MyPREFERENCES,
					Context.MODE_PRIVATE);
			String DirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ File.separator
					+ "Part-E" + File.separator + sp.getString(MainActivity.User, "defaultUser") + File.separator
					+ sp.getString(MainActivity.PartyName, "defaultParty");
		    File f = new File(DirectoryPath);
		    
		    if (!f.exists()) {
		    	f.mkdirs();
		    }
		    		
		    file = f.listFiles();
		    
		    for(int i=0;i<file.length;i++){
//		    	Bitmap myBitmap =BitmapFactory.decodeFile(file[i].getAbsolutePath());
		    imageIDs.add(file[i].getAbsolutePath());
		    
		    }
				// Note that Gallery view is deprecated in Android 4.1---
				Gallery gallery = (Gallery) findViewById(R.id.gallery1);
				ImageView imageView = (ImageView) findViewById(R.id.image1);
				imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
				    @Override
				    public void onSwipeRight() {
				    	ImageView imageView = (ImageView) findViewById(R.id.image1);
						currentPosition-=1;
						if(currentPosition <0) currentPosition =0;
						imageView.setImageURI(Uri.parse(imageIDs.get(currentPosition)));
				    }
				    @Override
				    public void onSwipeLeft() {
				    	ImageView imageView = (ImageView) findViewById(R.id.image1);
						currentPosition+=1;
						if(currentPosition >=imageIDs.size()) currentPosition--;
						imageView.setImageURI(Uri.parse(imageIDs.get(currentPosition)));
				    }
				    
				});
				gallery.setAdapter(new ImageAdapter(this));
				
				gallery.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position,long id)
			{
						// display the images selected
						ImageView imageView = (ImageView) findViewById(R.id.image1);
						currentPosition=position;
						imageView.setImageURI(Uri.parse(imageIDs.get(currentPosition)));
				}
			});
		}

		public class ImageAdapter extends BaseAdapter {
			private Context context;
			private int itemBackground;
			public ImageAdapter(Context c)
			{
				context = c;
				// sets a grey background; wraps around the images
//				TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
//				itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
//				a.recycle();
			}
			// returns the number of images
			public int getCount() {
				return imageIDs.size();
			}
			// returns the ID of an item
			public Object getItem(int position) {
				return position;
			}
			// returns the ID of an item
			public long getItemId(int position) {
				return position;
			}
			// returns an ImageView view
			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView imageView = new ImageView(context);
				Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imageIDs.get(position)), 200, 200);
				imageView.setImageBitmap(ThumbImage);
				imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
				imageView.setBackgroundResource(itemBackground);
				return imageView;
			}
		}

		public class OnSwipeTouchListener implements OnTouchListener {

		    private final GestureDetector gestureDetector;

		    public OnSwipeTouchListener(Context context) {
		        gestureDetector = new GestureDetector(context, new GestureListener());
		    }

		    public void onSwipeLeft() {
		    }

		    public void onSwipeRight() {
		    }

		    public boolean onTouch(View v, MotionEvent event) {
		        return gestureDetector.onTouchEvent(event);
		    }

		    private final class GestureListener extends SimpleOnGestureListener {

		        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
		        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

		        @Override
		        public boolean onDown(MotionEvent e) {
		            return true;
		        }

		        @Override
		        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		            float distanceX = e2.getX() - e1.getX();
		            float distanceY = e2.getY() - e1.getY();
		            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
		                if (distanceX > 0)
		                    onSwipeRight();
		                else
		                    onSwipeLeft();
		                return true;
		            }
		            return false;
		        }
		    }
		}

}
