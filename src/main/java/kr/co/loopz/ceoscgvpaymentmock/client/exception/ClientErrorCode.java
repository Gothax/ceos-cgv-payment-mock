package kr.co.loopz.ceoscgvpaymentmock.client.exception;


import kr.co.loopz.ceoscgvpaymentmock.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ClientErrorCode implements ErrorCode {

    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "클라이언트를 찾을 수 없습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 인증 정보입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
