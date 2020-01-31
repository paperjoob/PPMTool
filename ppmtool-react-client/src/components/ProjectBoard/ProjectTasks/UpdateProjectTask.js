import React, { Component } from "react";
import { connect } from "react-redux";
import classnames from "classnames";
import { Link } from "react-router-dom";
import {getProjectTask, updateProjectTask} from "../../../actions/backlogActions";
import PropTypes from "prop-types";

class UpdateProjectTask extends Component {

  ComponentDidMount() {
    // every time the component is rendered, grab the project task by its backlog id and pt_id
    // react-router-dom, you can grab the information as props
    const { backlog_id, pt_id } = this.props.match.params;
    this.props.getProjectTask(backlog_id, pt_id, this.props.history);
  }

state = {
    id: "",
    projectSequence: "",
    summary: "",
    acceptanceCriteria: "",
    status: "",
    priority: "",
    dueDate: "",
    projectIdentifier: "",
    create_At: "",
    errors: {}
};

componentWillReceiveProp(nextProps) {
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors });
          }

        // destructure the fields we need from the project_task props
        const {
            id,
            projectSequence,
            summary,
            acceptanceCriteria,
            status,
            priority,
            dueDate,
            projectIdentifier,
            create_At
        } = nextProps.project_task;
        // set the state to the props received from the project_task
        this.setState({
            id,
            projectSequence,
            summary,
            acceptanceCriteria,
            status,
            priority,
            dueDate,
            projectIdentifier,
            create_At
          });
    };

    // onChange function for input form
    onChange = propertyName => (event) => {
        this.setState({
            [propertyName]: event.target.value
        });
    };

    onSubmit(e) {
        e.preventDefault();
    
        const UpdateProjectTask = {
          id: this.state.id,
          projectSequence: this.state.projectSequence,
          summary: this.state.summary,
          acceptanceCriteria: this.state.acceptanceCriteria,
          status: this.state.status,
          priority: this.state.priority,
          dueDate: this.state.dueDate,
          projectIdentifier: this.state.projectIdentifier,
          create_At: this.state.create_At
        };
    
        // console.log(UpdateProjectTask);
        this.props.updateProjectTask(
          this.state.projectIdentifier,
          this.state.projectSequence,
          UpdateProjectTask,
          this.props.history
        );
      }

    render() {
        const { errors } = this.state;
        console.log(this.state, "the state of")
        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <Link to={`/projectBoard/${this.state.projectIdentifier}`}  className="btn btn-light">
                                Back to Project Board
                            </Link>
                            <h4 className="display-4 text-center">Update Project Task</h4>
                            <p className="lead text-center">Project Name: {this.state.projectIdentifier}</p>
                            <p className="lead text-center">Task ID: {this.state.projectSequence}</p>
                            <form>
                                <div className="form-group">
                                    <input type="text" className={classnames("form-control form-control-lg", {
                                        "is-invalid": errors.summary
                                        })}
                                    name="summary" placeholder="Project Task summary" 
                                    value={this.state.summary}
                                    onChange={this.onChange('summary')} 
                                    />
                                    {errors.summary && (
                                        <div className="invalid-feedback">{errors.summary}</div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <textarea
                                        className="form-control form-control-lg"
                                        placeholder="Acceptance Criteria"
                                        name="acceptanceCriteria"
                                        value={this.state.acceptanceCriteria}
                                        onChange={this.onChange('acceptanceCriteria')} 
                                    />
                                </div>
                                <h6>Due Date</h6>
                                <div className="form-group">
                                    <input type="date" className="form-control form-control-lg" 
                                    name="dueDate"
                                    value={this.state.dueDate}
                                    onChange={this.onChange('dueDate')} 
                                    />
                                </div>
                                <div className="form-group">
                                    <select className="form-control form-control-lg" 
                                    name="priority"
                                    value={this.state.priority}
                                    onChange={this.onChange('priority')} 
                                    >
                                        <option value={0}>Select Priority</option>
                                        <option value={1}>High</option>
                                        <option value={2}>Medium</option>
                                        <option value={3}>Low</option>
                                    </select>
                                </div>

                                <div className="form-group">
                                    <select className="form-control form-control-lg" name="status" 
                                        value={this.state.status}
                                        onChange={this.onChange('status')} 
                                    >
                                        <option value="">Select Status</option>
                                        <option value="TO_DO">To Do</option>
                                        <option value="IN_PROGRESS">In Progress</option>
                                        <option value="COMPLETE">Complete</option>
                                    </select>
                                </div>

                                <input type="submit" className="btn btn-primary btn-block mt-4" 
                                    onClick={this.onSubmit}
                                />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

// PropTypes
UpdateProjectTask.propTypes = {
    getProjectTask: PropTypes.func.isRequired,
    project_task: PropTypes.object.isRequired,
    updateProjectTask: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
  };
  
  const mapStateToProps = state => ({
    project_task: state.backlog.project_task,
    errors: state.errors
  });

export default connect(mapStateToProps, {getProjectTask, updateProjectTask}) (UpdateProjectTask);
