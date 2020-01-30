import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";

class ProjectBoard extends Component {
  render() {

    // extract the project identifier of the project
    const { id } = this.props.match.params;

    return (

      // links to the addProjectTask component
      <div className="container">
        <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        <Backlog />
      </div>
    );
  }
}; // end of ProjectBoard component

export default ProjectBoard;