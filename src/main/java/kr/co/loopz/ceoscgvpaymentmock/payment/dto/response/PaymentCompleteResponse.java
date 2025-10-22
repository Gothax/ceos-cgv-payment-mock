package kr.co.loopz.ceoscgvpaymentmock.payment.dto.response;

import java.time.LocalDateTime;

public record PaymentCompleteResponse(
        String paymentId,
        LocalDateTime paidAt
) {
    public static PaymentCompleteResponse of(String paymentId, LocalDateTime createdAt) {
        return new PaymentCompleteResponse(paymentId, createdAt);
    }
}
