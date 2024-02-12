package com.management.user.service;

import com.management.user.model.Role;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
public interface RoleService {

    List<Role> createRoles(List<String> roles);
    List<Role> getRoles();
}
