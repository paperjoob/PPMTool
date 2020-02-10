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
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin // needed to talk to React client
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
    // PRINCIPAL = comes from security package - the owner of the token (user) fetches the user by grabbing the ID from the claims
    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal) {
        // if the result has an error, return the list of errors below
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap!=null) return errorMap; // if errorMap is not null, return the errorMap

        // call the project service method to save or update the passed project
        // set the relationship between the project and the user
       project = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    // get mapping - retrieve by project identifier
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {

        // extract this to a new project location
        Project project = projectService.findProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    // get route to grab all projects
    @GetMapping("/all") // getallProjects with no parameters
    public Iterable<Project> getAllProjects(Principal principal) {
        return projectService.findAllProjects(principal.getName());
    }

    // delete route by identifier
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal) {
        // use the method to delete the project by its id
        projectService.deleteProjectByIdentifier(projectId, principal.getName());
        // return the message below once deleted
        return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted.", HttpStatus.OK);
    }
}
