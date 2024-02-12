package com.management.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Setter
@Getter
@Entity(name = "token")
public class Token extends Base {

    private String value;
    @ManyToOne
    private UserDetail user;

    @Column(name = "expire_at")
    private Date expireAt;

}
