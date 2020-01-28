package io.seeyang.ppmtool.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // indicates that this class is a JPA entity - represents data that can be persisted to the database
// every instance of an entity represents a row in a table
public class Backlog {
    @Id // each JPA entity must have a primary key that uniquely identifies it
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id generation strategy
    private Long id;
    private Integer PTSequence = 0; // project task sequence
    private String projectIdentifier; // share same identifier as the project

    // One to One with project - each project has one backlog and only belongs to that specific project
    // One to Many project tasks - a backlog can have one or more project tasks - can only belong to 1 backlog

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
}
