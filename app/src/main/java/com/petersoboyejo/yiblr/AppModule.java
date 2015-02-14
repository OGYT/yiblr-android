package com.petersoboyejo.yiblr;

import com.dmchoull.daybreak.services.AlarmHelper;
import com.dmchoull.daybreak.ui.AlarmListActivity;
import com.dmchoull.daybreak.ui.EditAlarmActivity;

import dagger.Module;

@Module(
        injects = {
                EditAlarmActivity.class,
                AlarmListActivity.class,
                AlarmHelper.class
        },
        complete = false
)
public class AppModule {
}