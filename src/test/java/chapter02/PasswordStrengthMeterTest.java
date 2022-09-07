package chapter02;

import org.junit.jupiter.api.Test;

import static chapter02.PasswordStrength.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 암호검사기
 * 1. 길이가 8글자 이상
 * 2. 0부터 9사이의 숫자를 포함
 * 3. 대문자 포함
 * -----------------------
 * 3개 만족 -> 강함
 * 2개 만족 -> 보통
 * 1개이하 만족 -> 약함
 */
public class PasswordStrengthMeterTest {

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String password, PasswordStrength expStr) {
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr, result);
    }
    @Test
    void meterSelf() {
        PasswordStrength strength = MyPasswordStrengthMeter.validate("password");

        assertEquals(WEAK, strength);
        assertEquals(NORMAL, MyPasswordStrengthMeter.validate("password123"));
        assertEquals(STRONG, MyPasswordStrengthMeter.validate("Password123"));
    }

    @Test
    void meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", STRONG);
        assertStrength("abc1!Add", STRONG);
    }

    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal() {
        assertStrength("ab12!@A", NORMAL);
        assertStrength("Ab12!c", NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_number_Then_Normal() {
        assertStrength("ab!@ABqwer", NORMAL);
    }

    @Test
    void nullInput_Then_Invalid() {
        assertStrength(null, INVALID);
    }

    @Test
    void emptyInput_Then_Invalid() {
        assertStrength("", INVALID);
    }

    @Test
    void meetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        assertStrength("ab12!@df", NORMAL);
    }
}
