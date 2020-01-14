package io.seeyang.ppmtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {

    // public method that returns a generic response entity
    public ResponseEntity<?> MapValidationService(BindingResult result) {

        // if the result has an error, return the list of errors below
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            // extract field and message and then display map with a FOR LOOP
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            // return the mapped string with errors
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
        // if there are no errors, return null
        return null;
    }

}
