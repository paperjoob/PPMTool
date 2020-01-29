package io.seeyang.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // indicates that this class is a JPA entity - represents data that can be persisted to the database
// every instance of an entity represents a row in a table
public class Backlog {
    @Id // each JPA entity must have a primary key that uniquely identifies it
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id generation strategy
    private Long id;
    private Integer PTSequence = 0; // project task sequence
    private String projectIdentifier; // share same identifier as the project

    // One to One with project - each project has one backlog and only belongs to that specific project
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id", nullable = false) // cannot be null
    @JsonIgnore // add annotation ignore - break infinite recursion
    private Project project; // name should match the Project.java map

    // One to Many project tasks - a backlog can have one or more project tasks - can only belong to 1 backlog
    // if you delete the back log, everything with the backlog will be deleted
    // cascade refresh: The content of the managed object in memory is discarded (including changes, if any) and replaced by data that is retrieved from the database.
    // orphan removal = when the child is no longer referenced by the parent, delete the child
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)
    // create a list called projectTasks as a new array list
    private List<ProjectTask> projectTasks = new ArrayList<>();

    // no argument constructor
    public Backlog() {
    }

    // ID Getter
    public Long getId() {
        return id;
    }

    // Set ID
    public void setId(Long id) {
        this.id = id;
    }

    // PT Sequence Getter
    public Integer getPTSequence() {
        return PTSequence;
    }

    // Set PT Sequence
    public void setPTSequence(Integer PTSequence) {
        this.PTSequence = PTSequence;
    }

    // Get Project Identifier
    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    // Set Project Identifier
    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    // project getters and setters
    public Project getProject() {
        return project;
    }

    // project getters and setters
    public void setProject(Project project) {
        this.project = project;
    }

    // getter and setters
    public List<ProjectTask> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<ProjectTask> projectTasks) {
        this.projectTasks = projectTasks;
    }
}
