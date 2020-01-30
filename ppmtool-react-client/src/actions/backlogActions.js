import axios from "axios";
import {GET_ERRORS} from "./Types";

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
};