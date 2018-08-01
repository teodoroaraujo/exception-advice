package com.example.demo.exceptionhandler;


import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetail {

    private int errorCode;
    private Object errorDescription;
    private String errorCodeUser;
    private String errorUserMessage;
    private long timeStamp;
    private String stackTrace;
}
