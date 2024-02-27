package com.management.user.security.basic;

import com.management.user.model.Token;
import com.management.user.model.UserDetail;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Ashwani Kumar
 * Created on 12/02/24.
 */
/*
*  This is used for custom (non-oauth2) token generator POC.
* */
@Component
public class TokenGenerator {

    @Value("${token.expiry.days}")
    private int expiryDays;
    public Token generateToken(UserDetail userDetail) {
        Instant instant = Instant.now();
        Token token = new Token();
        token.setUser(userDetail);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setExpireAt(getExpiryTime());
        token.setCreateTimeStamp(instant);
        token.setUpdateTimeStamp(instant);
        return token;
    }

    private Date getExpiryTime() {
        LocalDate localDate = LocalDate.now().plusDays(expiryDays);
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
