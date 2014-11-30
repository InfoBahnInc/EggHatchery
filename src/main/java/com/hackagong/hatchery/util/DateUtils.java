package com.hackagong.hatchery.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities for working with the Java Date class.
 */

public final class DateUtils {

	//
	// Public statics
	//

	public static String formatISODateTime( Date date ) {

		synchronized ( ISO_8601_DATE_TIME_FORMAT ) {
			return ISO_8601_DATE_TIME_FORMAT.format( date );
		}
	}

	//
	// Private statics
	//

	private static final SimpleDateFormat	ISO_8601_DATE_TIME_FORMAT	= new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.S" );

	//
	// Private constructor
	//

	private DateUtils() {

		// Can never be called
	}
}
