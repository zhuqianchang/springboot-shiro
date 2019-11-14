package indi.zqc.shiro.advice;

import indi.zqc.shiro.core.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ControllerAdive {

    /**
     * AuthenticationException 异常处理
     */
    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public APIResponse authenticationExceptionHandler(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return new APIResponse(APIResponse.FAIL, null, e.getMessage());
    }

    /**
     * AuthorizationException 异常处理
     */
    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public APIResponse authenticationExceptionHandler(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return new APIResponse(APIResponse.UNAUTHORIZED, null, e.getMessage());
    }

    /**
     * Exception 异常处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public APIResponse exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return APIResponse.fail(e.getMessage());
    }
}
