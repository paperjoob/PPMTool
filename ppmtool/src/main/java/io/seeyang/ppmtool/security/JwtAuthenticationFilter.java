package io.seeyang.ppmtool.security;

import io.seeyang.ppmtool.domain.User;
import io.seeyang.ppmtool.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static io.seeyang.ppmtool.security.SecurityConstants.HEADER_STRING; // contains the HEADER which has Authorization key in it
import static io.seeyang.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

// extends Once Per Request Filter - use userdetails service to validate the user
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // check for the bearerToken and pass in the http serverlet request
            String jwt =getJWTFromRequest(httpServletRequest);

            // we want to make sure the token is not null and is valid
            // hastext = the request body is not null
            if(StringUtils.hasText(jwt)&& jwtTokenProvider.validateToken(jwt)) {
                // if not null, grab the user id from the token
                Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
                // grab the user details by its id
                User userDetails = customUserDetailsService.loadUserById(userId);

                // set up authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                // validate the token
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context, ", ex);
        }

        // Causes the next filter in the chain to be invoked
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);

        // if the text has the bearerToken and starts with the bearer prefix
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith(TOKEN_PREFIX)) {
            // leave out the first 7 characters and RETURN the rest of the length of the bearer token
            return bearerToken.substring(7, bearerToken.length());
        }
        // if the condition above doesn't pass, return a null token (pass nothing) and authentication should fail
        return null;
    }
}
