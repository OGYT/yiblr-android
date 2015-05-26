package com.petersoboyejo.yiblr;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TimeSelectAwakeActivity extends Activity {

	String inputTime;
	int wakeHour;
	int wakeMinute;
	int inputTimeNum;
	Context rawr = this;

	private MyAdapter aa;
	private ArrayList<ListElement> aList;

	private class ListElement {
		ListElement() {
		};

		public int hour;
		public int minute;
		public String totalSleep;

	}

	private class MyAdapter extends ArrayAdapter<ListElement> {
		int resource;
		Context context;

		public MyAdapter(Context _context, int _resource,
				List<ListElement> items) {
			super(_context, _resource, items);
			resource = _resource;
			context = _context;
			this.context = _context;
		}

		//get view to update
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout newView;

			final ListElement w = getItem(position);

			if (convertView == null) {
				newView = new LinearLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater vi = (LayoutInflater) getContext()
						.getSystemService(inflater);
				vi.inflate(resource, newView, true);
			} else {
				newView = (LinearLayout) convertView;
			}
			// fill in the view
			TextView tv = (TextView) newView.findViewById(R.id.awake_alarm_item_textview);
			TextView tv2 = (TextView) newView.findViewById(R.id.awake_alarm_item_minutes);
			Button b = (Button) newView.findViewById(R.id.awake_item_set_button);
			

			if (w.hour >= 10) {
				if (w.minute >= 10) {
					tv.setText(Integer.toString(w.hour) + ":"
							+ Integer.toString(w.minute));
				} else {
					tv.setText(Integer.toString(w.hour) + ":0"
							+ Integer.toString(w.minute));
				}
			} else {
				if (w.minute >= 10) {
					tv.setText("0" + Integer.toString(w.hour) + ":"
							+ Integer.toString(w.minute));
				} else {
					tv.setText("0" + Integer.toString(w.hour) + ":0"
							+ Integer.toString(w.minute));
				}
			}

			tv2.setText(w.totalSleep + " hours of sleep");

			b.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					Intent intent = new Intent(context, AlarmDetailsActivity.class);
					intent.putExtra("sleepMinute", w.minute);
					intent.putExtra("sleepHour", w.hour);
					intent.putExtra("id", (long)-3);
					startActivityForResult(intent, 0);
					setResult(RESULT_OK);
					finish();
				}
			});
			return newView;

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_select_awake);

		getActionBar().setTitle("Suggested Wake Times");
		getActionBar().setDisplayHomeAsUpEnabled(true);

		aList = new ArrayList<ListElement>();
		aa = new MyAdapter(this, R.layout.suggested_awake_item, aList);
		ListView myListView = (ListView) findViewById(R.id.awake_time_list);
		myListView.setAdapter(aa);
		aa.notifyDataSetChanged();
		

		//get data passed in from intent
		wakeHour = getIntent().getExtras().getInt("hour");
		wakeMinute = getIntent().getExtras().getInt("minute");
		inputTime = getIntent().getExtras().getString("tillSleep");
		inputTimeNum = Integer.parseInt(inputTime);
		inputTime = Integer.toString(inputTimeNum);
		
		if(inputTimeNum > 60)
			inputTimeNum = 60;

		//create the list elements
		for (int i = 0; i < 6; i++) {

			ListElement el = new ListElement();
			el.hour = 3;
			el.minute = 3;
			el.totalSleep = "Rawrrr!";
			aList.add(el);

			aa.notifyDataSetChanged();
		}

		//fill in the list elements with the appropriate suggested times
		for (int i = 1; i < 7; i++) {
			int totalHours = (90 * i) / 60;
			int totalMinutes = (90 * i) % 60;
			ListElement el = aList.get(i - 1);
			int remainingMinutes = 0;
			if (wakeHour + totalHours >= 24) {
				el.hour = wakeHour + totalHours - 24;
			} else {
				el.hour = wakeHour + totalHours;
			}
			if ((wakeMinute + totalMinutes) > 60) {
				remainingMinutes = (totalMinutes + wakeMinute) % 60;
				el.minute = remainingMinutes;
				el.hour++;
				if(el.hour >= 24){
					el.hour = el.hour - 24;
				}
			} else {
				el.minute = wakeMinute + totalMinutes;
			}

			if (inputTimeNum == 60) {
				el.hour++;
				if(el.hour >= 24){
					el.hour = el.hour - 24;
				}
			} else {
				if (el.minute + inputTimeNum > 60) {
					remainingMinutes = (inputTimeNum + el.minute) % 60;
					el.minute = remainingMinutes;
					el.hour++;
					if(el.hour >= 24){
						el.hour = el.hour - 24;
					}
				} else {
					el.minute = el.minute + inputTimeNum;
				}
			}
			
			el.totalSleep = Double.toString(1.5*i);

			// TextView tvset = (TextView) findViewById(R.id.)
			aa.notifyDataSetChanged();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_select, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

}
