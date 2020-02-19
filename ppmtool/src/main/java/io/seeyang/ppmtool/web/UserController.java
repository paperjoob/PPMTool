package io.seeyang.ppmtool.web;

// Allows Users to Register and Log In

import io.seeyang.ppmtool.domain.User;
import io.seeyang.ppmtool.payload.JWTLoginSuccessResponse;
import io.seeyang.ppmtool.payload.LoginRequest;
import io.seeyang.ppmtool.security.JWTTokenProvider;
import io.seeyang.ppmtool.services.MapValidationErrorService;
import io.seeyang.ppmtool.services.UserService;
import io.seeyang.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static io.seeyang.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@CrossOrigin // needed to talk to React client
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    // from JwtTokenProvider.java
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        // error map
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        // To authenticate the user, pass in the username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), // from the JWTTokenProvider.java
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate the token - pass in the token prefix (from the securityconstants.java) and generates a token
        String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        // if everything goes well, return a token
        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register") // valid object of type user
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        // validate the passwords match - pass in the user and the result
        // will return the errors if passwords do not match
        userValidator.validate(user, result);

        // error map
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        User newUser = userService.saveUser(user); // request body of User

        // return the new user and http status of created
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

    }
}
