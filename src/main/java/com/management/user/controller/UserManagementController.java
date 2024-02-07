package com.management.user.controller;

import com.management.user.dto.SignInDto;
import com.management.user.model.UserDetail;
import com.management.user.dto.SignUpDto;
import com.management.user.service.UserManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetail>> getAllUsers(){
        List<UserDetail> users = userManagementService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDetail> signUp(@RequestBody SignUpDto signUpDto) {
        UserDetail userDetail = userManagementService.registerUser(signUpDto);
        return new ResponseEntity<>(userDetail, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDetail> signIn(@RequestBody SignInDto signInDto) {
        UserDetail userDetail = userManagementService.loginUser(signInDto);
        return new ResponseEntity<>(userDetail, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut() {
        if (userManagementService.logoutUser()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
