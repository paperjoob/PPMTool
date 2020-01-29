package io.seeyang.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse {

    private String ProjectNotFound;

    public ProjectNotFoundExceptionResponse(String projectNotFound) {
        // assign ProjectNotFound to the parameter
        ProjectNotFound = projectNotFound;
    }

    // getters and setters
    public String getProjectNotFound() {
        return ProjectNotFound;
    }

    public void setProjectNotFound(String projectNotFound) {
        ProjectNotFound = projectNotFound;
    }
}
