import {combineReducers} from "redux";
import errorReducer from "./errorReducer";

// combinereducers holds all the reducers in the application
export default combineReducers({
    errors: errorReducer // errors reducer
});