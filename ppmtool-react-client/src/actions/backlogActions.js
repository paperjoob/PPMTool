import axios from "axios";
import { GET_ERRORS, GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK } from "./Types";

// it takes three parameters: backlog_id, project task and the history parameter that allows us to push a redirect
// async means that the function always returns a PROMISE; js will wait for the promise to settle
export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {

    try {
        // the URL for the post on the server side, and pass the project task
        await axios.post(`http://localhost:8080/api/backlog/${backlog_id}`, project_task);
        // once its been posted, push us back to the projectback - backlog page
        // clear the state if the post is successful
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    history.push(`/projectBoard/${backlog_id}`);
    } catch (error) {
        // if there is an error, the action GET_ERRORS and send the error response data it receives from the back-end
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}; // end addProjectTask

// get backlog by project identifier = backlog id
export const getBacklog = (backlog_id) => async dispatch => {
    try {
        const res = await axios.get(`http://localhost:8080/api/backlog/${backlog_id}`)
        dispatch({
            type: GET_BACKLOG, // get list of project tasks, if any
            payload: res.data
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}; // end getBacklog

// get project task details by its id
export const getProjectTask = (backlog_id, pt_id, history) => async dispatch => {
  try {
      const res = await axios.get(`http://localhost:8080/api/backlog/${backlog_id}/${pt_id}`);
      dispatch({
          type: GET_PROJECT_TASK, // get the project task of this particular project task ID
          payload: res.data
      });
  } catch (error) {
      // if there are errors, go back to the dashboard
      history.push("/dashboard");
  }
}; // end getProjectTask

// Update a Project Task
// Pass in the backlog id, project task id, and the project itself
export const updateProjectTask = (backlog_id, pt_id, project_task, history) => async dispatch => {
    try {
      await axios.patch(`http://localhost:8080/api/backlog/${backlog_id}/${pt_id}`, project_task);
      history.push(`/projectBoard/${backlog_id}`);
      dispatch({
        type: GET_ERRORS,
        payload: {}
      });
    } catch (err) {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      });
    }
  }; // end updateProjectTask

// delete a project task
export const deleteProjectTask = (backlog_id, pt_id) => async dispatch => {
        await axios.delete(`http://localhost:8080/api/backlog/${backlog_id}/${pt_id}`)
        dispatch({
            type: DELETE_PROJECT_TASK,
            payload: pt_id
        })
} // end deleteProjectTask





