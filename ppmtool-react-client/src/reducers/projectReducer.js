import {GET_PROJECTS} from "../actions/Types"; // import GET_PROJECTS from Types.js

// create initial state
const initialState = {
    projects: [], // empty projects array
    project: {} // a single project to update
};

// the function is taking in the state parameter and an action
export default function(state = initialState, action) {
    switch(action.type) {
        case GET_PROJECTS:
            return {
                ...state, // return the state
                projects: action.payload // return ALL projects from the server
            }
        default: 
            return state;
    }
}