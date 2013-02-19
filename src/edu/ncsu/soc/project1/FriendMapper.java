package edu.ncsu.soc.project1;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class FriendMapper extends MapActivity implements LocationListener{

	private FriendsMapOverlay mapOverlay;
	private MyLocationOverlay myLocationOverlay;
	LocationManager locationManager;
	String locationProvider;
	Location currentLocation;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_map);

		// Init location objects
		locationManager =
		        (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER).getName();
		
		// create the map overlay
		Drawable marker = getResources().getDrawable(R.drawable.marker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		mapOverlay = new FriendsMapOverlay(this, marker);
		

		// configure the friends list
		ListView list = (ListView) findViewById(R.id.FriendList);
		list.setAdapter(new FriendViewAdaptor(this));

		// configure the map
		MapView mapView = (MapView) findViewById(R.id.MapView);
		mapView.getController().setCenter(new GeoPoint(35772052, -78673718));
		mapView.getController().setZoom(15);
		mapView.getOverlays().add(mapOverlay);
		
		// configure My Location overlay
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);
		mapView.postInvalidate();

	}

	public void addMarkerAtCurrentLocation(String markerName) {
		locationManager.requestLocationUpdates(locationProvider, 60000, 100, this);
		
		Location lastLocation = locationManager.getLastKnownLocation(locationProvider);
		
		Location loc = currentLocation != null ? currentLocation : lastLocation;
		
		if(loc != null) {
			mapOverlay.addMarker(markerName, new GeoPoint((int)(lastLocation.getLatitude() * 1E6), 
				 (int)(lastLocation.getLongitude() * 1E6)));
		}
		else {
			// Show prompt indicating that location could be updated
			Toast.makeText(this.getBaseContext(), "Unable to resolve location. Is your GPS on?", Toast.LENGTH_SHORT).show();
		}
		
		
		// redraw the map
		MapView mapView = (MapView) findViewById(R.id.MapView);
		mapView.invalidate();

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
		locationManager.requestLocationUpdates(locationProvider, 60000, 100, this);
	};
	
	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
		locationManager.removeUpdates(this);
	};
	
	@Override
	public void onLocationChanged(Location loc) {
		currentLocation = loc;
	}
	
	@Override
	public void onProviderDisabled(String arg0) {
		Toast.makeText(getBaseContext(), "Location Services (like GPS) need to be enabled to use this app.", Toast.LENGTH_LONG).show(); 
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}