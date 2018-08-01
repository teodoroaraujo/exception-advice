package com.example.demo.util;

public enum ErrorMessage {
    NOT_FOUND("message.file.notfound"),
    NOT_IMPLEMENTED("message.method.not.implemented"),
    INTERNAL_SERVER_ERROR("message.unavailable.system"),
    UNSUPPORTED_MEDIA_TYPE("message.unsupported.media.type"),
    FORM_ERROR("message.form-error");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
