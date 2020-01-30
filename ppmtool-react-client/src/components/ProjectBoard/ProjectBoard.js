import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import {getBacklog} from "../../actions/backlogActions";

class ProjectBoard extends Component {

  // when we first load, have an empty errors object
  state = {
    errors: {}
  }

  componentDidMount() {
    const {id} = this.props.match.params;
    this.props.getBacklog(id);
  }

  // if there are errors, fill the errors state
  componentWillReceiveProps(nextProps) {
    if(nextProps.errors) {
      this.setState({
        errors: nextProps.errors
      })
    }
  }

  render() {

    // extract the project identifier of the project
    const { id } = this.props.match.params;
    // take the project tasks from the backlog object
    const { project_tasks } = this.props.backlog;
    const {errors} = this.state;

    let BoardContent;

    // if there are errors, show the errors projectnotfound reasons when the project tasks length is less than 1
    const boardAlgorithm = (errors, project_tasks) => {
      if (project_tasks.length < 1) {
        if (errors.projectNotFound) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectNotFound}
            </div>
          );
        } else {
          // if there are no tasks on the project, show this
          return (
            <div className="alert alert-info text-center" role="alert">
              No Project Tasks on this board
            </div>
          );
        }
      } else {
        // {/* pass in project tasks as props to Backlog component */}
        return <Backlog project_tasks_prop={project_tasks} />;
      }
    }; // end boardAlgorithm

    // assign boardAlgorithm to Boardcontent and pass in the errors and project tasks if any
    BoardContent = boardAlgorithm(errors, project_tasks);

    return (

      // links to the addProjectTask component
      <div className="container">
        <Link to={`/addProjectTask/${id}`} className="btn btn-secondary mb-3">
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        {BoardContent}
      </div>
    );
  }
}; // end of ProjectBoard component

ProjectBoard.propTypes = {
  // tell react that the createProject function is a required prop type for this component to work
  backlog: PropTypes.object.isRequired, // backlog reducer prop
  errors: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  backlog: state.backlog,
  errors: state.errors
})

export default connect(mapStateToProps, {getBacklog}) (ProjectBoard);