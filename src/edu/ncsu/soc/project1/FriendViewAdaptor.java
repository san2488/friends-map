package edu.ncsu.soc.project1;

import java.util.ArrayList;


import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Context;

public class FriendViewAdaptor extends BaseAdapter {

	private FriendMapper friendMapper;

	private ArrayList<String> names = new ArrayList<String>();

	public FriendViewAdaptor(FriendMapper friendMapper) {

		this.friendMapper = friendMapper;
		Cursor people = friendMapper.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		while(people.moveToNext()) {
		   int nameFieldColumnIndex = people.getColumnIndex(PhoneLookup.DISPLAY_NAME);
		   String contact = people.getString(nameFieldColumnIndex);
//		   int numberFieldColumnIndex = people.getColumnIndex(PhoneLookup.NUMBER);
//		   String number = people.getString(numberFieldColumnIndex);
		   names.add(contact);
		}

		people.close();
		// TODO put code here for getting contact's names from the
		// contentProvider and storing them in the list
		

	}

	public int getCount() {
		return names.size();
	}

	public Object getItem(int position) {
		return names.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(friendMapper);
		textView.setText(names.get(position));
		textView.setClickable(true);
		textView.setOnClickListener(new FriendListener());
		return textView;
	}

	public class FriendListener implements OnClickListener {

		public void onClick(View view) {
			TextView textView = (TextView) view;
			friendMapper.addMarkerAtCurrentLocation(textView.getText()
					.toString());
		}
	}
}