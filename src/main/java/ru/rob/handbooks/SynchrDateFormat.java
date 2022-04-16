package ru.rob.handbooks;

import org.joda.time.DateTime;
import org.joda.time.Days;
import ru.rob.handbooks.util.StringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: sporykhin
 * Date: 27.05.15
 * Time: 11:10
 */
public class SynchrDateFormat {

    private static final Locale LOCALE_RU = new Locale("ru");

    public static final String[] monthShortNames = new String[]{
            "янв",
            "фев",
            "мар",
            "апр",
            "май",
            "июн",
            "июл",
            "авг",
            "сен",
            "окт",
            "ноя",
            "дек"};

    public static final String[] monthNames = new String[]{
            "январь",
            "февраль",
            "март",
            "апрель",
            "май",
            "июнь",
            "июль",
            "август",
            "сентябрь",
            "октябрь",
            "ноябрь",
            "декабрь"};

    public static final String[] monthNamesWithDeclension = new String[]{
            "января",
            "февраля",
            "марта",
            "апреля",
            "мая",
            "июня",
            "июля",
            "августа",
            "сентября",
            "октября",
            "ноября",
            "декабря"};

    public static final String[] quarterNames = new String[]{
            "первый квартал",
            "второй квартал",
            "третий квартал",
            "четвертый квартал"};

    private static String doFormat0(Date date, String format) {
        if (date != null) {
            SimpleDateFormat fmt = new SimpleDateFormat(format);
            return fmt.format(date);
        } else {
            return null;
        }
    }

    private static String doFormat1(Date date, String format) {
        if (date != null) {
            SimpleDateFormat fmt = new SimpleDateFormat(format);
            fmt.setLenient(false);
            return fmt.format(date);
        } else {
            return null;
        }
    }

