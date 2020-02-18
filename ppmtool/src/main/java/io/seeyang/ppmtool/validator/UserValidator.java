package io.seeyang.ppmtool.validator;

import io.seeyang.ppmtool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    // implement methods

    // define class that we are going to support with the Validator
    // return user.class equals to a class = supporting the User class in our domain to validate we have the correct object
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        // cast the user to type object
        User user = (User) object;

        // throw an error if the password is less OR 6 and greater than 24
        if (user.getPassword().length() < 6) {
            // attribute password and forcing the length to be greater than 6
            errors.rejectValue("password", "Length", "Password must be at least 6");
        }

        // if the user password does not MATCh the confirmPassword field, throw the error
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Match", "Passwords must match");
        }
    }
}
