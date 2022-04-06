package com.ex.security.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CnvExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ConversationExceptions.class})
    protected ResponseEntity<Object> handleConflict(ConversationExceptions ex, WebRequest req){
        String body = ex.getMessage();

        return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getStatus() ,req);
    }
}
