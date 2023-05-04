package com.aarcom.categoryredisapi.exception;

import com.aarcom.categoryredisapi.exception_handler.EmployeeExceptionResponse;
import com.aarcom.categoryredisapi.exception_handler.EmployeeNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@RestController
@ControllerAdvice
public class EmployeeGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        EmployeeExceptionResponse exceptionResponse = new EmployeeExceptionResponse(ex.getMessage(),
                request.getDescription(false),
                new Date());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(EmployeeNotFound.class)
    public ResponseEntity<Object> handleEmployeeNotFoundException(Exception ex, WebRequest request) {
        EmployeeExceptionResponse exceptionResponse = new EmployeeExceptionResponse(ex.getMessage(),
                request.getDescription(false),
                new Date());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);

    }
}
