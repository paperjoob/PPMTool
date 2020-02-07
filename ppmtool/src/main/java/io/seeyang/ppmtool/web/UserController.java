package io.seeyang.ppmtool.web;

// Allows Users to Register and Log In

import io.seeyang.ppmtool.domain.User;
import io.seeyang.ppmtool.services.MapValidationErrorService;
import io.seeyang.ppmtool.services.UserService;
import io.seeyang.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/register") // valid object of type user
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        // validate the passwords match - pass in the user and the result
        // will return the errors if passwords do not match
        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        User newUser = userService.saveUser(user); // request body of User

        // return the new user and http status of created
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

    }
}
