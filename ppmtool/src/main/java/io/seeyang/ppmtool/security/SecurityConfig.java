package io.seeyang.ppmtool.security;

import io.seeyang.ppmtool.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static io.seeyang.ppmtool.security.SecurityConstants.H2_URL;
import static io.seeyang.ppmtool.security.SecurityConstants.SIGN_UP_URLS;

@Configuration
@EnableWebSecurity // it's to switch off the default web application security configuration and add your own.
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)

// WebSecurityConfigurerAdapter is a class that implements the web security interface and provides default security configuration
// by extending it, we can override some of the defaults
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // pass in JwtAuthenticationEntryPoint as unauthorizedHandler and throw this error if the user is not authenticated
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    // pass in custom user details service
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // pass in bcryptencoder
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {return new JwtAuthenticationFilter();}

    // takes in the user details service and password encoder and helps us build the authentication manager
    // which is used to authenticate the user when logging in
    // making sure the user has the correct password and username to generate JWT Token
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    // make the authentication manager a Bean
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Cross-origin resource sharing
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() // entry point is what thrown when someone is not authenticated
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // the REST API is stateless to not save sessions or cookies so the server doesnt hold the session.
                // use tokens instead of sessions, redux will hold the state
                .and()
                .headers().frameOptions().sameOrigin() // enables H2 database, if there's no mySQL
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll() // everything inside this, permit it all even when not logged in
                .antMatchers(SIGN_UP_URLS).permitAll() // permit everything in the users route
                .antMatchers(H2_URL).permitAll() // permit H2
                .anyRequest().authenticated(); // anything other than permitted, we need to be authenticated

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
