package io.github.crisstanza.jchron;

import static java.lang.String.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;

import javax.swing.text.NumberFormatter;

/**
 * @author Cris Stanza, 17-Ago-2015
 */
public final class JChron {

	private static final float _1000_0_x_60_x_60 = 3600000; // 1000.0 * 60 * 60;
	private static final float _1000_0_x_60 = 60000; // 1000.0 * 60;
	private static final float _1000_0 = 1000; // 1000.0 * 60;

	private static final String EMPTY = "", S = "s";
	private static final int ONE = 1;

	private static final String FORMAT_DEC_3 = "#,##0.###";
	private static final Locale DEFAULT_LOCALE = new Locale("en", "US");
	private static final DecimalFormatSymbols DEFAULT_DECIMAL_FORMAT_SYMBOLS = new DecimalFormatSymbols(DEFAULT_LOCALE);

	private static final String READ_FORMAT_HMS = "[ %s hour%s %s min%s %s sec%s ]";
	private static final String READ_FORMAT_MS = "[ %s min%s %s sec%s ]";
	private static final String READ_FORMAT_S = "[ %s sec%s ]";

	private static final String ERROR_JCRON_IS_ALREADY_STARTED = "JCron is already started!";

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final int START = 1, STOP = 2;

	private int estatus = STOP;
	private Date start = new Date(), stop = start;

	public final void start() {
		if (estatus == START) {
			throw new IllegalStateException(ERROR_JCRON_IS_ALREADY_STARTED);
		} else {
			start = new Date();
			estatus = START;
		}
	}

	public final void stop() {
		stop = new Date();
		estatus = STOP;
	}

	public final String read() {
		final long remainder = (estatus == STOP ? stop : new Date()).getTime() - start.getTime();
		return JChron.remainder(remainder);
	}

	public static final String remainder(long remainder) {
		final long hours = (long) (remainder / _1000_0_x_60_x_60);
		remainder %= _1000_0_x_60_x_60;
		final long mins = (long) (remainder / _1000_0_x_60);
		remainder %= _1000_0_x_60;
		final float secs = remainder / _1000_0;
		try {
			if (hours > 0) {
				return format(READ_FORMAT_HMS, hours, plural(hours), mins, plural(mins), dec3(secs), plural(secs));
			} else if (mins > 0) {
				return format(READ_FORMAT_MS, mins, plural(mins), dec3(secs), plural(secs));
			} else {
				return format(READ_FORMAT_S, dec3(secs), plural(secs));
			}
		} catch (final Exception exc) {
			return exc.toString();
		}
	}

	private static final String plural(final float n) {
		return n == ONE ? EMPTY : S;
	}

	private static final String dec3(final float n) throws Exception {
		return new NumberFormatter(new DecimalFormat(FORMAT_DEC_3, DEFAULT_DECIMAL_FORMAT_SYMBOLS)).valueToString(n);
	}

}
