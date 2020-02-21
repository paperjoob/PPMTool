import React, { Component } from 'react';
import {Link} from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class Landing extends Component {

    // if you are logged in, get pushed to the dashboard when clicking on the Project Management Tool Header Name
    componentDidMount() {
        if(this.props.security.validToken) {
            this.props.history.push("/dashboard");
        }
    };

    render() {
        return (
            <div className="landing">
                <div className="light-overlay landing-inner text-dark">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12 text-center">
                                <h1 className="display-3 mb-4">Personal Kanban Tool</h1>
                                <p className="lead">
                                    Create your account to join active projects or start you own
                                </p>
                                <hr />
                                <Link className="btn btn-lg btn-primary mr-2" to="/register">
                                    Sign Up
                                </Link>
                                <Link className="btn btn-lg btn-secondary mr-2" to="/login">
                                    Login
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
};

Landing.propTypes = {
    security: PropTypes.object.isRequired
  };

// Instead of taking everything from state, we just want the security information.
// if you wanted you could write this code like this:
const mapStateToProps = state => ({
    security: state.security
});

export default connect(mapStateToProps) (Landing);
