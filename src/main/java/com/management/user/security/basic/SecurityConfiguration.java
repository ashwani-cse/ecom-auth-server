package com.management.user.security.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
//@Configuration    // disable this to test oauth2 configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests ->
                        requests.anyRequest().permitAll()
                )
                .csrf().disable() // disable CSRF because POST method will not work for permitAll
        ;
        return http.build();
    }
}
/* This will add later, initially allowing all endpoint without any auth
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/", "/home").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                    .loginPage("/login")
                    .permitAll()
            )
            .logout(LogoutConfigurer::permitAll);

    return http.build();
}
*/