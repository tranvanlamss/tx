package com.vietsoft.security;

import com.vietsoft.exception.BadRequestException;
import com.vietsoft.exception.InputInvalidException;
import com.vietsoft.response.MessageResp;
import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlerInterceptor {

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadExceptions(BadRequestException ex) {
        MessageResp errorResponse = new MessageResp();
        errorResponse.setError(ex.getError());
        errorResponse.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        MessageResp errorResponse = new MessageResp();
        errorResponse.setError("Missing Parameter");
        errorResponse.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(JDBCException.class)
    public final ResponseEntity<Object> handleJDBCException(JDBCException ex) {
        MessageResp errorResponse = new MessageResp();
        errorResponse.setError("Internal Server Error");
        SQLException sqlException = ex.getSQLException();
        if(sqlException instanceof SQLIntegrityConstraintViolationException){
            errorResponse.setMessage("CONSTRAINT_VIOLATION");
        } else {
            errorResponse.setMessage(ex.getMessage());
        }
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InputInvalidException.class)
    public final ResponseEntity<Object> handleInputInvalidException(InputInvalidException ex) {
        MessageResp errorResponse = new MessageResp();
        errorResponse.setError(ex.getError());
        errorResponse.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
