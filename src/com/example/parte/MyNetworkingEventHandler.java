package com.example.parte;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import com.example.qdn.*;

public class MyNetworkingEventHandler implements  NetworkingEventHandler{

	
	@Override
	public void valueChangedForKeyOfUser(JSONObject json, String key,
			String user) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void unlockedKeyOfUser(JSONObject json, String key, String user) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void savedValueForKeyOfUser(JSONObject json, String key, String user) {
		String code="";
		if(!json.isNull("code")){
		try {
		 
		 code = json.getString("code");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Integer a = Integer.parseInt(code);
		
		switch(a){
		case 0:
			Log.d("CallbackCode","Error creating Party List");
			break;
		case 1:
			Log.d("CallbackCode","New item created");
			break;
		case 2:
			Log.d("CallbackCode","Item updated");
			break;
		
		}
	}
	}
	
	@Override
	public void monitoringKeyOfUser(JSONObject json, String key, String user) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void lockedKeyofUser(JSONObject json, String key, String user) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void loadedValueForKeyOfUser(JSONObject json, String key, String user) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void ignoringKeyOfUser(JSONObject json, String key, String user) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deletedKeyOfUser(JSONObject json, String key, String user) {
		// TODO Auto-generated method stub
		
	}
}
