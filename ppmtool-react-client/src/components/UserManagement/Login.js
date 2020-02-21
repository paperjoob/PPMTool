import React, { Component } from 'react';
import {login} from "../../actions/securityActions";
import PropTypes from "prop-types";
import {connect} from "react-redux"; // connect react to the redux store
import classnames from "classnames";

class Login extends Component {

    state = {
        username: '',
        password: '',
        errors: {}
    }

    // if you are logged in, get pushed to the dashboard
    componentDidMount() {
        if(this.props.security.validToken) {
            this.props.history.push("/dashboard");
        }
    }

    // when the props receive new props
    // takes a parameter of nextProps 
    componentWillReceiveProps(nextProps) {
        // if there are errors in the nextProps
        // then we are setting the state with the errors
        if (nextProps.errors) {
            this.setState({errors: nextProps.errors})
        }
        // if there's a valid token, push the user to the dashboard
        if (nextProps.security.validToken) {
            this.props.history.push("/dashboard")
        }
    }

    // onChange function for input form
    onChange = propertyName => (event) => {
        this.setState({
            [propertyName]: event.target.value
        });
    };

    // on submit, pass this information along to the server
    onSubmit = (e) => {
        e.preventDefault();
        // create a new object to hold the state
        const loginUser = {
            username: this.state.username,
            password: this.state.password,
            };
            // pass in the login function with two parameters: the state
        this.props.login(loginUser);
    };

    render() {

        const {errors} = this.state;

        return (
            <div className="login">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h1 className="display-4 text-center">Log In</h1>
                            <form action="dashboard.html">
                                <div className="form-group">
                                    <input type="email" className={classnames("form-control form-control-lg", {"is-invalid":errors.username} )} placeholder="Email Address" name="email"
                                    value={this.state.username} 
                                    onChange={this.onChange('username')}
                                    />
                                    {/* if there are errors, show the error with the invalid-feedback bootstrap style ERROR FONT TURNS RED */}
                                    {errors.username && (
                                        <div className="invalid-feedback">{errors.username}</div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <input type="password" className={classnames("form-control form-control-lg", {"is-invalid":errors.password} )} placeholder="Password" name="password"
                                    value={this.state.password} 
                                    onChange={this.onChange('password')}
                                    />
                                    {/* if there are errors, show the error with the invalid-feedback bootstrap style ERROR FONT TURNS RED */}
                                    {errors.password && (
                                        <div className="invalid-feedback">{errors.password}</div>
                                    )}
                                </div>
                                <input onClick={this.onSubmit} type="submit" className="btn btn-info btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
};

Login.propTypes = {
    login: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired
  };

// Instead of taking everything from state, we just want the errors information.
// if you wanted you could write this code like this:
const mapStateToProps = state => ({
    errors: state.errors,
    security: state.security
});

export default connect(mapStateToProps, { login })  (Login);
