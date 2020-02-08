package io.seeyang.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // attributes

    // not blank annotation allows us to set up a message for input validation
    @NotBlank(message = "Project name is required")
    private String projectName;
    @NotBlank(message = "Project identifier is required")
    // minimum 4 characters, 5 characters max for project identifier
    @Size(min=4, max=5, message = "Please use 4 to 5 characters")
    // cannot update the project identifier, and set it to unique for each project
    // the @column only validates on the database side, not through the validation service
    @Column(updatable = false, unique = true)
    private String projectIdentifier; // adds custom identifier for our Project object
    @NotBlank(message = "Project description is required")
    private String description;
    @JsonFormat(pattern = "yyyy-mm-dd") // date format
    private Date start_date; // start date of project
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date end_date; // end date of project
    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false) // if a null value is passed in, it won't update the values
    private Date created_At;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At; // last update made for the project

    // backlog - a project has ONLY ONE backlog
    // fetch eager = project object is readily available
    // cascade = the owning side of the relationship. EX: If I delete something downstream, it won't delete the project
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore // hide the backlog information
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    // project leader
    private String projectLeader;

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

    // Back log getters and setters
    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }
}
