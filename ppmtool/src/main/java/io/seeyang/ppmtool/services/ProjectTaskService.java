package io.seeyang.ppmtool.services;

import io.seeyang.ppmtool.domain.Backlog;
import io.seeyang.ppmtool.domain.ProjectTask;
import io.seeyang.ppmtool.repositories.BacklogRepository;
import io.seeyang.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    // a method that returns a projectTask called addProjectTask
    // takes two parameters: project identifier and project task
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        // I want Project Tasks to be added to a specific project, make sure project != null = Backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
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

        // Set initial priority, if null
        // in the future, need to add projectTask.getPriority() == 0 ||
        if(projectTask.getPriority() == null) {
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
    public Iterable<ProjectTask> findBacklogById(String id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
