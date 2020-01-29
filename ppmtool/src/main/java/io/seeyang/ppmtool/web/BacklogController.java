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
                                                     BindingResult result, @PathVariable String backlog_id) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap; // if there are errors, return the errors

        // new project task that takes the backlog id and project task
        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);

        // return new project task and the http status created
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    // GET by Backlog ID
    @GetMapping("/{backlog_id}")
    // iterable of backlog
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id) {
        return projectTaskService.findBacklogById(backlog_id);
    }

    // GET Project Task by ID
    // pass the backlog id and project task id (pt_id)
    @GetMapping("/{backlog_id}/{pt_id}")
    // takes in two parameters
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, pt_id);

        // return response entity of type project task and http status of OK
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

}
