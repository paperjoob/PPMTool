package io.seeyang.ppmtool.repositories;

import io.seeyang.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository; // gives us a set of methods that we can use out of the box
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepositories extends CrudRepository<Project, Long> { // pass in Project object

    // pass in the projectID as a string
    Project findByProjectIdentifier(String projectId);

    @Override
    Iterable<Project> findAll();

    // find project by project leader (username)
    Iterable<Project> findAllByProjectLeader(String username);
}
