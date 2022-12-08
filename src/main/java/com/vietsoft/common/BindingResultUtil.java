package com.vietsoft.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public final class BindingResultUtil {
    public static HttpEntity<Object> convertToResponse(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> messageErr = new ArrayList<>();
            for (FieldError e : errors) {
                messageErr.add(e.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageErr);
        }
        return null;
    }
}
