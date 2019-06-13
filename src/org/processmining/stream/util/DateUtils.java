package org.processmining.stream.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date floorMinutes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date addMinutes(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, amount);
		return calendar.getTime();
	}

	/**
	 * Returns the difference between date1 and date 2 in the order of minutes
	 * date2 will be subtracted from date1. Thus if date2 is in the future,
	 * w.r.t. date1, the result will be negative.
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differenceInMinutes(Date date1, Date date2) {
		long diffMs = date1.getTime() - date2.getTime();
		Long diffMinutes = diffMs / (60 * 1000);

		return diffMinutes.intValue();
	}
}
