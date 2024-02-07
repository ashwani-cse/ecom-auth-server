package com.management.user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "role")
public class Role extends Base {

    private String name;

}
