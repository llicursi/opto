package com.llicursi.opto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResultNotFoundException extends Exception {

    static final long serialVersionUID = -6687526993444229949L;

    public ResultNotFoundException(String message) {
        super(message);
    }
}
