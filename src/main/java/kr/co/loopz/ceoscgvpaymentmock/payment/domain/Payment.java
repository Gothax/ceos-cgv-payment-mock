package kr.co.loopz.ceoscgvpaymentmock.payment.domain;

import jakarta.persistence.*;
import kr.co.loopz.ceoscgvpaymentmock.common.domain.BaseTimeEntity;
import kr.co.loopz.ceoscgvpaymentmock.payment.domain.enums.Currency;
import kr.co.loopz.ceoscgvpaymentmock.payment.domain.enums.PaymentStatus;
import kr.co.loopz.ceoscgvpaymentmock.payment.dto.request.InstantPaymentRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_payment_client_payment",
                        columnNames = {"clientId", "paymentId"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    private String paymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // PENDING, PAID, FAILED, CANCELLED

    private String orderName; // 주문명

    private String pgProvider; // PG사

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String customData;

    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }

    public static Payment createPayment(String clientId, String paymentId, InstantPaymentRequest request) {
        return Payment.builder()
                .status(PaymentStatus.PAID)

                .clientId(clientId)
                .paymentId(paymentId)
                .orderName(request.orderName())
                .currency(request.currency())
                .customData(request.customData())
                .build();
    }

    @Builder
    public Payment(String clientId, String paymentId, PaymentStatus status, String orderName, Currency currency, String customData) {

        this.pgProvider = "CEOS_PAY";

        this.clientId = clientId;
        this.paymentId = paymentId;
        this.status = status;
        this.orderName = orderName;
        this.currency = currency;
        this.customData = customData;
    }
}
