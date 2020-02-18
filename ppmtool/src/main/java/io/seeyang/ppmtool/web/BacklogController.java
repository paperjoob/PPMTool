package io.seeyang.ppmtool.web;

import io.seeyang.ppmtool.domain.ProjectTask;
import io.seeyang.ppmtool.services.MapValidationErrorService;
import io.seeyang.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin // needed to cross connect to React
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // Post by Backlog ID
    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result, @PathVariable String backlog_id,
                                                     Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap; // if there are errors, return the errors

        // new project task that takes the backlog id and project task along with the username
        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

        // return new project task and the http status created
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    // GET by Backlog ID
    @GetMapping("/{backlog_id}")
    // iterable of backlog
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
        // return the backlog id by its id and return the username
        return projectTaskService.findBacklogById(backlog_id, principal.getName());
    }

    // GET Project Task by ID
    // pass the backlog id and project task id (pt_id)
    @GetMapping("/{backlog_id}/{pt_id}")
    // takes in two parameters
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, pt_id, principal.getName());

        // return response entity of type project task and http status of OK
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    // Updating a project task
    // patch mapping = a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.POST)
    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                               @PathVariable String backlog_id, @PathVariable String pt_id,
                                               Principal principal) {
        // check to see if there are any errors
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap; // if there are errors, return the errors

        // assign updatedTask to the project task
        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id, principal.getName());

        // pass in the updated task and http of OK
        return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,
                                               Principal principal) {

        // call the delete method from project task service and pass in the backlog and pt ids
        projectTaskService.deletePTByProjectSequence(backlog_id, pt_id, principal.getName());

        // return the string and status of OK
        return new ResponseEntity<String>("Project task "+pt_id+" was deleted successfully.", HttpStatus.OK);
    }

}
