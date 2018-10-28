package edu.spring.data.nosql.redis.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends GlobalMethodSecurityConfiguration {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ADMIN = "ADMIN";

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        UserDetails inMemoryUser = User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .roles(ADMIN)
                .build();
        auth.inMemoryAuthentication()
                .withUser(inMemoryUser);
    }
}
