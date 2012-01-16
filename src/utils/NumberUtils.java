package utils;

import java.math.BigDecimal;

/**
 * @author amuract
 */
public class NumberUtils {
    
    /**
     * Given a String, you get the BigDecimal that represents 
     * @param number
     * @return 
     */
    public static BigDecimal getBigDecimal(String number) {
        if (number != null && !"".equals(number)) {
            return new BigDecimal(number);
        }
        return null;
    }
    
    /**
     * Given a String, you get the Long that represents
     * @param number
     * @return 
     */
    public static Long getLong(String number) {
        if (number != null && !"".equals(number)) {
            return new Long(number);
        }
        return null;
    }
}
