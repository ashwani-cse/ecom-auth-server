package com.management.user.service;

import com.management.user.dto.SignUpDto;
import com.management.user.model.Token;
import com.management.user.model.UserDetail;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
public interface UserManagementService {

    List<UserDetail> getAllUsers();
    UserDetail registerUser(SignUpDto signUpDto);

    Token loginUser(String email, String password);

    boolean logoutUser(String token);

    UserDetail validateToken(String token);
}
