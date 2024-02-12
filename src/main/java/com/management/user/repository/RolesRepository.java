package com.management.user.repository;

import com.management.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

    Role getRoleByName(String name);

    Role getRoleByNameEqualsAndIsDeleted(String name, boolean isDeleted);

}
