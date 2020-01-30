import {combineReducers} from "redux";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";
import backlogReducer from "./backlogReducer";

// combinereducers holds all the reducers in the application
export default combineReducers({
    errors: errorReducer, // errors reducer
    project: projectReducer, // projects reducer
    backlog: backlogReducer, // backlog reducer
});