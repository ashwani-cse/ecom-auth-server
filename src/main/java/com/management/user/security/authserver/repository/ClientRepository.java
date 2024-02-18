package com.management.user.security.authserver.repository;

import com.management.user.security.authserver.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ashwani Kumar
 * Created on 18/02/24.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
	Optional<Client> findByClientId(String clientId);
}