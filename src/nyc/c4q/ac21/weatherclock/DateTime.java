package nyc.c4q.ac21.weatherclock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class DateTime {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("h:mm");
    public static final SimpleDateFormat TIME_FORMAT_SECS = new SimpleDateFormat("h:mm:ss");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:MM");

    /**
     * Formats a 'Calendar' object as a date.
     * @param cal
     *   The object to format.
     * @return
     *   The date in "YYYY-MM-DD" format.
     */
    public static String formatDate(Calendar cal) {
        if (cal == null)
            return null;
        else
            return DATE_FORMAT.format(cal.getTime());
    }

    public static String format(Calendar cal) {
        if (cal == null)
            return null;
        else
            return DATE_TIME_FORMAT.format(cal.getTime());
    }

    /**
     * Formats a 'Calendar'
     * @param cal
     *   The object to format.
     * @param seconds
     *   If true, include seconds.
     * @return
     *   The time in "HH:MM" or "HH:MM:SS" format using a 12-hour clock.  No AM/PM indicator is included.
     */
    public static String formatTime(Calendar cal, boolean seconds) {
        if (cal == null)
            return null;
        else if (seconds)
            return TIME_FORMAT_SECS.format(cal.getTime());
        else
            return TIME_FORMAT.format(cal.getTime());
    }

    /**
     * Parses a date in "YYYY-MM-DD" format.
     * @param date
     *   The date in "YYYY-MM-DD" format.
     * @return
     *   The date it represents, or null if the date is incorrectly formatted.
     */
    public static Calendar parseDate(String date) {
        if (date.length() == 10 && date.charAt(4) == '-' && date.charAt(7) == '-') {
            try {
                int year = Integer.valueOf(date.substring(0, 4));
                int month = Integer.valueOf(date.substring(5, 7));
                int dayOfMonth = Integer.valueOf(date.substring(8, 10));
                if (year > 0 && year <= 9999
                        && month >= 1 && month <= 12
                        && dayOfMonth >= 1 && dayOfMonth <= 31) {
                    // Allocate directly instead of using Calendar.getInstance() so that we don't
                    // set the time fields and leave them initialized to zero.
                    return new GregorianCalendar(year, month - 1, dayOfMonth);
                }
            } catch (NumberFormatException exception) {
                // Fall through.
            }
        }
        // Didn't work.
        return null;
    }

    /**
     * Returns the following day.
     */
    public static Calendar getNextDay(Calendar cal) {
        // 'Calendar' is mutable, but we don't want to change it.  Instead, create a copy and change that.
        cal = (Calendar) cal.clone();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal;
    }

    /**
     * Builds and returns a map from integers representing days of the week to their names.
     * @return
     *   A map with keys 'Calendar.MONDAY' through 'Calendar.SUNDAY' with corresponding names as values.
     */
    public static HashMap<Integer, String> getDayOfWeekNames() {
        HashMap<Integer, String> names = new HashMap<Integer, String>();
        names.put(Calendar.MONDAY, "Monday");
        names.put(Calendar.TUESDAY, "Tuesday");
        names.put(Calendar.WEDNESDAY, "Wednesday");
        names.put(Calendar.THURSDAY, "Thursday");
        names.put(Calendar.FRIDAY, "Friday");
        names.put(Calendar.SATURDAY, "Saturday");
        names.put(Calendar.SUNDAY, "Sunday");
        return names;
    }

    /**
     * Builds and returns a map of month names.
     * @return
     *   A map with keys `Calendar.JANUARY` through `Calendar.DECEMBER` with corresponding names as values.
     */
    public static HashMap<Integer, String> getMonthNames() {
        HashMap<Integer, String> names = new HashMap<Integer, String>();
        names.put(Calendar.JANUARY, "January");
        names.put(Calendar.FEBRUARY, "February");
        names.put(Calendar.MARCH, "March");
        names.put(Calendar.APRIL, "April");
        names.put(Calendar.MAY, "May");
        names.put(Calendar.JUNE, "June");
        names.put(Calendar.JULY, "July");
        names.put(Calendar.AUGUST, "August");
        names.put(Calendar.SEPTEMBER, "September");
        names.put(Calendar.OCTOBER, "October");
        names.put(Calendar.NOVEMBER, "November");
        names.put(Calendar.DECEMBER, "December");
        return names;
    }

    /**
     * Converts a "UNIX time" timestamp to a Calendar object.
     *
     * A "UNIX time" timestamp represents a specific date and time as the number of seconds since
     * 1970 January 1 in the GMT/UTC time zone.
     *
     * @param timestamp
     *   The UNIX time timestamp.
     * @return
     *   The corresponding date and time as a `Calendar` object.
     */
    public static Calendar fromTimestamp(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000);
        return cal;
    }

    /**
     * Pauses program execution.
     * @param seconds
     *   The number of seconds to pause for.  May be fractional.
     */
    public static void pause(double seconds) {
        long millis = (long) (seconds * 1000);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // Ignore.
        }
    }

}
