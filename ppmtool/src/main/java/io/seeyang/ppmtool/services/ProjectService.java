package io.seeyang.ppmtool.services;

import io.seeyang.ppmtool.domain.Project;
import io.seeyang.ppmtool.repositories.ProjectRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    // Tells the application context to inject an instance of ProjectRepositories here so you can use its methods
    @Autowired
    private ProjectRepositories projectRepositories;

    // enable project to save - pass in the project object in domain
    public Project saveOrUpdateProject(Project project) {
        // save or update the project object that was passed
        return projectRepositories.save(project);
    }
}
