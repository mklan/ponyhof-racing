package de.ponyhofgang.ponyhofgame.framework.math;

import java.text.DecimalFormat;

public class PonyMath {

	private static boolean firstTime;
	private static int min = 0;
	private static int sec;
	private static int millis;
	;

	public static float percentage(int value, int percantage) {

		return value / 100 * percantage;

	}

	public static String timeString(float time, DecimalFormat formatedTime) {

		int secTmp = Math.round(time);
		if (secTmp % 62 == 0)
			firstTime = true;
		if (secTmp % 60 == 0 && secTmp != 0 && firstTime) {
			firstTime = false;
			min++;

		}

		sec = secTmp - 60 * min;

		float roundedTime = (float) (((int) (time * 100)) / 100.0);
		DecimalFormat formatedTime2 = new DecimalFormat("00.00");
		formatedTime2.format(roundedTime);
		String roundedTimeString = roundedTime + "";

		if (!roundedTimeString.substring(roundedTimeString.length() - 2,
				roundedTimeString.length()).contains("."))
			millis = Integer
					.parseInt(roundedTimeString.substring(
							roundedTimeString.length() - 2,
							roundedTimeString.length()));

		return formatedTime.format(min) + ":" + formatedTime.format(sec) + ":"
				+ formatedTime.format(millis);

	}

	public static float getRatio(int l, int value) {
		
		return Math.round((l / 0.625f) / (2048 / value));
	}

}
