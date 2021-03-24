
package by.daryazalevskaya.utils;
import by.daryazalevskaya.utils.exception.NotNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"1", "9999999999999999999"})
    void isPositiveTrue(String str) {
        assertTrue(StringUtils.isPositiveNumber(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "-9999999999999999999", "0"})
    void isPositiveFalse(String str) {
        assertFalse(StringUtils.isPositiveNumber(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-h", "-1h", "-", "jojoj123", "+3"})
    void isPositiveNotNumber(String str) {
        Assertions.assertThrows(NotNumberException.class, ()->StringUtils.isPositiveNumber(str));
    }

}
