package io.seeyang.ppmtool.services;

// created to handle the registration to save/update

import io.seeyang.ppmtool.domain.User;
import io.seeyang.ppmtool.exceptions.UsernameExistsException;
import io.seeyang.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // encode the password to unreadable
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser) {

        try {
            // encrypt the password the user sets
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            // Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());

            // Make sure that password and confirmPassword match
            // We do not persist or show the confirmPassword
            // after the user is registered with matched passwords, pass the confirmPassword JSON as an empty string
            newUser.setConfirmPassword("");

            // save the new user
            return userRepository.save(newUser);

        } catch (Exception e) {
            throw new UsernameExistsException("Username '"+newUser.getUsername()+"' already exists.");
        }

    }

}
