package io.seeyang.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectNotFoundException extends RuntimeException {

    // ProjectNotFoundException Constructor that takes a message string
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
