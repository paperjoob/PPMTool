package io.seeyang.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // gives you a global exception handling for controllers
@RestController // helps you break away from having exception handlers that are controller specific
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler // annotation
    // pass in exception and request
    public final ResponseEntity<Object> handleProjectIdException(ProjectIDException ex, WebRequest request) {
        ProjectIDExceptionResponse exceptionResponse = new ProjectIDExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // ProjectNotFound Exception
    @ExceptionHandler // annotation
    // pass in exception and request
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request) {
        ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // UsernameExists Exception
    @ExceptionHandler // annotation
    // pass in exception and request
    public final ResponseEntity<Object> handleUsernameExists(UsernameExistsException ex, WebRequest request) {
        UsernameExistsResponse exceptionResponse = new UsernameExistsResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
