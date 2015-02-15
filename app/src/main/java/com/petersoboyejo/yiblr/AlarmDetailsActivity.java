package com.petersoboyejo.yiblr;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class AlarmDetailsActivity extends Activity {

	private AlarmDBHelper dbHelper = new AlarmDBHelper(this);

	private AlarmModel alarmDetails;

	private TimePicker timePicker;
	private EditText edtName;
	private CheckBox chkWeekly;
	private CheckBox chkSunday;
	private CheckBox chkMonday;
	private CheckBox chkTuesday;
	private CheckBox chkWednesday;
	private CheckBox chkThursday;
	private CheckBox chkFriday;
	private CheckBox chkSaturday;
	private TextView txtToneSelection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_details);

		alarmDetails = new AlarmModel();

		// enables back button on action bar
		getActionBar().setTitle("Create New Alarm");
		getActionBar().setDisplayHomeAsUpEnabled(true);

		//get all activity ojects
		timePicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
		edtName = (EditText) findViewById(R.id.alarm_details_name);
		chkWeekly = (CheckBox) findViewById(R.id.alarm_details_repeat_weekly);
		chkSunday = (CheckBox) findViewById(R.id.alarm_details_repeat_sunday);
		chkMonday = (CheckBox) findViewById(R.id.alarm_details_repeat_monday);
		chkTuesday = (CheckBox) findViewById(R.id.alarm_details_repeat_tuesday);
		chkWednesday = (CheckBox) findViewById(R.id.alarm_details_repeat_wednesday);
		chkThursday = (CheckBox) findViewById(R.id.alarm_details_repeat_thursday);
		chkFriday = (CheckBox) findViewById(R.id.alarm_details_repeat_friday);
		chkSaturday = (CheckBox) findViewById(R.id.alarm_details_repeat_saturday);
		txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);

		long id = getIntent().getExtras().getLong("id");

		//create a new alarm details activity based off the id that is passed in
		if (id == -1) {
			//new alarm
			alarmDetails = new AlarmModel();
		} else if(id == -2){ 
			//alarm to sleep with info passed in
			alarmDetails = new AlarmModel();
			timePicker.setCurrentHour(getIntent().getExtras().getInt("sleepHour"));
			timePicker.setCurrentMinute(getIntent().getExtras().getInt("sleepMinute"));
			edtName.setText("Go to sleep!!");
		} else if(id == -3){ 
			//alarm to wake up with info passed in
			alarmDetails = new AlarmModel();
			timePicker.setCurrentHour(getIntent().getExtras().getInt("sleepHour"));
			timePicker.setCurrentMinute(getIntent().getExtras().getInt("sleepMinute"));
			edtName.setText("Wake up!!");
		} else {
			//editing an old alarm, so load the data
			alarmDetails = dbHelper.getAlarm(id);

			timePicker.setCurrentMinute(alarmDetails.timeMinute);
			timePicker.setCurrentHour(alarmDetails.timeHour);

			edtName.setText(alarmDetails.name);

			chkWeekly.setChecked(alarmDetails.repeatWeekly);
			chkSunday.setChecked(alarmDetails
					.getRepeatingDay(AlarmModel.SUNDAY));
			chkMonday.setChecked(alarmDetails
					.getRepeatingDay(AlarmModel.MONDAY));
			chkTuesday.setChecked(alarmDetails
					.getRepeatingDay(AlarmModel.TUESDAY));
			chkWednesday.setChecked(alarmDetails
					.getRepeatingDay(AlarmModel.WEDNESDAY));
			chkThursday.setChecked(alarmDetails
					.getRepeatingDay(AlarmModel.THURSDAY));
			chkFriday.setChecked(alarmDetails
					.getRepeatingDay(AlarmModel.FRIDAY));
			chkSaturday.setChecked(alarmDetails
					.getRepeatingDay(AlarmModel.SATURDAY));

			txtToneSelection.setText(RingtoneManager.getRingtone(this,
					alarmDetails.alarmTone).getTitle(this));
		}

		// Adding event to linear layout of ringtone section that will spawn
		// ringtone picker
		final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
		ringToneContainer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						RingtoneManager.ACTION_RINGTONE_PICKER);
				startActivityForResult(intent, 1);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_details, menu);
		return true;
	}

	// save_alarm_details is the button that saves the alarm
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			break;
		}
		case R.id.action_save_alarm_details: {
			updateModelFromLayout();
			
			AlarmManagerHelper.cancelAlarms(this);

			// If alarm ID is less than 0, it is a new alarm and we need to
			// create a new one
			// If alarm ID is greater than 0, it is an existing alarm and we
			// need to update it
			// AlarmDBHelper dbHelper = new AlarmDBHelper(this);
			if (alarmDetails.id < 0) {
				dbHelper.createAlarm(alarmDetails);
			} else {
				dbHelper.updateAlarm(alarmDetails);
			}
			
			AlarmManagerHelper.setAlarms(this);
			
			setResult(RESULT_OK);
			finish();
		}
		}
		return super.onOptionsItemSelected(item);
	}

	// Once the user picks a ringtone, it is returned as a URI and is stored in
	// the alarm model
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1: {
				alarmDetails.alarmTone = data
						.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

				TextView txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);
				txtToneSelection.setText(RingtoneManager.getRingtone(this,
						alarmDetails.alarmTone).getTitle(this));
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	// updates our alarm model from the layout information
	private void updateModelFromLayout() {

		alarmDetails.timeMinute = timePicker.getCurrentMinute().intValue();
		alarmDetails.timeHour = timePicker.getCurrentHour().intValue();

		alarmDetails.name = edtName.getText().toString();

		alarmDetails.repeatWeekly = chkWeekly.isChecked();

		alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, chkSunday.isChecked());

		alarmDetails.setRepeatingDay(AlarmModel.MONDAY, chkMonday.isChecked());

		alarmDetails
				.setRepeatingDay(AlarmModel.TUESDAY, chkTuesday.isChecked());

		alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY,
				chkWednesday.isChecked());

		alarmDetails.setRepeatingDay(AlarmModel.THURSDAY,
				chkThursday.isChecked());

		alarmDetails.setRepeatingDay(AlarmModel.FRIDAY, chkFriday.isChecked());

		alarmDetails.setRepeatingDay(AlarmModel.SATURDAY,
				chkSaturday.isChecked());

		alarmDetails.isEnabled = true;
	}

}
