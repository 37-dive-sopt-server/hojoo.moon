package org.sopt.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonPropertyOrder({"code", "message", "data"})
public class BaseResponse<T> {

    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    private BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> onSuccess(T data) {
        return new BaseResponse<>(String.valueOf(HttpStatus.OK.value()), "요청이 성공적으로 처리되었습니다.", data);
    }

    public static BaseResponse<Void> onSuccess() {
        return new BaseResponse<>(String.valueOf(HttpStatus.OK.value()), "요청이 성공적으로 처리되었습니다.", null);
    }

    public static <T> BaseResponse<T> onCreated(T data) {
        return new BaseResponse<>(String.valueOf(HttpStatus.CREATED.value()), "리소스가 성공적으로 생성되었습니다.", data);
    }

    public static <T> BaseResponse<T> onFailure(String code, String message) {
        return new BaseResponse<>(code, message, null);
    }

}
