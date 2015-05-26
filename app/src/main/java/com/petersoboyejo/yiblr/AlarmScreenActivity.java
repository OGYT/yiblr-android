package com.petersoboyejo.yiblr;

import java.util.Random;
<<<<<<< HEAD
import java.util.UUID;
=======
>>>>>>> origin/master

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
<<<<<<< HEAD
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.PebbleKit.PebbleDataReceiver;
import com.getpebble.android.kit.util.PebbleDictionary;


public class AlarmScreenActivity extends Activity {

    private PebbleDataReceiver mReceiver;

    private static final int
            KEY_BUTTON_EVENT = 0,
            BUTTON_EVENT_UP = 1,
            BUTTON_EVENT_DOWN = 2,
            BUTTON_EVENT_SELECT = 3,
            KEY_VIBRATION = 4;

    public final String TAG = this.getClass().getSimpleName();
    private WakeLock mWakeLock;
    private MediaPlayer mPlayer;
    private static final int WAKELOCK_TIMEOUT = 60 * 1000;
    Random generator;
    int answer;
    EditText edv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);


        // Test Dismiss Button

        Button orderButton = (Button)findViewById(R.id.testDismiss);

        orderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismissAlarm();
            }

        });

        String name = getIntent().getStringExtra(AlarmManagerHelper.NAME);
        int timeHour = getIntent().getIntExtra(AlarmManagerHelper.TIME_HOUR, 0);
        int timeMinute = getIntent().getIntExtra(
                AlarmManagerHelper.TIME_MINUTE, 0);
        String tone = getIntent().getStringExtra(AlarmManagerHelper.TONE);

        TextView tvName = (TextView) findViewById(R.id.alarm_screen_name);
        tvName.setText(name);
        TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
        tvTime.setText(String.format("%02d : %02d", timeHour, timeMinute));

        mPlayer = new MediaPlayer();
        try {
            if (tone != null && !tone.equals("")) {
                Uri toneUri = Uri.parse(tone);
                if (toneUri != null) {
                    mPlayer.setDataSource(this, toneUri);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mPlayer.setLooping(true);
                    mPlayer.prepare();
                    mPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // stop the wakelock after a certain amount of time
        Runnable releaseWakelock = new Runnable() {

            @Override
            public void run() {
                getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

                if (mWakeLock != null && mWakeLock.isHeld()) {
                    mWakeLock.release();
                }
            }
        };

        new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Main Shit

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        PowerManager pm = (PowerManager) getApplicationContext()
                .getSystemService(Context.POWER_SERVICE);
        if (mWakeLock == null) {
            mWakeLock = pm
                    .newWakeLock(
                            (PowerManager.FULL_WAKE_LOCK
                                    | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP),
                            TAG);
        }

        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }

        // Pebble

        mReceiver = new PebbleDataReceiver(UUID.fromString("61bfd8aa-15bf-43fc-b140-d4d95601e239")) {

            @Override
            public void receiveData(Context context, int transactionId, PebbleDictionary data) {

                //ACK the message
                PebbleKit.sendAckToPebble(context, transactionId);

                //Check the key exists
                if (data.getUnsignedIntegerAsLong(KEY_BUTTON_EVENT) != null) {
                    int button = data.getUnsignedIntegerAsLong(KEY_BUTTON_EVENT).intValue();

                    switch (button) {
                        case BUTTON_EVENT_UP:
                            //The UP button was pressed
                            dismissAlarm();
                            break;
                        case BUTTON_EVENT_DOWN:
                            //The DOWN button was pressed
                            dismissAlarm();
                            break;
                        case BUTTON_EVENT_SELECT:
                            //The SELECT button was pressed
                            dismissAlarm();
                            break;
                    }
                }

                //Make the watch vibrate
                PebbleDictionary dict = new PebbleDictionary();
                dict.addInt32(KEY_VIBRATION, 0);
                PebbleKit.sendDataToPebble(context, UUID.fromString("61bfd8aa-15bf-43fc-b140-d4d95601e239"), dict);
            }

        };

        PebbleKit.registerReceivedDataHandler(this, mReceiver);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }

        unregisterReceiver(mReceiver);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.alarm_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dismissAlarm() {
        mPlayer.stop();
        finish();
    }
}
=======
import android.widget.EditText;
import android.widget.TextView;

public class AlarmScreenActivity extends Activity {

	public final String TAG = this.getClass().getSimpleName();
	private WakeLock mWakeLock;
	private MediaPlayer mPlayer;
	private static final int WAKELOCK_TIMEOUT = 60 * 1000;
	Random generator;
	int answer;
	EditText edv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_screen);

		generator = new Random();
		int var1 = generator.nextInt(10) + 1;
		int var2 = generator.nextInt(10) + 1;
		answer = var1 * var2;

		edv1 = (EditText) findViewById(R.id.alarm_answer_edit);
		TextView tv1 = (TextView) findViewById(R.id.alarm_question_textview);
		tv1.setText(var1 + " * " + var2 + " = ?");

		String name = getIntent().getStringExtra(AlarmManagerHelper.NAME);
		int timeHour = getIntent().getIntExtra(AlarmManagerHelper.TIME_HOUR, 0);
		int timeMinute = getIntent().getIntExtra(
				AlarmManagerHelper.TIME_MINUTE, 0);
		String tone = getIntent().getStringExtra(AlarmManagerHelper.TONE);

		TextView tvName = (TextView) findViewById(R.id.alarm_screen_name);
		tvName.setText(name);
		TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
		tvTime.setText(String.format("%02d : %02d", timeHour, timeMinute));

		mPlayer = new MediaPlayer();
		try {
			if (tone != null && !tone.equals("")) {
				Uri toneUri = Uri.parse(tone);
				if (toneUri != null) {
					mPlayer.setDataSource(this, toneUri);
					mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
					mPlayer.setLooping(true);
					mPlayer.prepare();
					mPlayer.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// stop the wakelock after a certain amount of time
		Runnable releaseWakelock = new Runnable() {

			@Override
			public void run() {
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

				if (mWakeLock != null && mWakeLock.isHeld()) {
					mWakeLock.release();
				}
			}
		};

		new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);

	}

	@Override
	protected void onResume() {
		super.onResume();

		// set window to keep screen on
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		PowerManager pm = (PowerManager) getApplicationContext()
				.getSystemService(Context.POWER_SERVICE);
		if (mWakeLock == null) {
			mWakeLock = pm
					.newWakeLock(
							(PowerManager.FULL_WAKE_LOCK
									| PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP),
							TAG);
		}

		if (!mWakeLock.isHeld()) {
			mWakeLock.acquire();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mWakeLock != null && mWakeLock.isHeld()) {
			mWakeLock.release();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void dismissAlarm() {
		mPlayer.stop();
		finish();
	}

	public void checkAnswer(View v) {
		if (!edv1.getText().toString().matches("")) {
			if (Integer.parseInt(edv1.getText().toString()) == answer) {
				dismissAlarm();
			}
		}
	}

}
>>>>>>> origin/master
