package kr.co.loopz.ceoscgvpaymentmock.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import kr.co.loopz.ceoscgvpaymentmock.common.dto.ResponseError;
import kr.co.loopz.ceoscgvpaymentmock.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseError> handleCustomException(CustomException e,
                                                               HttpServletRequest request) {

        ResponseError responseError = ResponseError.builder()
                .messageDetail(e.getMessage())
                .errorDetail(e.getErrorCode().getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(responseError);

    }

    // 요청 본문이 없거나 변환할 수 없는 경우 (NOT NULL)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseError> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e,
            HttpServletRequest request) {

        String errorDetail = "요청 본문(Request Body)이 필요합니다.";
        String exceptionMessage = e.getMessage();

        if (exceptionMessage == null) {
            exceptionMessage = "";
        }

        // Enum 값 오류 체크
        if (exceptionMessage.contains("not one of the values accepted for Enum class")) {
            // "Currency" 같은 Enum 타입명 추출
            Pattern enumPattern = Pattern.compile("Enum class: \\[class [^\\]]*\\.([^\\]]+)\\]");
            Matcher matcher = enumPattern.matcher(exceptionMessage);

            if (matcher.find()) {
                String enumType = matcher.group(1);
                errorDetail = String.format("%s: 허용되지 않는 값입니다.",
                                            enumType.substring(0, 1).toLowerCase() + enumType.substring(1));
            } else {
                errorDetail = "Enum 필드에 허용되지 않는 값이 포함되어 있습니다.";
            }
        }
        // JSON 파싱 오류
        else if (exceptionMessage.contains("JSON parse error")) {
            errorDetail = "JSON 형식이 올바르지 않습니다.";
        }
        // Required request body is missing
        else if (exceptionMessage.contains("Required request body is missing")) {
            errorDetail = "요청 본문(Request Body)이 필요합니다.";
        }

        ResponseError responseError = ResponseError.builder()
                .messageDetail("요청 본문이 누락되었거나 올바르지 않습니다.")
                .errorDetail(errorDetail)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }


    // @Valid 어노테이션으로 DTO 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidationExceptions(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        // 필드명으로 정렬하여 일관된 순서 보장
        String errorDetail = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(FieldError::getField)) // 필드명 알파벳 순 정렬
                .map(error -> String.format("%s: %s",
                                            error.getField(),
                                            error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        ResponseError responseError = ResponseError.builder()
                .messageDetail("유효성 검사 실패")
                .errorDetail(errorDetail)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }


    // Request Param, Path Variable 등의 유효성 검사 실패
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseError> handleConstraintViolationException(
            ConstraintViolationException e,
            HttpServletRequest request) {

        // 실패한 파라미터들을 수집
        String errorDetail = e.getConstraintViolations()
                .stream()
                .map(violation -> {
                    String propertyPath = violation.getPropertyPath().toString();
                    // 메서드명 제거하고 파라미터명만 추출
                    String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
                    return String.format("%s: %s", fieldName, violation.getMessage());
                })
                .collect(Collectors.joining(", "));

        ResponseError responseError = ResponseError.builder()
                .messageDetail("파라미터 유효성 검사 실패")
                .errorDetail(errorDetail)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

}