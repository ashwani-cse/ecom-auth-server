package com.management.user.dto;

import java.io.Serializable;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
public record SignUpDto(String firstName, String lastName, String email, String password,
                        String phoneNumber) implements Serializable {
}