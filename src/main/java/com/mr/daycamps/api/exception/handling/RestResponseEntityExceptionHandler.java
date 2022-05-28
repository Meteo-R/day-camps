package com.mr.daycamps.api.exception.handling;

import com.mr.daycamps.domain.exception.ChildNotFoundException;
import com.mr.daycamps.domain.exception.ParentNotFoundException;
import com.mr.daycamps.domain.exception.SchoolNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            ParentNotFoundException.class,
            SchoolNotFoundException.class,
            ChildNotFoundException.class
    })
    protected ResponseEntity<?> handleNotFound(RuntimeException exception, WebRequest request) {
        String responseBody = exception.getMessage();
        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}