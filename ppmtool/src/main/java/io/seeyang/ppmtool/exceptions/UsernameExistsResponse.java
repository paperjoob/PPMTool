package io.seeyang.ppmtool.exceptions;

public class UsernameExistsResponse {

    private String username;

    // constructor that takes in the username as a strings
    public UsernameExistsResponse(String username) {
        this.username = username;
    }

    // setter and getters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
