package com.graduation.hp.core.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {


    public static final int SECONDS_PER_MINUTE = 60;
    public static final int MILLISECONDS_PER_SECOND = 1000;
    public static final int MILLISECONDS_PER_MINUTE = MILLISECONDS_PER_SECOND * 60;
    public static final int MILLISECONDS_PER_DAY = 24 * SECONDS_PER_MINUTE * MILLISECONDS_PER_MINUTE;

    private static final Class CLASS = DateUtils.class;
    private static final String TAG = DateUtils.class.getSimpleName();

    public static String getDateTimeString(Date date) {
        if (date != null) {
            final DateFormat dateFormat =
                    DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
            dateFormat.setTimeZone(TimeZone.getDefault());
            return dateFormat.format(date);
        } else {
            return "";
        }
    }

    public static String getDateTimeString(long dateInMillis) {
        return getDateTimeString(new Date(dateInMillis));
    }

    /**
     * Converts secondsSinceEpoch to a {@link java.util.Date} object.
     *
     * @param secondsSinceEpoch the number of seconds since January 1, 1970, 00:00:00 GMT
     * @return a Date object representing <code>secondsSinceEpoch</code>
     */
    public static Date dateFromSecondsSinceEpoch(long secondsSinceEpoch) {
        return new Date(secondsSinceEpoch * 1000);
    }

    // Returns the due date in the format the server recognizes
    public static String getDueDateString(Date dueDate) {
        return new SimpleDateFormat("yyyy-MM-dd").format(dueDate);
    }

    public static String getDueDateString(long timeInMillis) {
        return getDueDateString(new Date(timeInMillis));
    }


    public static Date getDateFromUTCString(String utcFormattedString) {
        if (utcFormattedString == null) {
            return null;
        }

        try {
            return getUTCSimpleDateFormatWithZone().parse(utcFormattedString);
        } catch (ParseException e) {
            Log.e(TAG, "Invalid UTC formatted string: " + String.valueOf(utcFormattedString), e);
            return null;
        }
    }

    public static SimpleDateFormat getUTCDateFormat() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",
                Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    public static String getUTCFormattedString(Date date) {
        return getUTCDateFormat().format(date);
    }

    public static String getUTCFormattedStringWithZone(Date date) {
        return getUTCSimpleDateFormatWithZone().format(date);
    }

    public static String getISOFormattedStringwithZone(Date date) {
        return getISOSimpleDateFormatDefaultZone().format(date);
    }

    public static SimpleDateFormat getUSDateFormat() {
        return new SimpleDateFormat("MMM d, y", Locale.US);
    }

    public static SimpleDateFormat getUSDateWithTimeFormat() {
        return new SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault());
    }

    public static boolean isSameDay(Date date1, Date date2) {
        final Calendar cal1 = Calendar.getInstance();
        final Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isBeforeCurrentDate(Date date) {
        return date != null && date.before(Calendar.getInstance().getTime());
    }

    public static Date setDate(Date date, int year, int monthOfYear, int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar.getTime();
    }

    public static Date setTime(Date date, int hourOfDay, int minute) {
        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private static SimpleDateFormat getUTCSimpleDateFormatWithZone() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    /**
     * Calculates the difference between two dates and returns the result in milliseconds.
     *
     * @param dateOne
     * @param dateTwo
     * @return difference of time between the two dates in milliseconds
     */
    public static long getTimeDifferenceInMillis(Date dateOne, Date dateTwo) {
        final long dateOneMillis = dateOne.getTime();
        final long dateTwoMillis = dateTwo.getTime();
        return Math.abs(dateOneMillis - dateTwoMillis);
    }

    public static long toMilliseconds(long seconds) {
        return seconds * 1000;
    }

    public static String getHoursMinutesSecondsString(long millis) {
        final long totalSeconds = millis / 1000;
        final long seconds = totalSeconds % 60;
        final long minutes = (totalSeconds / 60) % 60;
        final long hours = (totalSeconds / (60 * 60)) % 24;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Returns the time since date in a string format (e.g. Yesterday, 20 hours ago, etc.)
     */
    public static String getTimeSinceString(Date date, boolean showDateAndTimeIfPastYesterday) {
        final int minuteInSecs = 60;
        final int hourInSecs = 60 * minuteInSecs;
        final int dayInSecs = 24 * hourInSecs;

        if (date == null) {
            return "";
        }

        // Get elapsed time in seconds.
        final long elapsed = (new Date().getTime() - date.getTime()) / 1000;

        if (elapsed < minuteInSecs) {
            final int seconds = (int) elapsed;

            if (seconds <= 1) {
                return HPApplication.getStringById(R.string.one_second_ago);
            } else {
                return HPApplication.getStringById(R.string.nnn_seconds_ago, seconds);
            }
        } else if (elapsed < hourInSecs) {
            final int minutes = (int) (elapsed / minuteInSecs);

            if (minutes <= 1) {
                return HPApplication.getStringById(R.string.one_minute_ago);
            } else {
                return HPApplication.getStringById(R.string.nnn_minutes_ago, minutes);
            }
        } else if (elapsed < dayInSecs) {
            final int hours = Math.min(23, (int) ((elapsed + (hourInSecs / 2)) / hourInSecs));

            if (hours <= 1) {
                return HPApplication.getStringById(R.string.one_hour_ago);
            } else {
                return HPApplication.getStringById(R.string.nnn_hours_ago, hours);
            }
        } else if (showDateAndTimeIfPastYesterday) {
            Calendar calendar = Calendar.getInstance();
            int yearNow = calendar.get(Calendar.YEAR);
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            if (year == yearNow) {
                return new SimpleDateFormat("MMM dd, h:mm a", Locale.getDefault()).format(date);
            } else {
                return new SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault()).format(date);
            }
        } else if (elapsed < (dayInSecs * 2)) {
            return HPApplication.getStringById(R.string.yesterday);
        } else {
            return getDefaultDateFormat().format(date);
        }
    }

    public static String getSimpleDateTimeStamp(Date date) {
        final Calendar calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(date);
        final Calendar now = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        now.setTime(new Date(System.currentTimeMillis()));
        if (calendar.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            return getSimpleTimeStamp(date);
        } else if (now.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR) < 6) {
            return new SimpleDateFormat("EEE", Locale.getDefault()).format(date);
        } else if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            return new SimpleDateFormat("MMM d", Locale.getDefault()).format(date);
        } else {
            return getDefaultDateFormat().format(date);
        }
    }

    public static String getSimpleTimeStamp(Date date) {
        return new SimpleDateFormat("h:mm a", Locale.getDefault()).format(date);
    }

    /**
     * Converts a String in the form "2013-12-14T23:59:59.000Z" into a Date object.
     *
     * @param dateTimeWithTimeZoneString the date String
     * @return the equivalent Date object
     */
    public static Date dateWithTimeZoneFromString(String dateTimeWithTimeZoneString) {
        try {
            return getISOSimpleDateFormatDefaultZone().parse(dateTimeWithTimeZoneString);
        } catch (ParseException e) {
            Log.e(TAG, "Unable to parse date String : " + dateTimeWithTimeZoneString);
        }
        return null;
    }

    /**
     * Calculate the number of days between two dates. The calculation will ignore the
     * time of day.
     *
     * @param startDate the start Date
     * @param endDate   the end Date
     * @return the number of whole days between the two dates, ignoring time of day
     */
    public static int daysBetween(Date startDate, Date endDate) {

        // Normalize the start and end dates to hold only date-precision.

        final Calendar startCal = Calendar.getInstance(TimeZone.getDefault());
        startCal.setTime(startDate);
        DateUtils.clearTimeOfDay(startCal);

        final Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
        endCal.setTime(endDate);
        DateUtils.clearTimeOfDay(endCal);

        final long startTimeMillis = startCal.getTimeInMillis();
        final long endTimeMillis = endCal.getTimeInMillis();
        final long differenceMillis = endTimeMillis - startTimeMillis;

        return millisecondsToDays(differenceMillis);
    }

    /**
     * Clears all the time of day fields (i.e. hour, minute, second, millisecond) from the
     * Calendar.
     *
     * @param cal the Calendar to clear the time of day for
     */
    public static void clearTimeOfDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Converts milliseconds into whole days. All partial days will simply be truncated.
     *
     * @param millis the number of milliseconds to convert
     */
    public static int millisecondsToDays(long millis) {
        return (int) (millis / MILLISECONDS_PER_DAY);
    }

    public static long getTimeInSeconds(Date date) {
        return date.getTime() / MILLISECONDS_PER_SECOND;
    }

    public static String getFullUsDateString(Date date) {
        return new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US).format(date);
    }

    public static String getUSDateTimeString(Date date) {
        return new SimpleDateFormat("MMM d, yyyy '@' h:mm a", Locale.US).format(date);
    }

    public static String getHourString(Date date) {
        return new SimpleDateFormat("h", Locale.US).format(date);
    }

    public static int getHourOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Date addDays(Date originalDate, int days) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static String getDateWithDayInWeek(Date date) {
        if (date == null) {
            return null;
        } else {
            return new SimpleDateFormat("EEEE, MMMM dd 'at' hh:mm aaa", Locale.getDefault())
                    .format(date);
        }
    }

    public static String getDateTimeForPlanner(Date date) {
        if (date == null) {
            return null;
        }
        if (android.text.format.DateUtils.isToday(date.getTime())) {
            return HPApplication.getStringById(R.string.today_at) + new SimpleDateFormat(" hh:mm aaa", Locale.getDefault())
                    .format(date);
        } else if (android.text.format.DateUtils.isToday(date.getTime() + android.text.format.DateUtils.DAY_IN_MILLIS)) {
            return HPApplication.getStringById(R.string.yesterday_at) + new SimpleDateFormat(" hh:mm aaa", Locale.getDefault())
                    .format(date);
        } else if (android.text.format.DateUtils.isToday(date.getTime() - android.text.format.DateUtils.DAY_IN_MILLIS)) {
            return HPApplication.getStringById(R.string.tomorrow_at) + new SimpleDateFormat(" hh:mm aaa", Locale.getDefault())
                    .format(date);
        } else {
            return new SimpleDateFormat("EEE, MMM dd 'at' hh:mm aaa", Locale.getDefault())
                    .format(date);
        }
    }

    public static String getDateForPlanner(Date date) {
        if (date == null) {
            return null;
        }
        if (android.text.format.DateUtils.isToday(date.getTime())) {
            return HPApplication.getStringById(R.string.today);
        } else if (android.text.format.DateUtils.isToday(date.getTime() + android.text.format.DateUtils.DAY_IN_MILLIS)) {
            return HPApplication.getStringById(R.string.yesterday);
        } else if (android.text.format.DateUtils.isToday(date.getTime() - android.text.format.DateUtils.DAY_IN_MILLIS)) {
            return HPApplication.getStringById(R.string.tomorrow);
        } else {
            return new SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
                    .format(date);
        }
    }

    public static String getDateForPlannerEvent(Date date) {
        if (date == null) {
            return null;
        }
        if (android.text.format.DateUtils.isToday(date.getTime())) {
            return HPApplication.getStringById(R.string.today) + new SimpleDateFormat(" hh:mm aaa", Locale.getDefault())
                    .format(date);
        } else if (android.text.format.DateUtils.isToday(date.getTime() + android.text.format.DateUtils.DAY_IN_MILLIS)) {
            return HPApplication.getStringById(R.string.yesterday) + new SimpleDateFormat(" hh:mm aaa", Locale.getDefault())
                    .format(date);
        } else if (android.text.format.DateUtils.isToday(date.getTime() - android.text.format.DateUtils.DAY_IN_MILLIS)) {
            return HPApplication.getStringById(R.string.tomorrow) + new SimpleDateFormat(" hh:mm aaa", Locale.getDefault())
                    .format(date);
        } else {
            return new SimpleDateFormat("EEEE, MMMM dd hh:mm aaa", Locale.getDefault())
                    .format(date);
        }
    }

    public static String getHourForPlanner(Date date) {
        return new SimpleDateFormat("hh:mm aaa", Locale.getDefault())
                .format(date);
    }

    public static SimpleDateFormat getFormatForPlannerReminder() {
        return new SimpleDateFormat("HH:mm", Locale.getDefault());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getFileTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    public static long getFirstOfNextMonth() {
        final Calendar currentDate = new GregorianCalendar();
        final int currentYear = currentDate.get(Calendar.YEAR);
        final int currentMonth = currentDate.get(Calendar.MONTH);

        final Calendar firstOfNextMonth = new GregorianCalendar();
        final int resultYear;
        final int resultMonth;
        if (currentMonth == Calendar.DECEMBER) {
            resultYear = currentYear + 1;
            resultMonth = Calendar.JANUARY;
        } else {
            resultYear = currentYear;
            resultMonth = currentMonth + 1;
        }
        firstOfNextMonth.clear();
        firstOfNextMonth.set(resultYear, resultMonth, 1);
        return firstOfNextMonth.getTimeInMillis();
    }

    public static boolean isBetween(String timeStr1, String timeStr2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        try {
            Date time = dateFormat.parse(dateFormat.format(new Date()));
            Date time1 = dateFormat.parse(timeStr1);
            Date time2 = dateFormat.parse(timeStr2);

            return time1.before(time) && time2.after(time);
        } catch (ParseException e) {
            Log.e(TAG, "Unable to parse time string.");
            return true;
        }
    }

    public static boolean isWeekends() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    public static int getCurrentHour() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Getter for the singleton DateFormat instance representing the default locale and
     * the default style for that locale. If another style or locale is needed, then a new
     * {@link DateFormat} object should be instantiated and this method should not be
     * used.
     *
     * @return the singleton DateFormat instance
     */
    private static DateFormat getDefaultDateFormat() {
        final DateFormat format = DateFormat.getDateInstance();
        format.setTimeZone(TimeZone.getDefault());
        return format;
    }

    /**
     * Getter for the singleton SimpleDateFormat instance for formatting a date into a
     * full date and time String (e.g. "2013-12-14T23:59:59.000Z").
     *
     * @return the singleton SimpleDateFormat instance
     */
    private static SimpleDateFormat getISOSimpleDateFormatDefaultZone() {
        final SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getDefault());
        return format;
    }

    /**
     * @return String for displaying date/time duration with short days of week and time.
     */
    public static String getDuration(Date startsAt, Date endsAt) {
        final String endDatePattern;
        if (isSameDay(startsAt, endsAt)) {
            endDatePattern = "h:mm a";
        } else {
            endDatePattern = "EEE MMM d h:mm a";
        }
        return new SimpleDateFormat("EEE h:mm a", Locale.getDefault()).format(startsAt)
                + " - " + new SimpleDateFormat(endDatePattern, Locale.getDefault()).format(endsAt);
    }

    public static String getThreeCharacterMonth(Date date) {
        return new SimpleDateFormat("MMM", Locale.getDefault()).format(date);
    }

    public static int getDate(Date date) {
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    public static String getFileNameTimestampFromDate(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(date);
    }
    
    private DateUtils() {
    }

    public static String formatPublishDate(Date publishDate) {
        long current = System.currentTimeMillis() / 1000;
        long publish = publishDate.getTime() / 1000;
        long result = current - publish;
        if (result >= 14400) {
            return DateUtils.formatDate("MM-dd", publishDate);
        } else if (result >= 3600) {
            return result / 3600 + "小时前";
        } else if (result >= 60) {
            return result / 60 + "分钟前";
        } else {
            return "刚刚";
        }
    }

    public static String formatDate(String fmt, Date date) {
        fmt = !TextUtils.isEmpty(fmt) ? fmt : "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(fmt, Locale.CHINA);
        return format.format(date);
    }

}
