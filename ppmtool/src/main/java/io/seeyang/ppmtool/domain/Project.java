package io.seeyang.ppmtool.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // attributes
    private String projectName;
    private String projectIdentifier; // adds custom identifier for our Project object
    private String description;
    private Date start_date; // start date of project
    private Date end_date; // end date of project

    private Date created_At;
    private Date updated_At; // last update made for the project

    // public constructor
    public Project() {

    }

    // Setter and Getters for each of the attributes
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getCreated_At() {
        return created_At;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public Date getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(Date updated_At) {
        this.updated_At = updated_At;
    }

    // every time we create an object, it will store the date
    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    // every time we update an object, it will store the latest date
    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }

}
