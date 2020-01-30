import axios from "axios";

// it takes three parameters: backlog_id, project task and the history parameter that allows us to push a redirect
// async means that the function always returns a PROMISE; js will wait for the promise to settle
export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {

    // the URL for the post on the server side, and pass the project task
    await axios.post(`http://localhost:8080/api/backlog/${backlog_id}`, project_task);
    // once its been posted, push us back to the projectback - backlog page
    history.push(`/projectBoard/${backlog_id}`);
};