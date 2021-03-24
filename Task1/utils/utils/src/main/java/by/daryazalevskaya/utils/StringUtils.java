
package by.daryazalevskaya.utils;

import by.daryazalevskaya.utils.exception.NotNumberException;

public class StringUtils {
    public static boolean isPositiveNumber(String str) {
        boolean isNumber = org.apache.commons.lang3.StringUtils.isNumeric(str);
        char firstSymbol = str.charAt(0);
        if (firstSymbol == '-' && org.apache.commons.lang3.StringUtils.isNumeric(str.substring(1))
                || (firstSymbol == '0' && str.length() == 1)) {
            return false;
        } else if (isNumber) {
            return true;
        }
        throw new NotNumberException("Input string is not a number!");

    }
}
