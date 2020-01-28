import {GET_PROJECTS, GET_PROJECT, DELETE_PROJECT} from "../actions/Types"; // import GET_PROJECTS from Types.js

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
            };
        case GET_PROJECT:
            return {
                ...state,
                project: action.payload // return a SINGLE ELEMENT instead of all the projects
            };
        case DELETE_PROJECT:
            return {
                ...state,
                // filter out projects that are not identitical to the project identifier and display them
                projects: state.projects.filter(project=>project.projectIdentifier !== action.payload)
            };
        default: 
            return state;
    }
}