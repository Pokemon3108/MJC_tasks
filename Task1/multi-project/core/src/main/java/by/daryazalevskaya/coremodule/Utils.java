package by.daryazalevskaya.coremodule;

import by.daryazalevskaya.utils.StringUtils;

import java.util.Arrays;

public class Utils {
    public static boolean isAllPositiveNumbers(String... strArr) {
        return Arrays.stream(strArr).allMatch(StringUtils::isPositiveNumber);
    }
}
