package de.fhconfig.android.library.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeHelper
{
	public static Date fromUtc(Date date) throws ParseException {
		DateFormat utcFormat = SimpleDateFormat.getDateTimeInstance();
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		DateFormat format = SimpleDateFormat.getDateTimeInstance();
		return utcFormat.parse(format.format(date));
	}

	public static Date toUtc(Date date) throws ParseException {
		DateFormat utcFormat = SimpleDateFormat.getDateTimeInstance();
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		DateFormat format = SimpleDateFormat.getDateTimeInstance();
		return format.parse(utcFormat.format(date));
	}
}
