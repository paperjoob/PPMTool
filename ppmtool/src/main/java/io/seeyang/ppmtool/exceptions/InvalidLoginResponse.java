package io.seeyang.ppmtool.exceptions;

// returns a JSON object
public class InvalidLoginResponse {

    private String username;
    private String password;

    // constructor with no arguments
    public InvalidLoginResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }

    // GETTER AND SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