    private static Date doParse0(String s, String format) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return checkDateYear(fmt.parse(s));
    }

    private static Date doParse1(String s, String format) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setLenient(false);
        return checkDateYear(fmt.parse(s));
    }

    public static String format(Date date, String format) {
        return doFormat1(date, format);
    }

    public static String formatDate(Date date) {
        return doFormat1(date, "dd.MM.yyyy");
    }

    public static String formatDateShortYear(Date date) {
        return doFormat1(date, "dd.MM.yy");
    }

    public static String formatDateGorod(Date date) {
        return doFormat1(date, "dd/MM/yyyy");
    }

    public static String formatDayMonthYear(Date date) {
        return doFormat1(date, "ddMMyyyy");
    }

    public static String formatYearMonthDay(Date date) {
        return doFormat1(date, "ddMMyyyy");  // ОДIНАКОВЫЕ форматы с предыдущим ... офигеть (((
    }

    public static String formatPeriod(Date date) {
        return doFormat1(date, "MM.yyyy");
    }

    public static String formatTime(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        return date != null ? fmt.format(date) : null;
    }

    public static String formatMonthYear(Date date) {
        return date != null ? formatMonth(date).concat(" ").concat(formatYear(date)) : null;
    }

    public static String formatDateWithQuotes(Date date) {
        return date != null ?
                "\"" + formatDay(date) + "\" " + formatMonthWithDeclension(date).concat(" ").concat(formatYear(date)) : null;
    }

    public static String formatMonthYear(Date date, String separator) {
        return date != null ? formatMonth(date).concat(separator).concat(" ").concat(formatYear(date)) : null;
    }

    public static String formatMonth(Date date) {
        if (date == null) return null;
        String month = monthNames[getMonth(date) - 1];
        return date != null ? month.substring(0, 1).toUpperCase().concat(month.substring(1)) : null;
    }

    public static String formatMonthWithDeclension(Date date) {
        if (date == null) return null;
        String month = monthNamesWithDeclension[getMonth(date) - 1];
        return date != null ? month.substring(0, 1).toUpperCase().concat(month.substring(1)) : null;
    }

    public static String formatDateXML(Date date) {
        return doFormat1(date, "yyyy-MM-dd");
    }

    public static String formatDateTimeXML(Date date) {
        return doFormat0(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parsePeriod(String s) throws ParseException {
        return doParse1(s, "MM.yyyy");
    }
    public static Date parsePeriodWithNull(String s, String exceptionMsg) throws Exception {
        if (s == null) return null;
        try {
            return doParse1(s, "MM.yyyy");
        } catch (Exception e) {
            throw new Exception(exceptionMsg);
        }
    }

    public static Date parseDateNotThrows(String s){
        try {
            return doParse1(s, "dd.MM.yyyy");
        } catch (ParseException e){
            return null;
        }
    }

    public static Date parseDate(String s) throws ParseException {
        return doParse1(s, "dd.MM.yyyy");
    }

    /**
     * Распознование периода по наименованию месяца и года
     */
    public static Date parsePeriod(String month, String year) throws ParseException {
        if (StringUtils.isEmpty(month) || StringUtils.isEmpty(year)) return null;
        List<String> monthList = Arrays.asList(monthNames);
        Integer monthNum = monthList.indexOf(month.toLowerCase()) + 1;
        return parsePeriod(monthNum.toString().concat(".").concat(year));
    }

    public static Date parsePeriodMultiple(String periodStr) throws ParseException {
        Date period = null;
        try {
            period = doParse1(periodStr, "MM yyyy");
        } catch (ParseException e) {
            try {
                period = doParse1(periodStr, "MM.yyyy");
            } catch (ParseException e1) {
                try {
                    period = doParse1(periodStr, "MM-yyyy");
                } catch (ParseException e2) {

                }
            }
        }
        return period;
    }

    public static Date parseDate(String str, String format) throws ParseException {
        return doParse1(str, format);
    }

    public static Date parseXmlDate(String s) throws ParseException {
        return doParse1(s, "yyyy-MM-dd");
    }

    public static Date parseDateWithNull(String date, String exceptionMsg) throws Exception {
        if (StringUtils.isEmpty(date)) return null;

        return parseDate(date, new Exception(exceptionMsg));
    }

    public static Date parseDate(String s, Exception exception) throws Exception {
        if (StringUtils.isEmpty(s)) {
            throw exception;
        }
        try {
            return doParse1(s, "dd.MM.yyyy");
        } catch (ParseException pe) {
            throw exception;
        }
    }

    public static Date parseDate(String s, Date def) throws Exception {
        if (StringUtils.isEmpty(s)) {
            return def;
        }
        try {
            return doParse1(s, "dd.MM.yyyy");
        } catch (ParseException pe) {
            return def;
        }
    }

    public static Date parseDate(String dateString, boolean endOfDay) throws ParseException {

        Date date = SynchrDateFormat.parseDate(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (endOfDay) {
            calendar.add(Calendar.DATE, 1);
            calendar.add(Calendar.MILLISECOND, -1);
        }
        return calendar.getTime();
    }

    public static String formatYear(Date date) {
        return doFormat0(date, "yyyy");
    }
    public static String formatShortYear(Date date) {
        return doFormat0(date, "yy");
    }

    public static String formatDay(Date date) {
        return doFormat0(date, "dd");
    }

    /**
     * Формат даты в формат MMYYYY без разделителя
     *
     * @param date
     * @return
     */
    public static String formatMMYY(Date date) {
        return doFormat1(date, "MMyy");
    }

    public static Date parseYear(String s) throws ParseException {
        return doParse0(s, "yyyy");
    }

    public static String formatTimeMinute(Date date) {
        return doFormat0(date, "HH:mm");
    }

    private static String formatMillisecond(int i) {
        return (i < 10 ? "00" : i < 100 ? "0" : "") + i;
    }

    private static int parseMillisecond(String s) throws NumberFormatException, ParseException {
        int l = s == null ? 0 : s.length();
        if (l == 0 || l >= 4)
            throw new ParseException(s, l == 0 ? 0 : 3);
        int i = Integer.parseInt(s);
        return (l == 1 ? 100 : l == 2 ? 10 : 1) * i;
    }

    public static Date parseDateTime(String s) throws ParseException {
        return doParse0(s, "dd.MM.yyyy HH:mm:ss");
    }

    public static Date parseDateTimeMillisecond(String s) throws ParseException {
        try {
            return doParse0(s, "dd.MM.yyyy HH:mm:ss,SSS");
        } catch (ParseException e) {
            try {
                return doParse0(s, "dd.MM.yyyy HH:mm:ss SSS");
            } catch (ParseException e1) {
                return doParse0(s, "dd.MM.yyyy HH:mm:ss.SSS");
            }
        }

    }

    public static Date parseDateTimeFloat(String s) throws ParseException {
        try {
            return doParse0(s, "dd.MM.yyyy HH:mm:ss");
        } catch (ParseException e) {
            try {
                return doParse0(s, "dd.MM.yyyy HH:mm");
            } catch (ParseException e1) {
                try {
                    return doParse0(s, "dd.MM.yyyy HH");
                } catch (ParseException e2) {
                    return doParse0(s, "dd.MM.yyyy");
                }
            }
        }
    }

    public static Date parseTimestamp(String s) throws NumberFormatException, ParseException {
        int i = s.indexOf(',');

        if (i > 0) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(doParse0(s.substring(0, i), "dd.MM.yyyy HH:mm:ss"));
            gc.add(Calendar.MILLISECOND, parseMillisecond(s.substring(i + 1)));
            return gc.getTime();
        } else {
            return doParse0(s, "dd.MM.yyyy HH:mm:ss");
        }
    }

    public static Date parseMonthYear(String s) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("MMMMM yyyy", LOCALE_RU);
        return fmt.parse(s);
    }

    public static Date parseGorodPeriod(String s) throws ParseException {
        return doParse0(s, "MM/yyyy");
    }

    public static Date parseGorodPeriodNoDivider(String s) throws ParseException {
        return doParse1(s, "MMyyyy");
    }

    public static Date parseGorodPeriodNoDividerSmall(String s) throws ParseException {
        return doParse1(s, "MMyy");
    }

    public static Date parseGorodDate(String s) throws ParseException {
        return doParse1(s, "dd/MM/yyyy");
    }

    public static Date parseVologdaDate(String s) throws ParseException {
        return doParse1(s.replaceAll("\\.", "").replaceAll("/", ""), "ddMMyyyy");
    }

    public static Date parseDbfDate(String s) throws ParseException {
        return doParse1(s, "yyyyMMdd");
    }

    public static Date parseGorodTimestamp(String s) throws ParseException {
        return doParse0(s, "dd/MM/yyyy HH:mm:ss");
    }

    public static Date parseDateNoDivider(String s) throws ParseException {
        return doParse1(s, "ddMMyyyy");
    }

    public static String formatDateTime(Date d) {
        return doFormat0(d, "dd.MM.yyyy HH:mm:ss");
    }

    public static String formatDateTimeMillisecond(Date d) {
        return doFormat0(d, "dd.MM.yyyy HH:mm:ss.SSS");
    }

    public static String formatDateTimeMillisecondSpace(Date d) {
        return doFormat0(d, "dd.MM.yyyy HH:mm:ss SSS");
    }

    public static String formatDateSmall(Date d) {
        return doFormat1(d, "MM.yy");
    }

    public static String formatDateDayMonth(Date d) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMMM", LOCALE_RU);
        return d != null ? fmt.format(d) : null;
    }

    public static String formatTimestamp(Date d) {
        if (d == null)
            return null;
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(d);

        String res = doFormat0(d, "dd.MM.yyyy HH:mm:ss") + ","
                + formatMillisecond(c.get(Calendar.MILLISECOND));

        return res;
    }

    public static String formatDateTimeMinute(Date d) {
        return doFormat0(d, "dd.MM.yyyy HH:mm");
    }

    public static String formatTSMarker(Date d) {
        Date dt = d != null ? d : new Date();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(dt);

        String res = doFormat0(dt, "yyyy")
                + formatMillisecond(c.get(Calendar.MILLISECOND));

        return res;
    }

    //ok

    public static String formatGorodPeriod(Date d) {
        return doFormat0(d, "MM/yyyy");
    }

    /**
     * Создается java.util.Calendar на основе объекта java.sql.Time
     *
     * @param time Объект типа java.sql.Time
     * @return java.util.Calendar
     */
    public static Calendar getCalendarFromTime(Time time) {
        Calendar cal = GregorianCalendar.getInstance(LOCALE_RU);
        cal.setTimeInMillis(time.getTime());
        return cal;
    }

    /**
     * Возвращает дату по году.
     * Наприме, для 2005 вернет 01.01.2005 00:00:00
     *
     * @param year
     * @return
     */
    public static Date getYearAsDate(Integer year) {
        Calendar cal = GregorianCalendar.getInstance(LOCALE_RU);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 0);
        return SynchrDateFormat.getStartAndEndOfMonth(cal.getTime())[0];
    }

    /**
     * Создается java.util.Calendar на основе объекта java.sql.Date
     *
     * @param timestamp Объект типа java.sql.Date
     * @return java.util.Calendar
     */
    public static Calendar getCalendarFromTimestamp(Timestamp timestamp) {
        Calendar cal = GregorianCalendar.getInstance(LOCALE_RU);
        cal.setTimeInMillis(timestamp.getTime());
        return cal;
    }

    /**
     * Функция, кт удаляет часы, минуты, секунды и миллисекунды из даты
     *
     * @param date - дата для форматирования
     */
    @Deprecated/* лучше не использовать т.к. функция изменяет входящий объект */
    public static void removeTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        removeTime(c);
        date.setTime(c.getTimeInMillis());
    }

    /**
     * Функция, кт удаляет часы, минуты, секунды и миллисекунды из даты
     *
     * @param date - дата для форматирования
     * @return новую дату
     */
    public static Date removeTimeFromDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        removeTime(c);
        return new Date(c.getTimeInMillis());
    }

    public static void removeTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    public static Date truncateDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        removeTime(c);
        return new Date(c.getTimeInMillis());
    }

    public static Date truncateDateToMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        removeTime(c);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(c.getTimeInMillis());
    }

    public static Date truncateDateToYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        removeTime(c);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MONTH, 0);
        return new Date(c.getTimeInMillis());
    }

    public static Date setDay(Date date, int day) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, day);

        return c.getTime();
    }

    public static Date setYear(Date date, int year) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.YEAR, year);

        return c.getTime();
    }

    /**
     * Возвращает даты начала и конца месяца для заданной даты
     *
     * @param date - дата для которой берется начало и конец
     * @return - массив из двух дат Date[0] - начало месяца, Date[1] - конец месяца.
     * Даты округлены до дня.
     */
    public static Date setDayTrunc(Date date, int day) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, day);

        return removeTimeFromDate(c.getTime());
    }

    public static Date setMonthAndDay(Date date, int month, int day) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        return c.getTime();
    }

    public static Date addYears(Date date, int n) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(GregorianCalendar.YEAR, n);

        return c.getTime();
    }

    public static Date addMonths(Date date, int n) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(GregorianCalendar.MONTH, n);

        return c.getTime();
    }

    public static Date addDays(Date date, int n) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(GregorianCalendar.DAY_OF_MONTH, n);

        return c.getTime();
    }

    public static Date addHours(Date date, int hours) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(GregorianCalendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

    public static Date addMinutes(Date date, int minutes) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(GregorianCalendar.MINUTE, minutes);
        return c.getTime();
    }

    public static Date addSeconds(Date date, int seconds) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(GregorianCalendar.SECOND, seconds);
        return c.getTime();
    }

    public static Date addMilliseconds(Date date, int seconds) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(GregorianCalendar.MILLISECOND, seconds);
        return c.getTime();
    }

    public static void endOfDay(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
    }

    public static Date endOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        endOfDay(c);
        return new Date(c.getTimeInMillis());
    }

    /**
     * Возвращает БУХГАЛТЕРСКIЙ (с 15 по 15 число) период заданной даты
     *
     * @param date дата
     * @return период, в виде первого дня месяца
     */
    @Deprecated
    public static Date getPeriodByDate(Date date) {
        Calendar c = Calendar.getInstance();
        Date result;
        Date md;

        c.setTime(date);
        endOfDay(c);
        c.set(Calendar.DAY_OF_MONTH, 15);

        md = new Date(c.getTimeInMillis());

        if (date.getTime() <= md.getTime()) {
            removeTime(c);
            c.set(Calendar.DAY_OF_MONTH, 1);
            result = new Date(c.getTimeInMillis());
        } else {
            removeTime(c);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.MONTH, 1);
            result = new Date(c.getTimeInMillis());
        }
        return result;
    }

    public static Date getPeriodFd(Date period) {
        Calendar c = Calendar.getInstance();
        c.setTime(period);

        c.set(Calendar.DAY_OF_MONTH, 15);
        endOfDay(c);

        return new Date(c.getTimeInMillis());
    }

    public static Date getPeriodSd(Date period) {
        Calendar c = Calendar.getInstance();
        c.setTime(period);

        c.set(Calendar.DAY_OF_MONTH, 16);
        c.add(Calendar.MONTH, -1);

        removeTime(c);

        return new Date(c.getTimeInMillis());
    }

    public static Date getPeriodOperDate(Date period) {
        Calendar c = Calendar.getInstance();
        c.setTime(period);

        c.set(Calendar.DAY_OF_MONTH, 15);

        removeTime(c);

        return new Date(c.getTimeInMillis());
    }


    /**
     * Возвращает даты начала и конца месяца для заданной даты
     *
     * @param date - дата для которой берется начало и конец
     * @return - массив из двух дат Date[0] - начало месяца, Date[1] - конец месяца.
     * Даты округлены до дня.
     */
    public static Date[] getStartAndEndOfMonth(Date date) {
        Calendar start = Calendar.getInstance(),
                end = Calendar.getInstance();
        start.setTime(date);
        end.setTime(date);
        start.set(Calendar.DAY_OF_MONTH, 1);
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        removeTime(start);
        removeTime(end);
        return new Date[]{start.getTime(), end.getTime()};
    }

    public static Date calcFdByNextSd(Date nextSd) {
        Date val;

        if (nextSd != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(nextSd);
            c.add(Calendar.DATE, -1);
            val = c.getTime();
        } else {
            val = GKHConstants.DATE_INFINITY_AS_DATE;
        }
        return val;
    }

    public static Date calcSdByPrevFd(Date prevFd) {
        Date val;

        if (prevFd != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(prevFd);
            c.add(Calendar.DATE, 1);
            val = c.getTime();
        } else {
            throw new IllegalArgumentException("prevFd is null");
        }
        return val;
    }

    public static void main(String[] args) {
        Date date = new Date();

        date = getPeriodByDate(date);
        Date fd = getPeriodFd(date);
        Date sd = getPeriodSd(date);

        System.out.println();
    }

    /**
     * Возвращает число дней между двумя датами
     *
     * @param first  - первая дата (должна быть раньше на временной шкале)
     * @param second - вторая дата
     * @return число дней между датами
     */
    public static long daysBetween(Date first, Date second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("dates must be not null");
        }

        if (first.after(second)) {
            throw new IllegalArgumentException("first date argument must be before second");
        }
        first = truncateDate(first);
        second = truncateDate(second);
        long dayInMillis = TimeUnit.DAYS.toMillis(1);
        return (second.getTime() / dayInMillis) - (first.getTime() / dayInMillis);
    }

    public static Date least(Date date, Date... dates) {
        List<Date> list = new ArrayList(1 + dates.length);
        list.add(date);
        list.addAll(Arrays.asList(dates));
        Collections.sort(list);
        return list.get(0);
    }

    public static final Date greatest(Date date, Date... dates) {
        List<Date> list = new ArrayList(1 + dates.length);
        list.add(date);
        list.addAll(Arrays.asList(dates));
        Collections.sort(list);
        return list.get(list.size() - 1);
    }

    public static final int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static final int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static final int getMonthCountBetweenDates(Date date1, Date date2) {
        if (date1.after(date2)) return 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        c.setTime(date2);
        int year2 = c.get(Calendar.YEAR);
        int month2 = c.get(Calendar.MONTH);

        return (year2 - year1 - 1) * 12 + (12 - month1 + month2 - 1);
    }

    public static final int getMonthCountBetweenDates2(Date date1, Date date2) {
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        c.setTime(date2);
        int year2 = c.get(Calendar.YEAR);
        int month2 = c.get(Calendar.MONTH);

        return 12 * year2 + month2 - 12 * year1 - month1;
    }

    public static final short getYear(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return (short) c.get(Calendar.YEAR);
    }

    public static final Date parseDateMultiFormat(String s) throws ParseException {
        Date date = null;
        try {
            date = SynchrDateFormat.parseGorodTimestamp(s);
        } catch (ParseException e) {
            try {
                date = SynchrDateFormat.parseGorodDate(s);
            } catch (ParseException e1) {
                try {
                    date = parseDateNoDivider(s);
                } catch (ParseException e2) {
                    try {
                        date = SynchrDateFormat.parseDate(s);
                    } catch (ParseException e3) {
                        throw new ParseException("невозможно обработать поле Дата формирования реестра!", 0);
                    }
                }
            }
        }

        return date;
    }

    /**
     * Проверка даты на предмет некорректного года (например 0017)
     * Если дата меньше даты начала в системе (01.01.1900) и год меньше 100
     * считаем что это 2к+ год
     * */
    private static Date checkDateYear(Date dt) throws ParseException {
        if (dt == null || GKHConstants.DATE_BEGINNING_AS_DATE == null) return dt;

        Calendar c1 = Calendar.getInstance();
        c1.setTime(dt);
        Date newDate = c1.getTime();

        if (dt.before(GKHConstants.DATE_BEGINNING_AS_DATE)) {
            Short year = getYear(dt);
            if (year < 100) {
                newDate = setYear(newDate, 2000 + year);
            }
        }

        return newDate;
    }

    public static final boolean equalsDates(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);


        return c1.equals(c2);
    }

    public static final boolean dateBetween(Date date, Date from, Date to) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(from);
        Calendar c3 = Calendar.getInstance();
        c2.setTime(to);

        return (c1.after(c2) || c1.equals(c2)) && (c1.equals(c3) || c1.before(c3));
    }

    /**
     * Возвращает дату и время конца дня (например 20.01.2016 23:59:59)
     */
    public static final Date getDayEnd(Date dt) {
        Date dtNew = removeTimeFromDate(dt);
        dtNew = addDays(dtNew, 1);
        return addMilliseconds(dtNew, -1);
    }

    /**
     * Преобразование даты для JAXB технологии.
     *
     * @param date
     * @return
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar getXmlGrogerianCalendarFromDate(Date date) throws DatatypeConfigurationException {

        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();

        gc.setTime(date);

        XMLGregorianCalendar xmlGrogerianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);

        return xmlGrogerianCalendar;
    }

    /**
     * Кол-во годов между двумя датами
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Integer getYearsBetween(Date date1, Date date2) {
        if (date1 == null || date2 == null) return 0;
        Date subDate = new Date(date1.getTime() - date2.getTime());
        Short subYear = SynchrDateFormat.getYear(subDate);
        return subYear - SynchrDateFormat.getYear(new Date(0L));
    }

    public static Date parseReestrPayDate(String payDateStr) throws ParseException {
        payDateStr = payDateStr.replaceAll("\\.", "/");
        if (payDateStr.length() == 8) {
            payDateStr = payDateStr.substring(0, 2) + "/" + payDateStr.substring(2, 4) + "/" + payDateStr.substring(4, 8);
        }
        return SynchrDateFormat.parseGorodDate(payDateStr);
    }

    public static boolean inRange(Date date, Date from, Date to) throws Exception {
        if (date == null || from == null || to == null) return false;

        return (from.equals(date) || date.after(from)) && (to.equals(date) || date.before(to));
    }

    /**
     * Возвращает период по номеру месяца, в зависимости от текущего месяца
     * если переданные месяц меньше текущего, он определяется как прошлый год, иначе как текущий
     * Например если сейчас 01.2017, а пришел 12, вернется 12.2016, а если пришет 1 то 01.2017
     */
    public static Date getPeriodByMonth(int month) throws Exception {
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int currMonth = cal.get(Calendar.MONTH);
        cal.set(Calendar.MONTH, month - 1);
        if (month - 1 > currMonth) cal.add(Calendar.YEAR, -1);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }


    /**
     * Разбираем назначение в строке банковской выписки и ищем различные варианты написания месяца и года
     * Если нашли возвращаем первое число найденного месяца, если нет, то первое число предыдущего месяцы от defValue
     * Варианты описаны в DS-20053
     */
    public static Date parsePeriodFromName(String name, Date defValue) {
        if (StringUtils.isEmpty(name)) {
            return defValue;
        }
        List<String> allMatches = new ArrayList<String>();
        String monthPattern = "(за[\\d]{2}|янва?р?ь?|февр?а?л?ь?|март?|апре?л?ь?|май|июнь?|июль?|авгу?с?т?|сент?я?б?р?ь?|октя?б?р?ь?|нояб?р?ь?|дека?б?р?ь?)";
        Matcher m = Pattern.compile("[^\\d]" + monthPattern + "([\\d]{4}|[\\d]{2})[^\\d]")
                .matcher(name.toLowerCase().replaceAll("(-|\\.|\\\\|/|,| )", ""));
        while (m.find()) {
            allMatches.add(m.group());
        }

        Date res = null;
        try {
            for (String matchStr : allMatches) {
                matchStr = matchStr.substring(1, matchStr.length() - 1);
                String parseDateStr = null;
                Date parseDate = null;
                if (matchStr.startsWith("за")) {
                    parseDateStr = matchStr.substring(2, matchStr.length());
                    SimpleDateFormat format = new SimpleDateFormat("MMyyyy");
                    res = format.parse(parseDateStr);
                } else {
                    String month = matchStr.substring(0, 3);
                    String year = matchStr.replaceAll("\\D+", "");
                    if (year.length() > 2) {
                        SimpleDateFormat format = new SimpleDateFormat("MMMyyyy", LOCALE_RU);
                        parseDateStr = month + year;
                        parseDate = format.parse(parseDateStr);
                    } else {
                        SimpleDateFormat format = new SimpleDateFormat("MMMyy", LOCALE_RU);
                        parseDateStr = month + year;
                        parseDate = format.parse(parseDateStr);
                    }
                }
                if (res == null || (parseDate != null && parseDate.compareTo(res) < 0)) {
                    res = parseDate;
                }

            }
        } catch (ParseException e) {
            //считаем что не распознали
        }

        // Если период нашелся, но он больше чем defValue - возвращаем null
        if(res != null){
            res = truncateDateToMonth(res);

            return (res.compareTo(defValue) > 0) ? null : res;
        }else{
            return defValue;
        }
    }

    public static Date truncateDateToLastMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        removeTime(c);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, -1);
        return new Date(c.getTimeInMillis());
    }

    public static String getDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String result = "000" + c.get(Calendar.DAY_OF_YEAR);
        return result.substring(result.length() - 3);
    }


    public static long getDaysInMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)/3 * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getLastWorkDayInMonth(Date date){

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // на всякий случай ставим последний день месяца
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.setFirstDayOfWeek(Calendar.MONDAY); // Ставим первый день в неделе Понедельник

        int dw = c.get(Calendar.DAY_OF_WEEK);

        if(dw == 6){
            //c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 2);
            c.roll(Calendar.DAY_OF_MONTH, -2);
        }else if(dw == 7){
            //c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
            c.roll(Calendar.DAY_OF_MONTH, -1);
        }

        return c.getTime();

    }

    /**
     * Возвращает список из указанного количество дат по указанному диапазону с примерно равными интервалами.
     * Если в диапазоне дат меньше чем запрошено, вернется список из дат между исходным диапазоном.
     */
    public static List<Date> getDatesWithEqualRangeByPeriod(Date startPeriod, Date endPeriod, int datesCount) throws Exception {
        int days = Days.daysBetween(new DateTime(startPeriod), new DateTime(endPeriod)).getDays() + 1;

        List<Date> list = new ArrayList<>();
        if (days <= datesCount) {
            Date dt = new Date(startPeriod.getTime());
            list.add(dt);
            for (int i = 1; i < days ; i++) {
                dt = addDays(dt, 1);
                list.add(dt);
            }
        } else {
            int diff = days / (datesCount -1);
            Date dt = new Date(startPeriod.getTime());
            list.add(dt);
            for (int i = 1; i < (datesCount - 1); i++) {
                dt = addDays(dt, diff);
                if (dt.compareTo(endPeriod) > 0) {
                    break;
                }
                list.add(dt);
            }
            list.add(endPeriod);
        }

        return list;
    }

    public static List<Date> getPeriodListBetween(Date startPeriod, Date endPeriod) throws Exception {

        startPeriod = startPeriod == null ? endPeriod : startPeriod;
        endPeriod = endPeriod == null ? startPeriod : endPeriod;
        if(startPeriod == null && endPeriod == null)
            return null;

        if(startPeriod.getTime() > endPeriod.getTime())
            throw new Exception("Начальная дата не может быть позже конечной!");
        List<Date> result = new LinkedList<>();
        Date period = startPeriod;
        while (period.getTime() <= endPeriod.getTime()){
            result.add(period);
            period = addMonths(period, 1);
        }
        return result;
    }
}
