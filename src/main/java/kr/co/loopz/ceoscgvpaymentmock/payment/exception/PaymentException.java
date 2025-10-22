package kr.co.loopz.ceoscgvpaymentmock.payment.exception;

import kr.co.loopz.ceoscgvpaymentmock.common.exception.CustomException;
import kr.co.loopz.ceoscgvpaymentmock.common.exception.ErrorCode;

public class PaymentException extends CustomException {
    public PaymentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PaymentException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
