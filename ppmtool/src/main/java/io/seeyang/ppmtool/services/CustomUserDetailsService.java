package io.seeyang.ppmtool.services;

import io.seeyang.ppmtool.domain.User;
import io.seeyang.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// makes sure the user exists
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // bring in the user repository
    @Autowired
    private UserRepository userRepository;

    // returns userdetails type
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // extract the User object from User.java to find the username
        User user = userRepository.findByUsername(username);

        // checks to see if user exists
        if (user == null) new UsernameNotFoundException("User not found");
        // return user if user exists
        return user;
    }

    // implement a load user by ID
    @Transactional
    public User loadUserById(Long id) {
        User user = userRepository.getById(id);

        // checks to see if user exists
        if (user == null) new UsernameNotFoundException("User not found");
        // return user if user exists
        return user;
    }

}
