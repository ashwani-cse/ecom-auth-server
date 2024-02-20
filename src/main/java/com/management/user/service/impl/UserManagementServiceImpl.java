package com.management.user.service.impl;

import com.management.user.dto.SignUpDto;
import com.management.user.exception.CustomException;
import com.management.user.exception.ObjectNotFoundException;
import com.management.user.model.Role;
import com.management.user.model.Token;
import com.management.user.model.UserDetail;
import com.management.user.repository.TokenRepository;
import com.management.user.repository.UserRepository;
import com.management.user.security.basic.TokenGenerator;
import com.management.user.service.RoleService;
import com.management.user.service.UserManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final TokenGenerator tokenGenerator;

    @Override
    public List<UserDetail> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetail registerUser(SignUpDto signUpDto) {
        Role userRole = roleService.getDefaultRole();
        UserDetail userDetail = buildUserDetail(signUpDto, Collections.singletonList(userRole));
        try {
            return userRepository.save(userDetail);
        } catch (Exception e) {
            throw new CustomException("Saving user in DB fails.");
        }
    }

    @Override
    public Token loginUser(String email, String password) {
        Optional<UserDetail> userOptional = userRepository.findUserDetailByEmail(email);
        if (userOptional.isEmpty()) {
            log.error("User doesn't exist for email: {}", email);
            throw new ObjectNotFoundException("Invalid email address.");
        }
        UserDetail user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Password doesn't match.");
        }
        return tokenRepository.save(tokenGenerator.generateToken(user));
    }

    @Override
    public boolean logoutUser(String token) {
        tokenRepository.findByValueAndIsDeletedEquals(token, false)
                .ifPresentOrElse(
                        t -> {
                            t.setDeleted(true);
                            tokenRepository.save(t);
                        },
                        () -> {
                            throw new ObjectNotFoundException("Either logged out or Invalid Token.");
                        }
                );
        return true;
    }

    @Override
    public UserDetail validateToken(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndIsDeletedEqualsAndExpireAtGreaterThan(token, false, new Date());
        if (tokenOptional.isEmpty()) {
            throw new BadCredentialsException("Either logged out or Invalid Token.");
        }
        return tokenOptional.get().getUser();
    }

    private UserDetail buildUserDetail(SignUpDto signUpDto, List<Role> roles) {
        return UserDetail.builder()
                .email(signUpDto.email())
                .phoneNumber(signUpDto.phoneNumber())
                .firstName(signUpDto.firstName())
                .lastName(signUpDto.lastName())
                .password(bCryptPasswordEncoder.encode(signUpDto.password()))
                .roles(roles)
                .build();
    }

}
