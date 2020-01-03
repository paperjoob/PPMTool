package io.seeyang.ppmtool.repositories;

import io.seeyang.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository; // gives us a set of methods that we can use out of the box
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepositories extends CrudRepository<Project, Long> { // pass in Project object


    @Override
    Iterable<Project> findAllById(Iterable<Long> iterable);
}
