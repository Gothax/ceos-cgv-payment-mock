package kr.co.loopz.ceoscgvpaymentmock.payment.dto.response;

import kr.co.loopz.ceoscgvpaymentmock.payment.domain.Payment;
import kr.co.loopz.ceoscgvpaymentmock.payment.domain.enums.Currency;
import kr.co.loopz.ceoscgvpaymentmock.payment.domain.enums.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentResponse(
        String paymentId,
        PaymentStatus paymentStatus,
        String orderName,
        String pgProvider,
        Currency currency,
        String customData,
        LocalDateTime paidAt
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getStatus(),
                payment.getOrderName(),
                payment.getPgProvider(),
                payment.getCurrency(),
                payment.getCustomData(),
                payment.getCreatedAt()
        );
    }
}
