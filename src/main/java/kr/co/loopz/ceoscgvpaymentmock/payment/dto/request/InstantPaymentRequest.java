package kr.co.loopz.ceoscgvpaymentmock.payment.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.loopz.ceoscgvpaymentmock.payment.domain.enums.Currency;


public record InstantPaymentRequest(

        @NotBlank
        String storeId,
        @NotBlank
        String orderName,
        @NotNull(message = "결제 금액은 필수입니다")
        @Min(value = 1, message = "결제 금액은 1원 이상이어야 합니다")
        Integer totalPayAmount,
        @NotNull
        Currency currency,
        String customData
) {
}
