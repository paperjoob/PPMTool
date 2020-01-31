import React, {Component} from "react";
import "./App.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import AddProject from "./components/Project/AddProject";
import {Provider} from "react-redux"; // allows us to connect react with redux
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";

class App extends Component {
  render() {
   return (
     <Provider store={store}>
    {/* wrap the app.js in the router tag to enable us to have specific routes */}
      <Router>
        <div className="App">
          <Header />
          <Route exact path="/dashboard" component={Dashboard}/>
          <Route exact path="/addProject" component={AddProject}/>
          {/* the update project takes a parameter of ID */}
          <Route exact path ="/updateProject/:id" component={UpdateProject}/>
          <Route exact path="/projectBoard/:id" component={ProjectBoard} />
          <Route exact path="/addProjectTask/:id" component={AddProjectTask} />
          {/* pass in the backlog_id and pt_id from the server in order to update a project task */}
          <Route exact path="/updateProjectTask/:backlog_id/:pt_id" component={UpdateProjectTask} />
        </div>
      </Router>
    </Provider>
    );
  }
}

export default App;
