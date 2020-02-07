package io.seeyang.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // a 400 status
public class UsernameExistsException extends RuntimeException {

    // constructor that takes a message string
    public UsernameExistsException(String message) {
        super(message);
    }
}
