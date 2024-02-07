package com.management.user.service;

import com.management.user.dto.SignInDto;
import com.management.user.model.UserDetail;
import com.management.user.dto.SignUpDto;

import java.util.List;

public interface UserManagementService {

    List<UserDetail> getAllUsers();
    UserDetail registerUser(SignUpDto signUpDto);

    UserDetail loginUser(SignInDto signInDto);

    boolean logoutUser();
}
