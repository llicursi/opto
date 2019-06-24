package com.llicursi.opto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateRangeException extends Exception {

    static final long serialVersionUID = -6687526993444229950L;

    public InvalidDateRangeException(String message) {
        super(message);
    }
}
