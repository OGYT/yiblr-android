package com.petersoboyejo.yiblr.models;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alarm extends SugarRecord<Alarm> {
    long time;

    @SuppressWarnings("UnusedDeclaration") public Alarm() {
    }

    public Alarm(long time) {
        this.time = time;
    }

    public int getHour() {
        return getCalendar().get(Calendar.HOUR_OF_DAY);
    }

    private Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    public int getMinute() {
        return getCalendar().get(Calendar.MINUTE);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String toString() {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }
}