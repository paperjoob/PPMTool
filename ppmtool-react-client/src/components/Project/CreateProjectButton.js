import React from 'react';
import {Link} from 'react-router-dom';

// functional component
// no state manipulation needed
const CreateProjectButton = () => {
    return (
        // React.Fragment = an invisible HTML wrap
        // link the button to /addProject
        <React.Fragment>
            <Link to="/addProject" className="btn btn-lg btn-info">
                Create a Project
            </Link>
        </React.Fragment>
    )
}

export default CreateProjectButton;
