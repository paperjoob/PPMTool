import React, { Component } from 'react'

class AddProject extends Component {

    // constructor() {
    //     super()


    // }

    state = {
        projectName: '',
        projectIdentifier: '',
        description: '',
        start_date: '',
        end_date: ''
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
        console.log(newProject);
    }

    render() {
        return (
            <div>
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
                                    <input onClick={this.onSubmit} type="submit" className="btn btn-primary btn-block mt-4" />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default AddProject;
