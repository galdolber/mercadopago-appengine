package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author amuract
 */
public class TimeUtils {
    
    /**
     * Given a string containing a date in ISO 8601 format, returns a DATE object that represents
     * @param date
     * @return 
     */
    public static Date getDate(String date) {
        if (date != null && !"".equals(date) && !"null".equals(date)) {
            DateTimeFormatter iso8601 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            return iso8601.parseDateTime(date).toDate();
        }
        return null;
    }
    
    /**
     * Given an object DATE, returns a String object representing the date in ISO 8601 format
     * @param date
     * @return 
     */
    public static String dateToString( Date date ) {
        if (date!=null){
            SimpleDateFormat iso8601 = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSZ" );
            return iso8601.format(date);
        }
        return "";
    }
    
    
}
