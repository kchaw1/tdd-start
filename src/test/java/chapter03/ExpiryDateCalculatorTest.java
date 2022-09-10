package chapter03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 *  매달 비용을 지불해야 사용할 수 있는 유료 서비스
 *  납부한 금액을 기준으로 서비스 만료일을 계산하는기능을 TDD로 구현
 *  [규칙]
 *  1. 서비스를 사용하려면 매달 1만원을 선불로 납부한다. 납부일 기준으로 한 달 뒤가 서비스 만료일이 된다.
 *  2. 2개월 이상 요금을 납부할 수 있다.
 *  3. 10만원을 납부하면 서비스를 1년 제공한다.
 *
 */
public class ExpiryDateCalculatorTest {
    @Test
    void 만원_납부하면_한달_뒤가_만료일이_됨() {
        assertExpiryDate(
                LocalDate.of(2022, 9, 9),
                10000,
                LocalDate.of(2022,10,9));
        assertExpiryDate(
                LocalDate.of(2022, 9, 10),
                10000,
                LocalDate.of(2022,10,10));
    }

    private void assertExpiryDate(LocalDate billingDate, int payAmount, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(billingDate, payAmount);
        Assertions.assertEquals(expectedExpiryDate, realExpiryDate);
    }
}
