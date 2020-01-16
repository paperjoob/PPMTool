import {GET_ERRORS} from "../actions/Types";

// create an empty initialstate
const initialState = {};

export default function(state=initialState, action) {
    switch(action.type) {
        // check if there are errors, if there are, return the errors as the payload
        case GET_ERRORS:
            return action.payload;
        default:
            return state;
    }
}