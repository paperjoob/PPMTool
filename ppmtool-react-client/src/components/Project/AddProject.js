import React, { Component } from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux"; // connect react to the redux store
import {createProject} from "../../actions/ProjectActions";

class AddProject extends Component {

    state = {
        projectName: '',
        projectIdentifier: '',
        description: '',
        start_date: '',
        end_date: '',
        errors: {} // an empty errors object since there are no errors in the initial state
    }

    // when the props receive new props
    // takes a parameter of nextProps 
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
    }

    // on submit, pass this information along to the server
    onSubmit = (e) => {
        e.preventDefault();
        // create a new object to hold the state
        const newProject = {
            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            start_date: this.state.start_date,
            end_date: this.state.end_date
          };
          // pass in the createProject function with two parameters: the state and the history
        this.props.createProject(newProject, this.props.history);
        console.log(newProject);
    }

    render() {

        const {errors} = this.state;

        return (
            <div>
                {/* if there are errors, show these p tags */}
                <p>{errors.projectName}</p>
                <p>{errors.projectIdentifier}</p>
                <p>{errors.description}</p>
                <div className="project">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8 m-auto">
                                <h5 className="display-4 text-center">Create Project form</h5>
                                <hr />
                                <form >
                                    {/* project name input */}
                                    <div className="form-group">
                                        <h6>Project Name</h6>
                                        <input type="text" onChange={this.onChange('projectName')} className="form-control form-control-lg " name="projectName" placeholder="Project Name" value={this.state.projectName} />
                                    </div>
                                    {/* project identifier input */}
                                    <div className="form-group">
                                        <h6>Project Identifier</h6>
                                        <input type="text" onChange={this.onChange('projectIdentifier')} className="form-control form-control-lg" name="projectIdentifier" placeholder="Unique Project ID" value={this.state.projectIdentifier} />
                                    </div>
                                    {/* project description input */}
                                    <div className="form-group">
                                        <h6>Project Description</h6>
                                        <textarea className="form-control form-control-lg" onChange={this.onChange('description')} name="description" placeholder="Project Description" value={this.state.description} ></textarea>
                                    </div>
                                    <h6>Start Date</h6>
                                    <div className="form-group">
                                        <input type="date" onChange={this.onChange('start_date')} className="form-control form-control-lg" name="start_date" value={this.state.start_date} />
                                    </div>
                                    <h6>Estimated End Date</h6>
                                    <div className="form-group">
                                        <input type="date" className="form-control form-control-lg" onChange={this.onChange('end_date')} name="end_date" value={this.state.end_date} />
                                    </div>
                                    {/* submit button */}
                                    <input onClick={this.onSubmit} type="submit" className="btn btn-secondary btn-block mt-4" />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

AddProject.propTypes = {
    // tell react that the createProject function is a required prop type for this component to work
    createProject: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired // make sure the errors is an object
}

// Instead of taking everything from state, we just want the errors information.
// if you wanted you could write this code like this:
const mapStateToProps = state => ({
    errors: state.errors
})

export default connect(mapStateToProps, {createProject}) (AddProject);
