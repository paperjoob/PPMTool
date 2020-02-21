import React from 'react';
import {Route, Redirect} from 'react-router-dom';
import {connect} from 'react-redux';
import PropTypes from "prop-types";

const SecureRoute =({component: Component, security, ...otherProps}) => (
    // we will take otherProps and we will render the component props only if there is a validToken that's true
    <Route 
        {...otherProps} render={props => security.validToken === true ? (<Component {...props}/>) : 
        // else we will redirect to login
        (<Redirect to="/login"/>)
        } 
    />
);

SecureRoute.propTypes = {
    security: PropTypes.object.isRequired
  };

// Instead of taking everything from state, we just want the security information.
// if you wanted you could write this code like this:
const mapStateToProps = state => ({
    security: state.security
});

export default connect(mapStateToProps) (SecureRoute);

