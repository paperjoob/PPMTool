import React, {Component} from 'react';
import ProjectItem from './Project/ProjectItem';
import CreateProjectButton from './Project/CreateProjectButton';
import { connect } from "react-redux"; // connect to the redux store
import { getProjects } from "../actions/ProjectActions";
import PropTypes from "prop-types";

class Dashboard extends Component {

    // life cycle hook
    // called immediately after a component is mounted and reloads if the state changes
    componentDidMount() {
        this.props.getProjects();
    }

    render() {

        // project list to pass onto the child component
        const {projects} = this.props.project;

        return (
            // Dashboard Component
            <div className="projects">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">
                            <h1 className="display-4 text-center">Projects</h1>
                            <br />
                            <CreateProjectButton />
                            <br />
                            <hr />
                            {/* Insert ProjectItem.js and map the list in the project item component */}
                            {projects.map(project => (
                                <ProjectItem key={project.id} project = {project}/>
                            ))
                            }
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

Dashboard.propTypes = {
    // tell react that the createProject function is a required prop type for this component to work
    project: PropTypes.object.isRequired, // map the state to the components properties
    getProjects: PropTypes.func.isRequired
};

// Instead of taking everything from state, we just want the projects information.
// if you wanted you could write this code like this:
const mapStateToProps = state => ({
    project: state.project // project reducer
})

export default connect(mapStateToProps, {getProjects}) (Dashboard);