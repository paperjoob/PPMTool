import React, { Component } from 'react';
import { getProject, createProject } from "../../actions/ProjectActions"; 
import PropTypes from "prop-types";
import {connect} from "react-redux";
import classnames from "classnames";

class UpdateProject extends Component {

    // when we load, we want to load the project and its details
    componentDidMount() {
        // takes in an ID parameter and the history props
        const {id} = this.props.match.params;
        this.props.getProject(id, this.props.history);
    };

    state = {
        id: this.props.match.params,
        projectName: '',
        projectIdentifier: '',
        description: '',
        start_date: '',
        end_date: '',
        errors: {} // if there are no errors, leave the errors object empty
    };

    // we receive the next props from the server/reducer, when update is clicked, populate the form with its details
    componentWillReceiveProps(nextProps) {

        // if there are errors, prevent the form from submitting and set the state of errors
        if (nextProps.errors) {
            this.setState({
                errors: nextProps.errors
            })
        }
        // desconstructor - grab all the details from the server and then set the state
        const {
            id,
            projectName,
            projectIdentifier,
            description,
            start_date,
            end_date,
        } = nextProps.project;
        // set the state to the props passed from the server
        this.setState({
            id,
            projectName,
            projectIdentifier,
            description,
            start_date,
            end_date,
        });
    };

    // onChange function for input form
    onChange = propertyName => (event) => {
        this.setState({
            [propertyName]: event.target.value
        });
    };

    // go back to the dashboard without making any changes
    handleBack = () => {
        this.props.history.push("/dashboard")
    }

    // when submitted
    onSubmit = (e) => {
        e.preventDefault();
        // put the new updates into an object and set the state
        const updateProject = {
            id: this.state.id,
            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            start_date: this.state.start_date,
            end_date: this.state.end_date,
        };
        // use the createProject function to update the project and then push it to the dashboard
        this.props.createProject(updateProject, this.props.history);
    }

    render() {
        
        const {errors} = this.state;

        return (
            <div className="project">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                        <h5 className="display-4 text-center">Update Project form</h5>
                        <hr />
                        <form>
                            <div className="form-group">
                            <input
                                type="text"
                                className={classnames("form-control form-control-lg", {"is-invalid":errors.projectName} )} 
                                placeholder="Project Name"
                                name="projectName"
                                value={this.state.projectName}
                                onChange={this.onChange('projectName')} 
                            />
                            {/* if there are errors, show the error with the invalid-feedback bootstrap style ERROR FONT TURNS RED */}
                            {errors.projectName && (
                                <div className="invalid-feedback">{errors.projectName}</div>
                            )}
                            </div>
                            <div className="form-group">
                            <input
                                type="text"
                                className={classnames("form-control form-control-lg", {"is-invalid":errors.projectIdentifier} )} 
                                placeholder="Unique Project ID"
                                disabled // leave the project id unchangeable
                                name="projectIdentifier"
                                value={this.state.projectIdentifier}
                                onChange={this.onChange('projectIdentifier')} 
                            />
                            {errors.projectIdentifier && (
                                <div className="invalid-feedback">{errors.projectIdentifier}</div>
                            )}
                            </div>
                            <div className="form-group">
                            <textarea
                                className={classnames("form-control form-control-lg", {"is-invalid":errors.description} )} 
                                placeholder="Project Description"
                                name="description"
                                value={this.state.description}
                                onChange={this.onChange('description')} 
                            />
                            {errors.description && (
                                <div className="invalid-feedback">{errors.description}</div>
                            )}
                            </div>
                            <h6>Start Date</h6>
                            <div className="form-group">
                            <input
                                type="date"
                                className="form-control form-control-lg"
                                name="start_date"
                                value={this.state.start_date}
                                onChange={this.onChange('start_date')} 
                            />
                            </div>
                            <h6>Estimated End Date</h6>
                            <div className="form-group">
                            <input
                                type="date"
                                className="form-control form-control-lg"
                                name="end_date"
                                value={this.state.end_date}
                                onChange={this.onChange('end_date')} 
                            />
                            </div>

                            <input
                                type="button"
                                className="btn btn-primary btn-block mt-4"
                                value="Cancel"
                                onClick={this.handleBack}
                            />
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
        )
    }
};

UpdateProject.propTypes = {
    // tell react that the createProject function is a required prop type for this component to work
    getProject: PropTypes.func.isRequired, // get a single project
    project: PropTypes.object.isRequired,
    createProject: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired // make sure the errors is an object
};

// Instead of taking everything from state, we just want the projects information.
// if you wanted you could write this code like this:
const mapStateToProps = state => ({
    project: state.project.project, // project reducer
    errors: state.errors
})

export default connect(mapStateToProps, {getProject, createProject}) (UpdateProject);