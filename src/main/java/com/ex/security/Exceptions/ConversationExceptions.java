package com.ex.security.Exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ConversationExceptions extends RuntimeException {
    private String message;
    private HttpStatus status;

    public ConversationExceptions(String message, HttpStatus status) {
        super(message);

        this.message = message;
        this.status = status;
    }
}
