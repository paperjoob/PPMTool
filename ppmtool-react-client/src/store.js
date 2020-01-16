import {createStore, applyMiddleware, compose} from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initialState = {};
const middleware = [thunk];

let store;

// if the window is Chrome, then we will create a store and use the middleware and the dev tools installed in chrome
// else
if (window.navigator.userAgent.includes("Chrome")) {
    // pass in the root reducer and the initial state, and the enhancer
    // add additional middleware if needed - ** I have Redux tools installed in Chrome
    store = createStore(rootReducer, 
        initialState, 
        compose(applyMiddleware(...middleware), window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__())
        );
} else {
    store = createStore(rootReducer, 
        initialState, 
        compose(applyMiddleware(...middleware))
        );
}

export default store;
