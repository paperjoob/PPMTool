import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import classnames from "classnames";
import { addProjectTask } from "../../../actions/backlogActions";
import PropTypes from "prop-types";

class AddProjectTask extends Component {

    state = {
        summary: '',
        acceptanceCriteria: '',
        status: '',
        priority: 0,
        dueDate: '',
        projectIdentifier: this.props.match.params.id,
        errors: {}
    }

    componentWillReceiveProps(nextProps) {
        // if there are errors in the nextProps
        // then we are setting the state with the errors
        if (nextProps.errors) {
            this.setState({errors: nextProps.errors})
        }
    }

    // onChange function for input form
    onChange = propertyName => (event) => {
        this.setState({
            [propertyName]: event.target.value
        });
        console.log('In handle change', this.state)
    };

    // when submitted, pass the state to the server to post
    onSubmit = (e) => {
        e.preventDefault();
        // create a new object to hold the state
        const newTask = {
            summary: this.state.summary,
            acceptanceCriteria: this.state.acceptanceCriteria,
            status: this.state.status,
            priority: this.state.priority,
            dueDate: this.state.dueDate,
          };
          // pass in the createProject function with three parameters: the project identifier, the state and the history
        this.props.addProjectTask(this.state.projectIdentifier, newTask, this.props.history);
        console.log("ON SUBMIT", newTask);
        console.log(this.state.projectIdentifier, "project identifier")
    }

    render() {

        const { id } = this.props.match.params;
        const {errors} = this.state;

        return (

          <div className="add-PBI">
            <div className="container">
              <div className="row">
                <div className="col-md-8 m-auto">
                  <Link to={`/projectBoard/${id}`} className="btn btn-light">
                    Back to Project Board
                  </Link>
                  <h4 className="display-4 text-center">Add Project Task</h4>
                  <p className="lead text-center">Project Name + Project Code</p>
                  <form>
                    <div className="form-group">
                      <input
                        type="text"
                        className={classnames("form-control form-control-lg", {"is-invalid": errors.summary})}
                        name="summary"
                        placeholder="Project Task summary"
                        value={this.state.summary}
                        onChange={this.onChange('summary')} 
                      />
                      {/* if there are errors, show the errors on the UI */}
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
                      <input
                        type="date"
                        className="form-control form-control-lg"
                        name="dueDate"
                        value={this.state.dueDate}
                        onChange={this.onChange('dueDate')} 
                      />
                    </div>
                    <div className="form-group">
                      <select
                        className="form-control form-control-lg"
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
                      <select
                        className="form-control form-control-lg"
                        name="status"
                        value={this.state.status}
                        onChange={this.onChange('status')} 
                      >
                        <option value="">Select Status</option>
                        <option value="TO_DO">To Do</option>
                        <option value="IN_PROGRESS">In Progress</option>
                        <option value="DONE">Complete</option>
                      </select>
                    </div>
    
                    <input
                      type="submit"
                      className="btn btn-primary btn-block mt-4"
                      onClick={this.onSubmit}
                    />
                  </form>
                </div>
              </div>
            </div>
          </div>
        );
    }
};

AddProjectTask.propTypes = {
    // tell react that the createProject function is a required prop type for this component to work
    addProjectTask: PropTypes.func.isRequired, // map the state to the components propertiess
    errors: PropTypes.object.isRequired,
};

// Instead of taking everything from state, we just want the projects information.
// if you wanted you could write this code like this:
const mapStateToProps = state => ({
    errors: state.errors
})

export default connect(mapStateToProps, {addProjectTask}) (AddProjectTask);
