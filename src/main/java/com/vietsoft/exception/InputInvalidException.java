package com.vietsoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InputInvalidException extends RuntimeException {

    String error;

    public InputInvalidException(String message) {
        super(message);
    }

    public InputInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public InputInvalidException(String error, String message) {
        super(message);
        this.error = error;
    }
}
