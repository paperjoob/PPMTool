package io.seeyang.ppmtool.services;

import io.seeyang.ppmtool.domain.Backlog;
import io.seeyang.ppmtool.domain.Project;
import io.seeyang.ppmtool.domain.User;
import io.seeyang.ppmtool.exceptions.ProjectIDException;
import io.seeyang.ppmtool.exceptions.ProjectNotFoundException;
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


        // WHEN UPDATING
        // if the project id is not null, find the project by its identifier
        // we only want the user who owns that particular (if found) to be able to update the project
        if (project.getId() != null) {
            Project existingProject = projectRepositories.findByProjectIdentifier(project.getProjectIdentifier());

            // if the existing project is not null and the username isn't equal to the existing project's owner,
            // throw an exception
            if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
                throw new ProjectNotFoundException("Project not found in your account.");
            } else if (existingProject == null) {
                // if the project is empty or not found, show this error - user will have to create a new project if the project id doesn't match
                throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it does not exist.");
            }
        }

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
    public Project findProjectByIdentifier(String projectId, String username) {

        Project project = projectRepositories.findByProjectIdentifier(projectId.toUpperCase());

        // if project is null, throw an exception
        if(project == null) {
            throw new ProjectIDException("Project ID '" + projectId.toUpperCase()+ "' is not found");
        }

        // if the project leader does not equal the username string, throw an error
        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account.");
        }

        return project; // if a project is found, return the project
    }

    // traverse through the list to find all projects by username
    public Iterable<Project> findAllProjects(String username) {
        // run this method to find the project by its project leader
        return projectRepositories.findAllByProjectLeader(username);
    }

    // delete project by its id
    public void deleteProjectByIdentifier(String projectId, String username) {
//        // assign project to have the method findByProjectIdentifier
//        Project project = projectRepositories.findByProjectIdentifier(projectId.toUpperCase());
//
//        // throw an exception if the project is empty
//        if(project == null) {
//            throw new ProjectIDException("Cannot delete project with '"+projectId.toUpperCase()+"'. This project does not exist.");
//        }
        // find the project id and username.
        // if it all matches, delete the project
        projectRepositories.delete(findProjectByIdentifier(projectId, username));
    }
}
