package com.management.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@ToString
@Setter
@Getter
@Builder
@Entity(name = "user_detail")
@AllArgsConstructor
public class UserDetail extends Base {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER) // need to spring security to load the roles eagerly, else  an error will be thrown
    @JoinTable(name = "user_role_mapping",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
   // @ToString.Exclude
    private List<Role> roles;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    @Column(name = "is_phone_verified")
    private boolean isPhoneVerified;

    public UserDetail() {

    }
}
