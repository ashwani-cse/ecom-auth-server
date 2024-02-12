package com.management.user.service.impl;

import com.management.user.model.Role;
import com.management.user.repository.RolesRepository;
import com.management.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RolesRepository rolesRepository;

    @Override
    public List<Role> createRoles(List<String> roleNames) {
        List<Role> existingRoles = rolesRepository.findAll();
        roleNames = roleNames.stream().filter(newRole -> existingRoles.stream().noneMatch(roleObj -> roleObj.getName().equalsIgnoreCase(newRole))).toList();
        List<Role> roles = buildRoles(roleNames);
        return rolesRepository.saveAll(roles);
    }

    @Override
    public List<Role> getRoles() {
        return rolesRepository.findAll();
    }

    private List<Role> buildRoles(List<String> roleNames) {
        Instant instant = Instant.now();
        return roleNames.stream()
                .map(name -> {
                    Role role = new Role();
                    role.setName(name.toUpperCase());
                    role.setCreateTimeStamp(instant);
                    role.setUpdateTimeStamp(instant);
                    return role;
                })
                .collect(Collectors.toList());
    }
}
