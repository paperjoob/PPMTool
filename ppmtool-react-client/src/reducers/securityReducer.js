import {SET_CURRENT_USER} from "../actions/Types";

// set the initial state to an empty user object and the validToken to false
const initialState = {
    user: {},
    validToken: false
};

// if theres a payload, return true
// else return false
const booleanActionPayload =( payload) => {
    if (payload) {
        return true;
    } else {
        return false;
    }
}

export default function(state = initialState, action) {
    switch(action.type) {
        case SET_CURRENT_USER:
            return {
                ...state,
                validToken: booleanActionPayload(action.payload),
                user: action.payload // set the user
            };
        default:
            return state;
    }
}; // end securityReducer

