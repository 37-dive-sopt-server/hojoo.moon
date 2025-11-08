package org.sopt.util.exception.handler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.sopt.util.exception.CommonErrorCode;
import org.sopt.util.exception.ErrorCode;
import org.sopt.util.exception.GeneralException;
import org.sopt.util.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<BaseResponse<?>> handleGeneralException(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(BaseResponse.onFailure(errorCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable rootCause = getRootCause(e);

        ErrorCode errorCode;
        if (rootCause instanceof DateTimeParseException) {
            errorCode = CommonErrorCode.DATETIME_PARSE_ERROR;
        } else if (rootCause instanceof MismatchedInputException) {
            errorCode = CommonErrorCode.MISMATCHED_INPUT;
        } else {
            errorCode = CommonErrorCode.BAD_REQUEST;
        }

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(BaseResponse.onFailure(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String validationMessages = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorCode errorCode = CommonErrorCode.VALIDATION_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(BaseResponse.onFailure(errorCode.getCode(), errorCode.format(validationMessages)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception e) {
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(BaseResponse.onFailure(errorCode.getCode(), errorCode.format(e.getMessage())));
    }

    private Throwable getRootCause(Throwable ex) {
        Throwable cur = ex;
        while (cur.getCause() != null) {
            cur = cur.getCause();
        }
        return cur;
    }
}
