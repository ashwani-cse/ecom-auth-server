package com.management.user.repository;

import com.management.user.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

/**
 * @author Ashwani Kumar
 * Created on 12/02/24.
 */
public interface TokenRepository extends JpaRepository<Token, Long>{

    /*
    *  This method will find the token by value and isDeleted  = true , so we will do safe deletion.
    * */
    Optional<Token> findByValueAndIsDeletedEquals(String value, boolean isDeleted);

    Optional<Token> findByValueAndIsDeletedEqualsAndExpireAtGreaterThan(String value, boolean isDeleted, Date expiryGreaterThan);

}
