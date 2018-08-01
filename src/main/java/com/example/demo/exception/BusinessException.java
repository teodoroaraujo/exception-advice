package com.example.demo.exception;

import com.example.demo.util.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus errorCode;

    private String errorMessage;

    private String stackTraceDeveloper;

    private ErrorMessage userMessageCode;

    public BusinessException() {
        this.errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        this.userMessageCode = ErrorMessage.INTERNAL_SERVER_ERROR;
    }

    public BusinessException(HttpStatus errorCode, ErrorMessage userMessageCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getReasonPhrase();
        this.userMessageCode = userMessageCode;
    }

    public BusinessException(String stackTraceDeveloper) {
        this();
        this.stackTraceDeveloper = stackTraceDeveloper;

    }

}