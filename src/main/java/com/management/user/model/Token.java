package com.management.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
