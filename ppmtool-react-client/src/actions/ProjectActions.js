import axios from 'axios';
import {GET_ERRORS} from "./Types"; // import GET_ERRORS from Types.js

// it takes two parameters, the project object, and the history parameter that allows us to push a redirect
// async means that the function always returns a PROMISE; js will wait for the promise to settle
export const createProject = (project, history) => async dispatch => {
    try {
        // create a response to post to that URL with the project object that was passed in through the addproject component
        const res = await axios.post("http://localhost:8080/api/project/", project);
        // if the project is posted, push the user back to the dashboard
        history.push("/dashboard");
    } catch (error) {
        // if there is an error, the action is GET_ERRORS and send the error response data it receives from the back-end
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
}

