package com.management.user.repository;

import com.management.user.model.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Repository
public interface UserRepository extends JpaRepository<UserDetail, Long> {

    Logger logger = LoggerFactory.getLogger(UserRepository.class);

    default UserDetail saveUser(UserDetail userDetail) {
        Instant instant = Instant.now();
        userDetail.setCreateTimeStamp(instant);
        userDetail.setUpdateTimeStamp(instant);
        userDetail.setDeleted(false);
        UserDetail user = save(userDetail);
        logger.info("User with email [{}] saved in DB", user.getEmail());
        return user;
    }

    Optional<UserDetail> findUserDetailByEmail(String email);
}
