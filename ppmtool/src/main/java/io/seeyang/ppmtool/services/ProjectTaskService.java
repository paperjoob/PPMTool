package io.seeyang.ppmtool.services;

import io.seeyang.ppmtool.domain.Backlog;
import io.seeyang.ppmtool.domain.ProjectTask;
import io.seeyang.ppmtool.exceptions.ProjectNotFoundException;
import io.seeyang.ppmtool.repositories.BacklogRepository;
import io.seeyang.ppmtool.repositories.ProjectRepositories;
import io.seeyang.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepositories projectRepositories;

    @Autowired
    private ProjectService projectService;

    // a method that returns a projectTask called addProjectTask
    // takes two parameters: project identifier and project task
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

            // I want Project Tasks to be added to a specific project, make sure project != null = Backlog exists
            // find the project by its identifier and username by the project
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog(); //backlogRepository.findByProjectIdentifier(projectIdentifier);

            // Set the backlog to the project task which sets the relationship
            projectTask.setBacklog(backlog);
            // We want our project sequence to be like this: IDPRO-1, IDPRO-2, IDPRO-3
            Integer BackLogSequence = backlog.getPTSequence(); // grab the PT Sequence, which is initialized at 0
            // Update the Backlog sequence so if you delete 3, the sequence will be 4
            BackLogSequence++; // increase the sequence right away
            backlog.setPTSequence(BackLogSequence);

            // Add sequence to the project task and project identifier
            projectTask.setProjectSequence(projectIdentifier+"-"+BackLogSequence); // EX: IDPRO-3
            projectTask.setProjectIdentifier(projectIdentifier); // set project identifier

            // Check if priority is null, if it is, Set initial priority to 3
            if(projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3); // if there are no priorities set, default it to a 3 - LOW PRIORITY
            }

            // Set initial status, if null
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO"); // if null, set the status to "TO_DO"
            }

            // return a saved project task
            return projectTaskRepository.save(projectTask);

    }

    // create a method to return the list by id
    public Iterable<ProjectTask> findBacklogById(String id, String username) {

        // check if Project exists
        // make sure the username and id matches the project
        projectService.findProjectByIdentifier(id, username);

        // if the projectservice does not throw an exception, return the backlog by its id
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    // Find project task by its sequence or Project Task ID
    public ProjectTask findProjectTaskByProjectSequence(String backlog_id, String pt_id, String username) {
        // make sure we are searching on the right backlog
        projectService.findProjectByIdentifier(backlog_id, username);

        // make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null) {
            throw new ProjectNotFoundException("Project Task with ID: '"+pt_id+"' not found.");
        }

        // make sure that the backlog/project id in the path corresponds to the RIGHT PROJECT
        // if the backlog id does not match the project identifier, then we can throw a new project exception
        if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task with ID: '"+pt_id+"' does not exist in project: '"+backlog_id+"'.");
        }

        // return tasks by its sequence ids
        return projectTask;
    }

    // update by project task id
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
        // use the findProjectTaskByProjectSequence and pass in the backlog and project task ids
        // this will use the exceptions
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, pt_id, username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    // delete project task
    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, pt_id, username);

        // call the delete method and pass in the project task
        projectTaskRepository.delete(projectTask);
    }
}
