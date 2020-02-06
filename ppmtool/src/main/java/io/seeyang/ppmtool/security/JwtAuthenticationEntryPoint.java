package io.seeyang.ppmtool.security;

import com.google.gson.Gson;
import io.seeyang.ppmtool.exceptions.InvalidLoginResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// an interface that provides the implementation for a method call commence
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // manage what the user sees when he/she accesses a resource without being authenticated
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        // create loginResponse object
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        // make sure the loginResponse is returned as JSON
        String jsonLoginResponse = new Gson().toJson(loginResponse);

        // what the user gets from the server as a response
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        // print the jsonLoginResponse
        httpServletResponse.getWriter().print(jsonLoginResponse);
    }
}
