package com.management.user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Entity(name = "role")
public class Role extends Base {

    private String name;


}
