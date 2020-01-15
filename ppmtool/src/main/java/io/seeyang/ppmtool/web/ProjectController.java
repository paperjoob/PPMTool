package io.seeyang.ppmtool.web;

import io.seeyang.ppmtool.domain.Project;
import io.seeyang.ppmtool.services.MapValidationErrorService;
import io.seeyang.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    // inject ProjectService to enable being able to make a new project
    @Autowired
    private ProjectService projectService;

    // create a route for us to actually post a new project or create a new project
    // post mapping entity will return a response entity - return JSON responses and control response statuses
    // method is named createNewProject that takes in a request body of project
    // returns the new project and a status of created

    // inject MapValidationErrorService to enable validation
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // @VALID is used to make sure we are passing a valid request
    // binding result is an interface that invokes the validator on an object - determines if there are errors
    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
        // if the result has an error, return the list of errors below
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap!=null) return errorMap; // if errorMap is not null, return the errorMap

        // call the project service method to save or update the passed project
       project = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    // get mapping - retrieve by project identifier
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {

        // extract this to a new project location
        Project project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    // get route to grab all projects
    @GetMapping("/all") // getallProjects with no parameters
    public Iterable<Project> getAllProjects() {
        return projectService.findAllProjects();
    }
}
