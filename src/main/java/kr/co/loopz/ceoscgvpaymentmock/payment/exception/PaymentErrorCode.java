package kr.co.loopz.ceoscgvpaymentmock.payment.exception;

import kr.co.loopz.ceoscgvpaymentmock.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),
    PAYMENT_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 결제 정보입니다."),
    INVALID_PAYMENT_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 결제 요청입니다."),
    PAYMENT_PROCESSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "결제 처리에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
