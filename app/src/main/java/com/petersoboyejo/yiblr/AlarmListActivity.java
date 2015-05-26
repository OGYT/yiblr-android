package com.petersoboyejo.yiblr;

<<<<<<< HEAD
=======
//Creation of alarm clock was adapted from the tutorial at this link
//http://www.steventrigg.com/alarm-clock-tutorials/
//author has claimed it to be free to use

>>>>>>> origin/master
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
<<<<<<< HEAD
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
=======
import android.view.Menu;
import android.view.MenuItem;
>>>>>>> origin/master

public class AlarmListActivity extends ListActivity {

	private AlarmListAdapter mAdapter;
	private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
	private Context mContext;

<<<<<<< HEAD
    private SensorManager mSensorManager;
    private Sensor mSensor;

=======
>>>>>>> origin/master
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		setContentView(R.layout.activity_alarm_list);
		mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());
		setListAdapter(mAdapter);

<<<<<<< HEAD
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        /*
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sen = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(sen==null){
            Toast.makeText(this, "You have no proximity sensor", Toast.LENGTH_LONG).show();
            finish();
        }
        */

	}


=======
	}

>>>>>>> origin/master
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//switch cases for action bar items
		switch (item.getItemId()) {
<<<<<<< HEAD
            case R.id.action_add_new_alarm: {
                startAlarmDetailsActivity(-1);
                break;
            }
            case R.id.action_sleep_alarm_button: {
                Intent intent = new Intent(this, SleepAlarmActivity.class);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.action_awake_alarm_button: {
                Intent intent = new Intent(this, AwakeAlarmActivity.class);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.action_settings: {
                Intent intent = new Intent(this, AwakeAlarmActivity.class);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.action_howto: {
                Intent intent = new Intent(this, AwakeAlarmActivity.class);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.action_about: {
                Intent intent = new Intent(this, AwakeAlarmActivity.class);
                startActivityForResult(intent, 0);
                break;
            }
        }
=======
		case R.id.action_add_new_alarm: {
			startAlarmDetailsActivity(-1);
			break;
		}
		case R.id.action_sleep_alarm_button: {
			Intent intent = new Intent(this, SleepAlarmActivity.class);
			startActivityForResult(intent, 0);
			break;
		}
		case R.id.action_awake_alarm_button: {
			Intent intent = new Intent(this, AwakeAlarmActivity.class);
			startActivityForResult(intent, 0);
			break;
		}
		}
>>>>>>> origin/master

		return super.onOptionsItemSelected(item);
	}

	public void setAlarmEnabled(long id, boolean isEnabled) {

		AlarmManagerHelper.cancelAlarms(this);

		AlarmModel model = dbHelper.getAlarm(id);
		model.isEnabled = isEnabled;
		dbHelper.updateAlarm(model);

		AlarmManagerHelper.setAlarms(this);
	}

	//start the activity to create a new alarm
	public void startAlarmDetailsActivity(long id) {
		Intent intent = new Intent(this, AlarmDetailsActivity.class);
		intent.putExtra("id", id);
		startActivityForResult(intent, 0);
	}

	//when the app returns to this activity, update the list of alarms
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			mAdapter.setAlarms(dbHelper.getAlarms());
			mAdapter.notifyDataSetChanged();
		}
	}

	
	//function to delete alarms
	public void deleteAlarm(long id) {
		final long alarmId = id;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Please confirm").setTitle("Delete set?")
				.setCancelable(true).setNegativeButton("Cancel", null)
				.setPositiveButton("Ok", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Cancel Alarms
						AlarmManagerHelper.cancelAlarms(mContext);
						// Delete alarm from DB by id
						dbHelper.deleteAlarm(alarmId);
						// Refresh the list of the alarms in the adaptor
						mAdapter.setAlarms(dbHelper.getAlarms());
						// Notify the adapter the data has changed
						mAdapter.notifyDataSetChanged();
						// Set the alarms
						AlarmManagerHelper.setAlarms(mContext);
					}
				}).show();
	}

}
