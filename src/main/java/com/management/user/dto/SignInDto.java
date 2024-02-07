package com.management.user.dto;

import java.io.Serializable;

public record SignInDto(String email, String password) implements Serializable {
}
