package org.sopt.member.exception;

import org.sopt.util.exception.ErrorCode;
import org.sopt.util.exception.GeneralException;

public class MemberException extends GeneralException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
