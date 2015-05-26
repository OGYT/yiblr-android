package com.petersoboyejo.yiblr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TimePicker;

public class SleepAlarmActivity extends Activity {

	private TimePicker timePicker;
	private EditText sleepEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sleep_alarm);

		getActionBar().setTitle("Sleep Alarm");
		getActionBar().setDisplayHomeAsUpEnabled(true);

		timePicker = (TimePicker) findViewById(R.id.sleep_alarm_timepicker);
		sleepEdit = (EditText) findViewById(R.id.sleep_alarm_edit);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sleep_alarm, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			break;
		}
		//when the save button is selected, the alarm creation page is started with the information the user input passed in
		case R.id.action_save_sleep_alarm: {
			String timeTillSleep = "0";
			Intent intent = new Intent(this, TimeSelectActivity.class);
			int timeHour = timePicker.getCurrentHour().intValue();
			int timeMinute = timePicker.getCurrentMinute().intValue();
			if (!sleepEdit.getText().toString().matches("")) {
				timeTillSleep = sleepEdit.getText().toString();
			}
			intent.putExtra("hour", timeHour);
			intent.putExtra("minute", timeMinute);
			intent.putExtra("tillSleep", timeTillSleep);
			startActivityForResult(intent, 0);
			setResult(RESULT_OK);
			finish();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

}
