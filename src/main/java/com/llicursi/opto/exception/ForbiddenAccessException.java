package com.llicursi.opto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User has no access to perform operation")
public class ForbiddenAccessException extends Exception {

    static final long serialVersionUID = -6687526993444229948L;

    public ForbiddenAccessException(String message) {
        super(message);
    }
}
