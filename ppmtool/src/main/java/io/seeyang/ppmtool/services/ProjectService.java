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

    // pass project id as a string and returns the projectRepositories parameter and uppercase it
    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepositories.findByProjectIdentifier(projectId.toUpperCase());

        // if project is null, throw an exception
        if(project == null) {
            throw new ProjectIDException("Project ID '" + projectId.toUpperCase()+ "' is not found");
        }

        return project; // if a project is found, return the project
    }

    // traverse through the list to find all projects
    public Iterable<Project> findAllProjects() {
        return projectRepositories.findAll();
    }

    // delete project by its id
    public void deleteProjectByIdentifier(String projectId) {
        // assign project to have the method findByProjectIdentifier
        Project project = projectRepositories.findByProjectIdentifier(projectId.toUpperCase());

        // throw an exception if the project is empty
        if(project == null) {
            throw new ProjectIDException("Cannot delete project with '"+projectId.toUpperCase()+"'. This project does not exist.");
        }
        // delete project if not null
        projectRepositories.delete(project);
    }
}
