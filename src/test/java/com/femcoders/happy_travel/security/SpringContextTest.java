package com.femcoders.happy_travel.security;

import com.femcoders.happy_travel.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class SpringContextTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void contextLoads() {
        assertNotNull(jwtUtils, "JwtUtils bean should be loaded");
        assertNotNull(userDetailsService, "UserDetailsServiceImpl bean should be loaded");
        assertNotNull(jwtAuthenticationFilter, "JwtAuthenticationFilter bean should be loaded");
    }
}
