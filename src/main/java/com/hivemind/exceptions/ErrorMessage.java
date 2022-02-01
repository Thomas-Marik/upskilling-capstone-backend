package com.hivemind.exceptions;



import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorMessage {
    Date date;
    int code;
    String status;
    String message;

    public ErrorMessage() {
        super();
        date = new Date();
    }

    public ErrorMessage(HttpStatus status, String message) {
        this.code = status.value();
        this.status = status.name();
        this.message = message;
        this.date = new Date();
    }
}