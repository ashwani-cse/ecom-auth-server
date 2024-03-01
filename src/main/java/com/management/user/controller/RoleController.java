package com.management.user.controller;

import com.management.user.model.Role;
import com.management.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.management.user.Constants.ApiSecurity.PATH;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Slf4j
@RequestMapping(PATH + "/roles")
@RestController
@RequiredArgsConstructor
public class RoleController {


    private final RoleService roleService;

    @PostMapping("/")
    public ResponseEntity<List<String>> addRoles(@RequestBody List<String> roles) {
        log.info("Adding roles: {}", roles);
        List<Role> createdRoles = roleService.createRoles(roles);
        if (createdRoles.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mapRolesToString(createdRoles));
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> getAllRoles() {
        log.info("Getting all roles");
        List<Role> roles = roleService.getRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mapRolesToString(roles));
    }

    private List<String> mapRolesToString(List<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .toList();
    }
}
