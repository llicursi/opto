package com.llicursi.opto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVoteException extends Exception {

    static final long serialVersionUID = -6687526993444229951L;

    public InvalidVoteException(String message) {
        super(message);
    }
}
