package io.seeyang.ppmtool.services;

import io.seeyang.ppmtool.domain.Project;
import io.seeyang.ppmtool.exceptions.ProjectIDException;
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
        // try and catch
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            // save or update the project object that was passed if no errors
            return projectRepositories.save(project);
        }catch (Exception e) {
            throw new ProjectIDException("Project ID '" + project.getProjectIdentifier().toUpperCase()+ "' already exists.");
        }

    }
}
