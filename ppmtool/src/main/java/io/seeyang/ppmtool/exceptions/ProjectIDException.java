package io.seeyang.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // send a bad request if this exception is thrown
public class ProjectIDException extends RuntimeException { // extend the runtimexception class

    // create a constructor that takes in a String message
    public ProjectIDException(String message) {
        super(message);
    }
}
