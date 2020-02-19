import axios from "axios";

// create the setJwtToken function
const setJwtToken = (token) => {
    // if we have a token present, then we are going to name Authorization in Headers and make sure that the headers has a token
    if (token) {
        axios.defaults.headers.common["Authorization"] = token;
    } else {
        // if there is no token, we are going to delete the Authorization field in Headers
        delete axios.defaults.headers.common["Authorization"];
    }
};

export default setJwtToken;