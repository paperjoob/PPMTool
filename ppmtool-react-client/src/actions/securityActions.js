import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./Types";
import setJwtToken from "../securityUtils/setJwtToken";
import jwt_decode from "jwt-decode";

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
}; // end createNewUser


export const login = (LoginRequest) => async dispatch => {
    try {
        // post => Login Request
        const res = await axios.post("http://localhost:8080/api/users/login", LoginRequest);
        // extract the token from the res.data (the response from the server tells us if the login was successful and then generates the token)
        const {token} = res.data;
        // store the token in the localStorage
        localStorage.setItem("jwtToken", token);
        // set the token in the Headers with the setJwtToken function from setJwtToken.js
        setJwtToken(token);
        // decode the token (encrypted bcrypt from the server)
        const decodedToken = jwt_decode(token);
        // dispatch to our security Reducer and load the decoded token into the payload
        dispatch({
            type: SET_CURRENT_USER,
            payload: decodedToken
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
}; // end login