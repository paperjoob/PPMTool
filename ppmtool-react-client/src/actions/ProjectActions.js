import axios from 'axios';
import {GET_ERRORS, GET_PROJECTS, GET_PROJECT} from "./Types"; // import GET_ERRORS and GET PROJECTS from Types.js

// it takes two parameters, the project object, and the history parameter that allows us to push a redirect
// async means that the function always returns a PROMISE; js will wait for the promise to settle
export const createProject = (project, history) => async dispatch => {
    try {
        // create a response to post to that URL with the project object that was passed in through the addproject component
        const res = await axios.post("http://localhost:8080/api/project/", project);
        // if the project is posted, push the user back to the dashboard
        history.push("/dashboard");
    } catch (error) {
        // if there is an error, the action GET_ERRORS and send the error response data it receives from the back-end
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
};

// GET ALL PROJECTS API CALL
export const getProjects = () => async dispatch => {
    const res = await axios.get("http://localhost:8080/api/project/all");
    dispatch({
        type: GET_PROJECTS,
        payload: res.data // the data is the response from the server
    })
};

// GET A SINGLE PROJECT BY ITS ID
export const getProject = (id, history) => async dispatch => {
    try {
        const res = await axios.get(`http://localhost:8080/api/project/${id}`);
        dispatch({
          type: GET_PROJECT,
          payload: res.data
        });
    } catch (error) {
        // if there's an error, send them back to the dashboard
        history.push("/dashboard");
    }
  };

