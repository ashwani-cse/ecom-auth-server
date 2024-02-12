package com.management.user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Setter
@Getter
@Entity(name = "role")
public class Role extends Base {

    private String name;


}
