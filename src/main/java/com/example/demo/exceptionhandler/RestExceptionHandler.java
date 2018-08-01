package com.example.demo.exceptionhandler;

import com.example.demo.exception.BusinessException;
import com.example.demo.util.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_IMPLEMENTED;
        ErrorMessage errorMessage = ErrorMessage.NOT_IMPLEMENTED;
        String errorUserMessage = getMessage(errorMessage.getMessage());

        ErrorDetail errorDetail = createErrorDetail(httpStatus, httpStatus.getReasonPhrase(), ex.getMessage(),
                errorMessage.toString(), errorUserMessage);

        return new ResponseEntity<>(errorDetail, httpStatus);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> businessException(BusinessException be, HttpServletRequest request) {

        HttpStatus errorCode = be.getErrorCode();
        String errorMessage = be.getErrorMessage();
        String stackTrace = be.getStackTraceDeveloper();
        String errorCodeUser = be.getUserMessageCode().toString();
        String errorUserMessage = getMessage(be.getUserMessageCode().getMessage());

        ErrorDetail errorDetail = createErrorDetail(errorCode, errorMessage, stackTrace, errorCodeUser,
                errorUserMessage);

        return new ResponseEntity<>(errorDetail, errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> generalException(Exception ex, HttpServletRequest request) {

        ErrorMessage errorMsg = ErrorMessage.INTERNAL_SERVER_ERROR;
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorUserMessage = getMessage(errorMsg.getMessage());
        ErrorDetail errorDetail = createErrorDetail(httpStatus, httpStatus.getReasonPhrase(), ex.getMessage(),
                errorMsg.toString(), errorUserMessage);

        logger.error("Error:", ex);

        return new ResponseEntity<>(errorDetail, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        HttpStatus errorCode = HttpStatus.BAD_REQUEST;
        ErrorDetail errorDetail = createErrorDetail(errorCode, errors, ex.getMessage(),
                ErrorMessage.FORM_ERROR.toString(), null);

        return new ResponseEntity<>(errorDetail, errorCode);
    }

    /**
     * @param errorCode
     * @param errorMessage
     * @param stackTrace
     * @param errorCodeUser
     * @param errorUserMessage
     * @return
     */
    private ErrorDetail createErrorDetail(HttpStatus errorCode, Object errorMessage, String stackTrace,
            String errorCodeUser, String errorUserMessage) {
        return ErrorDetail.builder()
                .timeStamp(new Date().getTime())
                .errorCode(errorCode.value())
                .errorDescription(errorMessage)
                .errorCodeUser(errorCodeUser)
                .errorUserMessage(errorUserMessage)
                .stackTrace(stackTrace).build();
    }

    private String getMessage(String messageKey) {

        return messageSource.getMessage(messageKey, null, null);

    }

}
