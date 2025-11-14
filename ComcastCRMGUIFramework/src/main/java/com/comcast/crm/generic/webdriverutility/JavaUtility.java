package com.comcast.crm.generic.webdriverutility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JavaUtility {

	public int getRandomNumber() {
		Random random = new Random();
		int randomNumber = random.nextInt(8000);
		return randomNumber;
	}

	public String getRandomAlphaNumeric(int length) {
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = (int) (alphaNumeric.length() * Math.random());
			sb.append(alphaNumeric.charAt(index));
		}

		return sb.toString();

	}

	public String getSystemDateYYYYMMDD() {
		Date dateObj = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(dateObj);
		return date;
	}

	public String getRequiredDateYYYYMMDD(int days) {
		Date dateObj = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(dateObj);
		Calendar cal = sdf.getCalendar();
		cal.add(Calendar.DAY_OF_MONTH, days);
		String reqDate = sdf.format(cal.getTime());
		return reqDate;
	}
	
	public String getSystemDateAndTime() {
		Date date = new Date();
		String sysDateAndTime = date.toString().replace(":", "-");
		return sysDateAndTime;
	}
}
