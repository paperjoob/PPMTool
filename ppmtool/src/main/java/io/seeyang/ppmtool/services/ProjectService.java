package io.seeyang.ppmtool.services;

import io.seeyang.ppmtool.domain.Backlog;
import io.seeyang.ppmtool.domain.Project;
import io.seeyang.ppmtool.domain.User;
import io.seeyang.ppmtool.exceptions.ProjectIDException;
import io.seeyang.ppmtool.repositories.BacklogRepository;
import io.seeyang.ppmtool.repositories.ProjectRepositories;
import io.seeyang.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    // Tells the application context to inject an instance of ProjectRepositories here so you can use its methods
    @Autowired
    private ProjectRepositories projectRepositories;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    // enable project to save - pass in the project object in domain
    public Project saveOrUpdateProject(Project project, String username) {
        // try and catch
        try{

            // find the user
            User user = userRepository.findByUsername(username);
            project.setProjectLeader(user.getUsername());
            // set the user to the project
            project.setUser(user);

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            // if the project id is empty, create a new backlog
            if(project.getId() == null) {
                Backlog backlog = new Backlog(); // create a new instance of backlog
                project.setBacklog(backlog); // set backlog to the new backlog just created
                backlog.setProject(project); // set the project that we are creating
                // set project identifier to the same one that we are persisting with the object
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            // if the project id is NOT NULL, then we are UPDATING
            if (project.getId() != null) {
                // find the backlog by its project identifier and set it to the backlog that was originally created
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

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
