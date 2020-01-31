import React, { Component } from 'react';
import {Link} from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import {deleteProject} from "../../actions/ProjectActions";
import Swal from 'sweetalert2';

class ProjectItem extends Component {

    // delete a project when clicked
    onDeleteClick = id => {
        // confirm or cancel with Sweet Alerts
        Swal.fire({
            title: 'Are you sure you want to delete this project?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
          }).then((toDelete) => {
            if (toDelete.value) {
                this.props.deleteProject(id); // pass in the id of the project
                Swal.fire(
                    'Deleted!',
                    'Project has been deleted.',
                    'success'
              )
            }
          })
        console.log(id)
    }

    render() {

        //extract the project prop from Dashboard.js
        const {project} = this.props;

        return (
            <div className="container">
                <div className="card card-body bg-light mb-3">
                    <div className="row">
                        <div className="col-2">
                            <span className="mx-auto">{project.projectIdentifier}</span>
                        </div>
                        <div className="col-lg-6 col-md-4 col-8">
                            <h3>{project.projectName}</h3>
                            <p>{project.description}</p>
                        </div>
                        <div className="col-md-4 d-none d-lg-block">
                            <ul className="list-group">
                                <Link to={`/projectBoard/${project.projectIdentifier}`}>
                                    <li className="list-group-item board">
                                        <i className="fa fa-flag-checkered pr-1"> Project Board </i>
                                    </li>
                                </Link>
                                {/* create a link to update project by its identifier */}
                                <Link to={`/updateProject/${project.projectIdentifier}`}>
                                    <li className="list-group-item update">
                                        <i className="fa fa-edit pr-1"> Update Project Info</i>
                                    </li>
                                </Link>
                                    <li className="list-group-item delete" 
                                    onClick={this.onDeleteClick.bind(
                                        this,
                                        project.projectIdentifier // the parent element
                                    )}
                                    >
                                        <i className="fa fa-minus-circle pr-1"> Delete Project</i>
                                    </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

ProjectItem.propTypes = {
    // tell react that the createProject function is a required prop type for this component to work
    deleteProject: PropTypes.func.isRequired // delete project function
};

export default  connect(null, {deleteProject}) (ProjectItem);