import axios from "axios";
import { GET_ERRORS } from "./Types";

// create a new user and pass in the user information along with history
export const createNewUser = (newUser, history) => async dispatch =>{
    try {
        await axios.post("http://localhost:8080/api/users/register", newUser);
        // if user is created, push the user to the login page
        history.push("/login");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (error) {
        // if there is an error, the action GET_ERRORS and send the error response data it receives from the back-end
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
};