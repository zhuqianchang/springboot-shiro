package indi.zqc.shiro.core;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class APIResponse<T> {

    public static final String SUCCESS = "success";

    public static final String FAIL = "fail";

    public static final String USER_NOT_LOGIN = "user-not-login";

    public static final String UNAUTHORIZED = "unauthorized";

    private String code;

    private T data;

    private String message;

    public APIResponse(String code) {
        this.code = code;
    }

    public APIResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public APIResponse(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static APIResponse success() {
        return new APIResponse(SUCCESS);
    }

    public static <T> APIResponse<T> success(T data) {
        return new APIResponse(SUCCESS, data);
    }

    public static APIResponse fail(String message) {
        return new APIResponse(FAIL, null, message);
    }
}
