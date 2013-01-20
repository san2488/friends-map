package edu.ncsu.soc.project1;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class FriendsMapOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

	private Context context;

	public FriendsMapOverlay(Context context, Drawable marker) {
		super(boundCenterBottom(marker));
		this.context = context;
		populate();
	}

	public void addMarker(String markerName, GeoPoint geoPoint) {
		for(OverlayItem item : items){
			if(item.getTitle() == markerName){
				items.remove(item);
			}
		}
		items.add(new OverlayItem(geoPoint, markerName, markerName));
		super.populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return (items.get(i));
	}

	@Override
	protected boolean onTap(int i) {
		Toast.makeText(context, items.get(i).getSnippet(), Toast.LENGTH_SHORT)
				.show();
		return true;
	}

	@Override
	public int size() {
		return items.size();
	}
}