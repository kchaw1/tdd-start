package chapter03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    private final PayData payData;

    public ExpiryDateCalculator(PayData payData) {
        if (payData == null) throw new IllegalArgumentException("payData가 존재하지 않습니다.");
        this.payData = payData;
    }

    public LocalDate calculateExpiryDate() {
        final int payAmount = payData.getPayAmount();
        final int BONUS_PAY_AMOUNT = 100000;
        final int DEFAULT_PAY_AMOUNT = 10000;
        final int TOTAL_MONTH_OF_YEAR = 12;

        int addedMonths = payAmount == BONUS_PAY_AMOUNT ? TOTAL_MONTH_OF_YEAR : payAmount / DEFAULT_PAY_AMOUNT;
        if (payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBilling(addedMonths);
        }
        return payData.getBillingDate().plusMonths(addedMonths);
    }

    private LocalDate expiryDateUsingFirstBilling(int addedMonths) {
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);
        if (isNotSameDayOfMonth(payData.getFirstBillingDate(), candidateExp)) {
            return expiryDateCompareDayOfCandideExp(candidateExp);
        }
        return candidateExp;
    }

    private LocalDate expiryDateCompareDayOfCandideExp(LocalDate candidateExp) {
        final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
        final int dayLenOfCandiMon = lastDayOfMonth(candidateExp);
        if (dayLenOfCandiMon < dayOfFirstBilling) {
            return candidateExp.withDayOfMonth(dayLenOfCandiMon);
        }
        return candidateExp.withDayOfMonth(dayOfFirstBilling);
    }

    private boolean isNotSameDayOfMonth(LocalDate date1, LocalDate date2) {
        return date1.getDayOfMonth() != date2.getDayOfMonth();
    }

    private int lastDayOfMonth(LocalDate date) {
        return YearMonth.from(date).lengthOfMonth();
    }
}
