package io.seeyang.ppmtool.web;

import io.seeyang.ppmtool.domain.Project;
import io.seeyang.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("")
    public ResponseEntity<Project> createNewProject(@RequestBody Project project) {
        // call the project service method to save or update the passed project
       project = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }
}
