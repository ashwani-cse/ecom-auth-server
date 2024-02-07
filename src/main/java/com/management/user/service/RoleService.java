package com.management.user.service;

import com.management.user.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> createRoles(List<String> roles);
    List<Role> getRoles();
}
