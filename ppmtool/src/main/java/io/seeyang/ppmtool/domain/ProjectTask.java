package io.seeyang.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, unique = true) // cannot update the projectSequence and no duplicates
    private String projectSequence; // projectIdentifier + 1 (Pt sequence) - used to search individual project tasks
    @NotBlank(message = "Please include a project summary.")
    private String summary;
    private String acceptanceCriteria; // so you can write information on what you want to accomplish
    private String status; // status of application
    private Integer priority; // so we can group tasks by priority levels
    private Date dueDate; // due date of task

    // Many to One with Backlog
    // Refresh EX: I can delete a project task that belongs to a child as a backlog object, and server will refresh the backlog
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="backlog_id", updatable = false, nullable = false) // cannot update and cannot be empty
    @JsonIgnore // kill the recursion
    private Backlog backlog;

    @Column(updatable = false) // projectIdentifier cannot be updatable
    private String projectIdentifier; // store the identifier here to pass it in the URL onto that specific backlog

    private Date create_At; // created date of task
    private Date update_At; // last updated date of task

    // no argument constructor
    public ProjectTask() {
    }

    // SETTERS AND GETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectSequence() {
        return projectSequence;
    }

    public void setProjectSequence(String projectSequence) {
        this.projectSequence = projectSequence;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

    // every time you create an object, the method should be called before the entity is inserted into the database
    @PrePersist
    protected void onCreate() {
        this.create_At = new Date();
    }

    // triggered before an entity has been updated in the database
    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date();
    }

    // getters and setters for backlogs
    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    // get more meaningful information when calling the objects
    @Override
    public String toString() {
        return "ProjectTask{" +
                "id=" + id +
                ", projectSequence='" + projectSequence + '\'' +
                ", summary='" + summary + '\'' +
                ", acceptanceCriteria='" + acceptanceCriteria + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", projectIdentifier='" + projectIdentifier + '\'' +
                ", create_At=" + create_At +
                ", update_At=" + update_At +
                '}';
    }
}
