package com.management.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name = "user_detail")
public class UserDetail extends Base {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany
    @JoinTable(name = "user_role_mapping",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    @Column(name = "is_phone_verified")
    private boolean isPhoneVerified;

}
