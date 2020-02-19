import React, { Component } from 'react';
import {createNewUser} from "../../actions/securityActions";
import PropTypes from "prop-types";
import {connect} from "react-redux"; // connect react to the redux store
import classnames from "classnames";

class Register extends Component {

    state = {
        username: "",
        fullName: "",
        password: "",
        confirmPassword: "",
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
        console.log(this.state)
    };

    // on submit, pass this information along to the server
    onSubmit = (e) => {
        e.preventDefault();
        // create a new object to hold the state
        const newUser = {
            username: this.state.username,
            fullName: this.state.fullName,
            password: this.state.password,
            confirmPassword: this.state.confirmPassword
          };
          // pass in the createProject function with two parameters: the state and the history
          this.props.createNewUser(newUser, this.props.history);
    };

    render() {

        const {errors} = this.state;

        return (
            <div className="register">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h1 className="display-4 text-center">Sign Up</h1>
                            <p className="lead text-center">Create your Account</p>
                            <form >
                                <div className="form-group">
                                    <input type="text" className={classnames("form-control form-control-lg", {"is-invalid":errors.fullName} )}  placeholder="Name" name="fullName"
                                        value={this.state.fullName}
                                        onChange={this.onChange('fullName')}
                                        />
                                    {/* if there are errors, show the error with the invalid-feedback bootstrap style ERROR FONT TURNS RED */}
                                    {errors.fullName && (
                                        <div className="invalid-feedback">{errors.fullName}</div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <input type="email" className={classnames("form-control form-control-lg", {"is-invalid":errors.username} )}  placeholder="Email Address (Username)" name="username" 
                                        value={this.state.username}
                                        onChange={this.onChange('username')}
                                    />
                                    {/* if there are errors, show the error with the invalid-feedback bootstrap style ERROR FONT TURNS RED */}
                                    {errors.username && (
                                        <div className="invalid-feedback">{errors.username}</div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <input type="password" className={classnames("form-control form-control-lg", {"is-invalid":errors.password} )}  placeholder="Password" name="password" 
                                        value={this.state.password}
                                        onChange={this.onChange('password')}
                                    />
                                    {/* if there are errors, show the error with the invalid-feedback bootstrap style ERROR FONT TURNS RED */}
                                    {errors.password && (
                                        <div className="invalid-feedback">{errors.password}</div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <input type="password" className={classnames("form-control form-control-lg", {"is-invalid":errors.confirmPassword} )}  placeholder="Confirm Password"
                                        value={this.state.confirmPassword}
                                        name="confirmPassword" 
                                        onChange={this.onChange('confirmPassword')}
                                    />
                                    {/* if there are errors, show the error with the invalid-feedback bootstrap style ERROR FONT TURNS RED */}
                                    {errors.confirmPassword && (
                                        <div className="invalid-feedback">{errors.confirmPassword}</div>
                                    )}
                                </div>
                                <input onClick={this.onSubmit} type="submit" className="btn btn-secondary btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

Register.propTypes = {
    createNewUser: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
  };

// Instead of taking everything from state, we just want the errors information.
// if you wanted you could write this code like this:
const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(mapStateToProps, { createNewUser }) (Register);
