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
            PayData.builder()
                    .billingDate(LocalDate.of(2019, 3, 1))
                    .payAmount(10000)
                    .build(),
            LocalDate.of(2019,4,1));
        assertExpiryDate(
            PayData.builder()
                    .billingDate(LocalDate.of(2019, 5, 5))
                    .payAmount(10000)
                    .build(),
            LocalDate.of(2019,6,5));
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음() {
        assertExpiryDate(
            PayData.builder()
                    .billingDate(LocalDate.of(2019, 1, 31))
                    .payAmount(10000)
                    .build(),
            LocalDate.of(2019,2,28));
        assertExpiryDate(
            PayData.builder()
                    .billingDate(LocalDate.of(2019, 5, 31))
                    .payAmount(10000)
                    .build(),
            LocalDate.of(2019,6,30));
        assertExpiryDate(
            PayData.builder()
                    .billingDate(LocalDate.of(2024, 1, 31))
                    .payAmount(10000)
                    .build(),
            LocalDate.of(2024,2,29));
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_만원_납부() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 31))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(10000)
                .build();

        assertExpiryDate(payData, LocalDate.of(2019, 3, 31));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 30))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(10000)
                .build();

        assertExpiryDate(payData2, LocalDate.of(2019, 3, 30));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 5, 31))
                .billingDate(LocalDate.of(2019, 6, 30))
                .payAmount(10000)
                .build();

        assertExpiryDate(payData3, LocalDate.of(2019, 7, 31));
    }

    @Test
    void 이만원_이상_납부하면_비례해서_만료일_계산() {
        assertExpiryDate(PayData.builder()
                .payAmount(20000)
                .billingDate(LocalDate.of(2019, 3, 1))
                .build(),
                LocalDate.of(2019,5,1));

        assertExpiryDate(PayData.builder()
                .payAmount(30000)
                .billingDate(LocalDate.of(2019, 3, 1))
                .build(),
                LocalDate.of(2019,6,1));
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부 () {
        assertExpiryDate(PayData.builder()
                .firstBillingDate(LocalDate.of(2019,1,31))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(20000)
                .build(),
                LocalDate.of(2019,4,30));

        assertExpiryDate(PayData.builder()
                .firstBillingDate(LocalDate.of(2019,1,31))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(40000)
                .build(),
                LocalDate.of(2019,6,30));

        assertExpiryDate(PayData.builder()
                .firstBillingDate(LocalDate.of(2019,1,31))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(30000)
                .build(),
                LocalDate.of(2019,5,31));
    }

    @Test
    void 십만원을_납부하면_1년_제공() {
        assertExpiryDate(PayData.builder()
                .billingDate(LocalDate.of(2019,1,28))
                .payAmount(100000)
                .build(),
                LocalDate.of(2020,1,28));
    }

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);
        Assertions.assertEquals(expectedExpiryDate, realExpiryDate);
    }

}
