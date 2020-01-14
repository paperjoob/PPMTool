package io.seeyang.ppmtool.exceptions;

public class ProjectIDExceptionResponse {

    // create a projectIdentifier string
    private String projectIdentifier;

    public ProjectIDExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier; // assign projectIdentifier to what is passed in
    }

    // getter
    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    // setter
    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
