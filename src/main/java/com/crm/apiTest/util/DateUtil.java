package com.crm.apiTest.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	public static final int MILISECONDS_IN_DAY = 1000 * 60 * 60 * 24;
	public Date setDateTimeToLastMomentOfDay(Date date) {
		return setDateTime(date, 23, 59, 59, 999);
	}
	public Date setDateTimeToFirstMomentOfDay(Date date) {
		return setDateTime(date, 0, 0, 0, 0);	
	}
	public Date setDateTime(Date date, int hours, int minutes, int seconds, int miliseconds) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, hours);
	    calendar.set(Calendar.MINUTE, minutes);
	    calendar.set(Calendar.SECOND, seconds);
	    calendar.set(Calendar.MILLISECOND, miliseconds);
	    date = calendar.getTime();
	    return date;
	}
}
