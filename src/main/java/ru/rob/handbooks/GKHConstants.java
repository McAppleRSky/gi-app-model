package ru.rob.handbooks;

import java.text.ParseException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GKHConstants {

    private static final Logger LOG = LoggerFactory.getLogger(GKHConstants.class);

    public final static String DATE_INFINITY = "01.01.5000"; // beskonechno daleko
    public final static Date DATE_INFINITY_AS_DATE;
    public final static String DATE_BEGINNING = "01.01.1900"; // davnym davno
    public final static Date DATE_BEGINNING_AS_DATE;
    static {
        Date d = null;
        try {
            d = SynchrDateFormat.parseDate(DATE_INFINITY);
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        DATE_INFINITY_AS_DATE = d;

        Date b = null;
        try {
            b = SynchrDateFormat.parseDate(DATE_BEGINNING);
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        DATE_BEGINNING_AS_DATE = b;
    }

}
