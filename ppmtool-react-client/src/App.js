import React, {Component} from "react";
import "./App.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import AddProject from "./components/Project/AddProject";
import {Provider} from "react-redux"; // allows us to connect react with redux
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";

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
        </div>
      </Router>
    </Provider>
    );
  }
}

export default App;
