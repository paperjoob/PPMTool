import React, { Component } from 'react';
import ProjectTask from './ProjectTasks/ProjectTask';

class Backlog extends Component {
    
    render() {

        // extract the project tasks from the props from ProjectBoard.js
        const { project_tasks_prop } = this.props;

        // iterate through the list of project tasks from the props
        const tasks = project_tasks_prop.map(project_task => (
          <ProjectTask key={project_task.id} project_task={project_task} />
        ));

        // depending on the status of the project, load the task under the correct status
        let toDoItems = [];
        let inProgressItems = [];
        let completeItems = [];

        // loop through the iterations, and check their statuses
        for(let i = 0; i < tasks.length; i++) {
            console.log(tasks[i]);
            // if the current task in the loop's status is equal to do, push that task into the to do array
            if(tasks[i].props.project_task.status === "TO_DO") {
                toDoItems.push(tasks[i]);
            };
            // if task is in progress, push into the in progress array
            if(tasks[i].props.project_task.status === "IN_PROGRESS") {
                inProgressItems.push(tasks[i]);
            };
            // if task is complete, push into the completed array
            if(tasks[i].props.project_task.status === "COMPLETE") {
                completeItems.push(tasks[i]);
            }
        }

        return (
            <div className="container">
                <div className="row">
                <div className="col-md-4">
                    <div className="card text-center mb-2">
                    <div className="card-header bg-secondary text-light">
                        <h3>To Do</h3>
                    </div>
                    </div>
                    {/* insert to do list */}
                    {toDoItems}
                </div>
                <div className="col-md-4">
                    <div className="card text-center mb-2">
                    <div className="card-header bg-primary text-white">
                        <h3>In Progress</h3>
                    </div>
                    </div>
                    {inProgressItems}
                </div>
                <div className="col-md-4">
                    <div className="card text-center mb-2">
                    <div className="card-header bg-success text-white">
                        <h3>Complete</h3>
                    </div>
                    </div>
                    {completeItems}
                </div>
                </div>
          </div>
        )
    }
}

export default Backlog;
