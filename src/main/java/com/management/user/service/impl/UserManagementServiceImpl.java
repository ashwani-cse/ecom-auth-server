package com.management.user.service.impl;

import com.management.user.dto.SignInDto;
import com.management.user.dto.SignUpDto;
import com.management.user.model.Role;
import com.management.user.model.UserDetail;
import com.management.user.repository.RolesRepository;
import com.management.user.repository.UserRepository;
import com.management.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    @Override
    public List<UserDetail> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetail registerUser(SignUpDto signUpDto) {
        Role userRole = rolesRepository.getRoleByNameEqualsAndIsDeleted("USER", false);
        UserDetail userDetail = buildUserDetail(signUpDto, Collections.singletonList(userRole));
        return userRepository.save(userDetail);
    }

    @Override
    public UserDetail loginUser(SignInDto signInDto) {
        // Implementation for login
        return null;
    }

    @Override
    public boolean logoutUser() {
        // Implementation for logout
        return false;
    }

    private UserDetail buildUserDetail(SignUpDto signUpDto, List<Role> roles) {
        return UserDetail.builder()
                .email(signUpDto.email())
                .firstName(signUpDto.firstName())
                .lastName(signUpDto.lastName())
                .password(signUpDto.password())
                .roles(roles)
                .build();
    }
}
