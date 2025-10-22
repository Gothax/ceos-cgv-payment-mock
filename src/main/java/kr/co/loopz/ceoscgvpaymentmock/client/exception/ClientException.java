package kr.co.loopz.ceoscgvpaymentmock.client.exception;

import kr.co.loopz.ceoscgvpaymentmock.common.exception.CustomException;
import kr.co.loopz.ceoscgvpaymentmock.common.exception.ErrorCode;

public class ClientException extends CustomException {
    public ClientException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ClientException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
