package com.management.user.controller;

import com.management.user.dto.LogOutDto;
import com.management.user.dto.SignInDto;
import com.management.user.dto.SignUpDto;
import com.management.user.model.Token;
import com.management.user.model.UserDetail;
import com.management.user.service.UserManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Slf4j
@RequestMapping("/users")
@RestController
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/")
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
    public ResponseEntity<Token> signIn(@RequestBody SignInDto signInDto) {
        Token token = userManagementService.loginUser(signInDto.email(), signInDto.password());
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody LogOutDto logOutDto) {
        if (userManagementService.logoutUser(logOutDto.token())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
    *  This endpoint will validate the token and return the user details.
    *  If the token is not valid then it will return 404. Any client can use this endpoint to validate the token.
    * */
    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDetail> validateToken(@PathVariable("token") String token) {
        UserDetail userDetail = userManagementService.validateToken(token);
        return new ResponseEntity<>(userDetail, HttpStatus.OK);
    }
}
