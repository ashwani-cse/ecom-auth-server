package com.management.user.dto;

import java.io.Serializable;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
public record SignInDto(String email, String password) implements Serializable {
}
