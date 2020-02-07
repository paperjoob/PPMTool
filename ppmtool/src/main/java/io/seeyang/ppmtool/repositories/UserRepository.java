package io.seeyang.ppmtool.repositories;

import io.seeyang.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // user details loads the user by its username and returns a user details object

    // need our repo to find by username
    User findByUsername(String username);

    // get username by ID
    User getById(Long id);

}
