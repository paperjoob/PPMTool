package io.seeyang.ppmtool.web;

import io.seeyang.ppmtool.domain.Project;
import io.seeyang.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    // @VALID is used to make sure we are passing a valid request
    // binding result is an interface that invokes the validator on an object - determines if there are errors
    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
        // if the result has an error, return the list of errors below
        if (result.hasErrors()) {

            Map<String, String> errorMap = new HashMap<>();

            // extract field and message and then display map with a FOR LOOP
            for (FieldError error: result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            // return the mapped string with errors
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        // call the project service method to save or update the passed project
       project = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }
}
