package com.like.likeutils.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtil {

	public static int getMonth(Calendar cal) {
	    int month = cal.get(Calendar.MONTH) + 1;
	    return month;
	}
	
	public static int getMonth(Date date) {
		Calendar cal = toCalendar(date);
	    int month = cal.get(Calendar.MONTH) + 1;
	    return month;
	}
	
	public static int getDay(Calendar cal) {
	    int day = cal.get(Calendar.DATE);
	    return day;
	}
	
	public static int getDay(Date date) {
		Calendar cal = toCalendar(date);
	    int day = cal.get(Calendar.DATE);
	    return day;
	}
	
	public static int getYear(Calendar cal) {
	    int year = cal.get(Calendar.YEAR);
	    return year;
	}
	
	public static int getYear(Date date) {
		Calendar cal = toCalendar(date);
	    int year = cal.get(Calendar.YEAR);
	    return year;
	}
	
	public static int getDayOfWeek(Calendar cal) {
	    int dow = cal.get(Calendar.DAY_OF_WEEK);
	    return dow;
	}
	
	public static int getDayOfWeek(Date date) {
		Calendar cal = toCalendar(date);
	    int dow = cal.get(Calendar.DAY_OF_WEEK);
	    return dow;
	}
	
	public static int getDayOfMonth(Calendar cal) {
	    int dom = cal.get(Calendar.DAY_OF_MONTH);
	    return dom;
	}
	
	public static int getDayOfMonth(Date date) {
		Calendar cal = toCalendar(date);
	    int dom = cal.get(Calendar.DAY_OF_MONTH);
	    return dom;
	}
	
	public static int getDayOfYear(Calendar cal) {
	    int doy = cal.get(Calendar.DAY_OF_YEAR);
	    return doy;
	}
	
	public static int getDayOfYear(Date date) {
		Calendar cal = toCalendar(date);
	    int doy = cal.get(Calendar.DAY_OF_YEAR);
	    return doy;
	}
	
	public static Date toDate(Calendar calendar) {
		Date date=calendar.getTime();
		return date;
	}
	
	public static Date toDate(String dateStr, String dataFormat) {
		SimpleDateFormat sdf= new SimpleDateFormat(dataFormat, Locale.getDefault());
		try {
			Date date =sdf.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public static Calendar toCalendar(String dateStr, String dataFormat) {
		Date date = toDate(dateStr, dataFormat);
		if(date == null)
			return null;
		return toCalendar(date);
	}
	
	public static String toString(Date date, String dataFormat) {
		SimpleDateFormat sdf= new SimpleDateFormat(dataFormat, Locale.getDefault());
		String dateStr=sdf.format(date);
		return dateStr;
	}
	
	public static String toString(Calendar calendar, String dataFormat) {
		Date date = toDate(calendar);
		return toString(date, dataFormat);
	}
	
	public static Calendar add(Calendar cal, int type, int day) {
		cal.add(type, day);
		return cal;
	}
	
	public static Date add(Date date, int type, int day) {
		Calendar cal = toCalendar(date);
		cal.add(type, day);
		return toDate(cal);
	}

}
