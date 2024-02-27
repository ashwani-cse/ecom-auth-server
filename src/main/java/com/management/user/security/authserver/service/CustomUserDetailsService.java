package com.management.user.security.authserver.service;

import com.management.user.model.Role;
import com.management.user.model.UserDetail;
import com.management.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is used to load the user by username. It implements the UserDetailsService interface
 * which has a method loadUserByUsername which is used to load the user by username.
 * @author Ashwani Kumar
 * Created on 19/02/24.
 */

@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is used to load the user by username. It is customized version of Spring's UserDetailsService
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername starts; username: {}", username);
        Optional<UserDetail> userDetailOptional = userRepository.findUserDetailByEmail(username);// we have email as username
        if(userDetailOptional.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }
        UserDetail userDetail = userDetailOptional.get();
        // Either we can create a custom UserDetails to return because the return type of this method
        // is Spring framework's userDetails object or we can use the Spring framework's User class which extends UserDetails
        List<GrantedAuthority> authorities  = getAuthorities(userDetail);
        return new org.springframework.security.core.userdetails.User(
                userDetail.getEmail(),
                userDetail.getPassword(),
                authorities);
    }

    /**
     * This method is used to get the authorities from the userDetail object and return it
     * @param userDetail
     * @return
     */
    private List<GrantedAuthority> getAuthorities(UserDetail userDetail) {
        log.info("getAuthorities starts; userDetail: {}", userDetail.getEmail());
        // we can get the authorities from the userDetail object and return it
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role: userDetail.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        log.info("getAuthorities ends; authorities: {}", authorities);
        return authorities;
    }
}
