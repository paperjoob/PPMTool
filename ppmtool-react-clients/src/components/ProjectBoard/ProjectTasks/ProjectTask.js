import React, { Component } from 'react';
import {Link} from "react-router-dom";

class ProjectTask extends Component {
    render() {

        // extract the project task from the props
        const {project_task} = this.props;
        let priorityString;
        let priorityClass;

        // if the priority task is equal to 1, set the card to red with light text
        if(project_task.priority === 1) {
            priorityClass = "bg-danger text-light";
            priorityString = "High"
        };

        // medium priority
        if(project_task.priority === 2) {
            priorityClass = "bg-warning text-light";
            priorityString = "Medium"
        };

        // low priority
        if(project_task.priority === 3) {
            priorityClass = "bg-light text-black";
            priorityString = "Low"
        };

        return (
            <div className="card mb-1 bg-light">
                <div className={`card-header text-primary ${priorityClass}`}>
                    ID: {project_task.projectSequence}
                    <br/>
                    Priority: {priorityString}
                </div>
                <div className="card-body bg-light">
                    <h5 className="card-title">{project_task.summary}</h5>
                    <p className="card-text text-truncate ">
                    {project_task.acceptanceCriteria}
                    </p>
                    <Link to={`/updateProjectTask/${project_task.projectIdentifier}/${project_task.projectSequence}`} className="btn btn-primary">
                    View / Update
                    </Link>

                    <button className="btn btn-danger ml-4">Delete</button>
                </div>
            </div>
        )
    }
}; // end of ProjectTask component

export default ProjectTask;
