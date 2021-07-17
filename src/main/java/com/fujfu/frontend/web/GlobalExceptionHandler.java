package com.fujfu.frontend.web;

import com.fujfu.frontend.exception.LoginValidException;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 *  统一异常处理基类
 * Created by ocean on 03/09/2020
 * @author ocean
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常
     *
     */
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseMO defaultErrorHandler(Exception e) {
        log.error("inner error：", e);
        return ResponseMO.errorWithMessage("内部发生错误，请联系管理员");
    }


    /**
     * 参数校验异常
     *
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class
            , MissingServletRequestParameterException.class})
    public ResponseMO methodArgumentNotValidException(Exception e) {
        //参数缺失异常
        if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException exception = (MissingServletRequestParameterException) e;
            String message = exception.getParameterName() + "不能为空";
            return ResponseMO.errorWithMessage(message);
        }

        List<ObjectError> allErrors = new ArrayList<>();
        if (e instanceof BindException) {
            allErrors = ((BindException) e).getBindingResult().getAllErrors();
        }
        if (e instanceof MethodArgumentNotValidException) {
            allErrors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
        }
        StringBuilder errors = new StringBuilder();
        for (ObjectError allError : allErrors) {
            errors.append(allError.getDefaultMessage());
        }
        String debugInfo = e.toString();
        return ResponseMO.errorWithErrorCode(errors.toString(), debugInfo);
    }

    /**
     * 请求方法不支持异常
     *
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseMO methodNotSupported(HttpRequestMethodNotSupportedException e) {
        String message = "不支持" + e.getMethod() + "请求访问";
        return ResponseMO.errorWithDebugInfo(message, e.toString());
    }

    /**
     * 请求内容不支持
     *
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseMO httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String message = "不支持'" + e.getContentType() + "'内容";
        return ResponseMO.errorWithDebugInfo(message, e.toString());
    }

    @ExceptionHandler(LoginValidException.class)
    public ResponseMO LoginValidException(LoginValidException e) {
        String message = e.getMessage();
        return ResponseMO.errorWithDebugInfo(message, e.toString());
    }
}
