package com.management.user.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Ashwani Kumar
 * Created on 18/02/24.
 */
@Slf4j
@Configuration
public class CommonPasswordEncoder {
    // This class is used to create a bean of BCryptPasswordEncoder
    // This bean is used to encode the password
    // This bean is used in the UserDetailsService class
    // This bean is used in the WebSecurityConfig class
    // This bean is used in the WebSecurityConfig class

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        log.info("bCryptPasswordEncoder bean created; strength: 16");
        return new BCryptPasswordEncoder(16);
    }
}
