package org.sopt.util.exception;

public class StorageException extends RuntimeException {

    private final ErrorCode errorCode;

    public StorageException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public StorageException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}