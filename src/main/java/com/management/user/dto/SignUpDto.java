package com.management.user.dto;

import com.management.user.model.UserDetail;

import java.io.Serializable;

/**
 * DTO for {@link UserDetail}
 */
public record SignUpDto(String firstName, String lastName, String email, String password,
                        String phoneNumber) implements Serializable {
}