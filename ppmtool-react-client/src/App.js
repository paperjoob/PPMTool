import React, {Component} from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import AddProject from "./components/Project/AddProject";
import {Provider} from "react-redux"; // allows us to connect react with redux
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";
import Landing from "./components/Layout/Landing";
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";
import jwt_decode from "jwt-decode";
import setJwtToken from "./securityUtils/setJwtToken";
import { SET_CURRENT_USER } from "./actions/Types";
import {logout} from "./actions/securityActions";
import SecureRoute from "./securityUtils/SecureRoute";

// extract the jwtToken from the localStorage
const jwtToken = localStorage.jwtToken;

// if we have a token, set the token in the Authorization header, 
// so that React keeps the user logged in during the session when refreshing the page (keeps the user logged in the state)
if (jwtToken) {
  setJwtToken(jwtToken)
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
      type: SET_CURRENT_USER,
      payload: decoded_jwtToken
  });

  const currentTime = Date.now()/1000;
  // if the decoded token's expiration time is less than the current time, then it is expired
  if(decoded_jwtToken.exp < currentTime) {
    // handle logout for user - call the log out function
    store.dispatch(logout());
    // send the user back to the main site
    window.location.href="/";
  }
};

class App extends Component {
  render() {
   return (
     <Provider store={store}>
    {/* wrap the app.js in the router tag to enable us to have specific routes */}
      <Router>
        <div className="App">
          <Header />
          {/* Public Routes */}

          <Route exact path="/" component={Landing} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />

          {/* Private Routes wrapped in Switch */}
          <Switch>
          <SecureRoute exact path="/dashboard" component={Dashboard}/>
          <SecureRoute exact path="/addProject" component={AddProject}/>
          {/* the update project takes a parameter of ID */}
          <SecureRoute exact path ="/updateProject/:id" component={UpdateProject}/>
          <SecureRoute exact path="/projectBoard/:id" component={ProjectBoard} />
          <SecureRoute exact path="/addProjectTask/:id" component={AddProjectTask} />
          {/* pass in the backlog_id and pt_id from the server in order to update a project task */}
          <SecureRoute exact path="/updateProjectTask/:backlog_id/:pt_id" component={UpdateProjectTask} />
          </Switch>
        </div>
      </Router>
    </Provider>
    );
  }
}

export default App;
