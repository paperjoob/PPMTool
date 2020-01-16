import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import AddProject from "./components/Project/AddProject";

function App() {
  return (
    // wrap the app.js in the router tag to enable us to have specific routes
    <Router>
    <div className="App">
      <Header />
      <Route exact path="/dashboard" component={Dashboard}/>
      <Route exact path="/addProject" component={AddProject}/>
    </div>
    </Router>
  );
}

export default App;
