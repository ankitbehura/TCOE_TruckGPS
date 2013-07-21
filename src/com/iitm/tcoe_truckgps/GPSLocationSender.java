package com.iitm.tcoe_truckgps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;

import android.content.Context;

import android.location.Location;

import android.location.LocationListener;

import android.location.LocationManager;

import android.os.AsyncTask;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class GPSLocationSender extends Activity

{
	Context context =this;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gpslocation_sender);
		Log.d("Debug", "App started");
		final CheckBox chk = (CheckBox) findViewById(R.id.checkBox1);
		Button send = (Button) findViewById(R.id.SendGps);
		/* Use the LocationManager class to obtain GPS locations */
		final LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		final LocationListener mlocListener = new MyLocationListener();
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Debug", "Clicked");
				if(chk.isChecked()){
					//This will ensure that the location data is sent to the server every 5 mins or 500 metres.
					mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 300000, 500, mlocListener);
				}
				mlocManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mlocListener, null);
			}
		});
		
	}


/* Class My Location Listener */
public class MyLocationListener implements LocationListener

{

	@Override

	public void onLocationChanged(Location loc)

	{

			loc.getLatitude();

			loc.getLongitude();

			String Text = "My current location is: " + "Latitud = " + loc.getLatitude() + "Longitud = " + loc.getLongitude();
			String longitude = "Longitude=" + loc.getLongitude();
			String latitude = "Latitude=" + loc.getLatitude();
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

			String Vehicle_id = "Vehicle_ID=" + tm.getDeviceId();
			Log.d("Debug", Text);
			DownloadWebPageTask task = new DownloadWebPageTask();
			String url = "http://10.0.0.2/tcoe/addlocation.php?"+longitude+"&"+latitude+"&"+Vehicle_id;
			Log.d("Debug", url);
			task.execute(url);
			Toast.makeText( getApplicationContext(),Text,Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onProviderDisabled(String provider)
	{
		Toast.makeText( getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT ).show();
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		Toast.makeText( getApplicationContext(), "Gps Enabled",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	}

}/* End of Class MyLocationListener */

//Class DownloadWebPageTask
private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... urls) {
		Log.d("DEBUG", "Inside dwp");
		String response = "";
		for (String url : urls) {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			try {
				HttpResponse execute = client.execute(httpGet);
				InputStream content = execute.getEntity().getContent();

				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response += s;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				Log.d("DEBUG", "Exception");
			}
		}
		Log.d("DEBUG", "Done dwp");
		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		Log.d("DEBUG", "onPostExecute"+result);
		Toast.makeText(context,  result, Toast.LENGTH_LONG).show();
	}
}

}/* End of UseGps Activity */